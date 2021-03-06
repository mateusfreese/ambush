package com.ambush.ambushchallenge.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ambush.ambushchallenge.R
import com.ambush.ambushchallenge.data.model.Language
import com.ambush.ambushchallenge.data.remote.state.State
import com.ambush.ambushchallenge.databinding.FragmentRepositoryListBinding
import com.ambush.ambushchallenge.extensions.navigateWithAnimations
import com.ambush.ambushchallenge.ui.adapter.LanguageAdapter
import com.ambush.ambushchallenge.ui.custom.BaseFragment
import com.ambush.ambushchallenge.ui.viewmodel.RepositoryViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RepositoryListFragment : BaseFragment<FragmentRepositoryListBinding>() {

    private lateinit var languageAdapter: LanguageAdapter
    private val viewModel: RepositoryViewModel by sharedViewModel()

    override fun getViewBinding() = FragmentRepositoryListBinding.inflate(layoutInflater)

    override fun setUpViews() {
        initRecycler()
    }

    override fun observeData() {
        initStateObservers()
    }

    override fun getData() {
        viewModel.getAmbushReposLanguageList()
    }

    private fun initStateObservers() {
        viewModel.state.observe(viewLifecycleOwner, { state ->
            when (state) {
                is State.Error -> errorDialogHandler(state.errorMessage)
                is State.Loading -> showLoading(state.show)
                is State.OnSuccessGetRepositoryLanguageList -> setRecyclerData(state.data)
                else -> {
                }
            }
        })
    }

    private fun initRecycler() {
        binding.rvRepositoryList.let { rv ->
            rv.layoutManager =
                StaggeredGridLayoutManager(RV_SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL)
            LanguageAdapter().apply {
                languageItemClickListener = { language ->
                    language.name?.let { navigateToRepositoryFilter(it) }
                }
            }.also { adapter ->
                languageAdapter = adapter
                binding.rvRepositoryList.adapter = adapter
            }
        }
    }

    private fun setRecyclerData(data: List<Language>) {
        languageAdapter.submitList(data)
    }

    private fun navigateToRepositoryFilter(languageName: String) {
        findNavController().navigateWithAnimations(
            R.id.action_repositoryListFragment_to_repositoryFilterFragment,
            Bundle().apply { putString(SELECTED_LANGUAGE_NAME, languageName) }
        )
    }

    private fun errorDialogHandler(errorMessage: String) {
        binding.dialogError.apply {
            fun showError() {
                root.visibility = View.VISIBLE
            }

            fun dismissError() {
                root.visibility = View.GONE
            }

            fun setUpError() {
                tvErrorMessage.text = errorMessage
                btnTryAgain.setOnClickListener {
                    dismissError()
                    getData()
                }
            }

            setUpError()
            showError()
        }
    }

    companion object {
        const val RV_SPAN_COUNT = 3
        const val SELECTED_LANGUAGE_NAME = "selectedLanguageName"
    }
}
