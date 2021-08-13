package com.ambush.ambushchallenge.ui.fragments

import android.view.View
import androidx.navigation.fragment.navArgs
import com.ambush.ambushchallenge.data.model.AmbushRepository
import com.ambush.ambushchallenge.data.remote.state.State
import com.ambush.ambushchallenge.databinding.FragmentRepositoryFilterBinding
import com.ambush.ambushchallenge.ui.adapter.RepositoryAdapter
import com.ambush.ambushchallenge.ui.custom.BaseFragment
import com.ambush.ambushchallenge.ui.viewmodel.RepositoryViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RepositoryFilterFragment : BaseFragment<FragmentRepositoryFilterBinding>() {

    private lateinit var repositoryAdapter: RepositoryAdapter
    private val viewModel: RepositoryViewModel by sharedViewModel()
    private val args: RepositoryFilterFragmentArgs by navArgs()

    override fun getViewBinding() = FragmentRepositoryFilterBinding.inflate(layoutInflater)

    override fun getData() {
        args.selectedLanguageName?.let {
            viewModel.getAmbushRepositoriesByLanguage(it)
        }
    }

    override fun setUpViews() {
        initRecycler()
    }

    override fun observeData() {
        initStateObserver()
    }

    private fun initStateObserver() {
        viewModel.state.observe(viewLifecycleOwner, { state ->
            when (state) {
                is State.Error -> errorDialogHandler(state.errorMessage)
                is State.Loading -> showLoading(state.show)
                is State.OnSuccessGetRepositoryFilteredByLanguage -> setRecyclerData(state.data)
                else -> {
                }
            }
        })
    }

    private fun setRecyclerData(data: List<AmbushRepository>) {
        repositoryAdapter.submitList(data)
    }

    private fun initRecycler() {
        RepositoryAdapter().also {
            repositoryAdapter = it
            binding.rvRepositoryFilter.adapter = it
        }
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
}
