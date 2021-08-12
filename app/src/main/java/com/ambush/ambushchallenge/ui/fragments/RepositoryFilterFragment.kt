package com.ambush.ambushchallenge.ui.fragments

import com.ambush.ambushchallenge.data.remote.response.AmbushReposResponse
import com.ambush.ambushchallenge.databinding.FragmentRepositoryFilterBinding
import com.ambush.ambushchallenge.ui.adapter.RepositoryAdapter
import com.ambush.ambushchallenge.ui.custom.BaseFragment
import com.ambush.ambushchallenge.ui.viewmodel.RepositoryViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RepositoryFilterFragment : BaseFragment<FragmentRepositoryFilterBinding>() {

    private lateinit var repositoryAdapter: RepositoryAdapter
    private val viewModel: RepositoryViewModel by sharedViewModel()

    override fun getViewBinding() = FragmentRepositoryFilterBinding.inflate(layoutInflater)

    override fun getData() {
        viewModel.loadAmbushRepos(isConnected.value)
    }

    override fun setUpViews() {
        initRecycler()
    }

    override fun observeData() {
        initStateObserver()
    }

    private fun initStateObserver() {
        viewModel.languageRepos.observe(viewLifecycleOwner, { repos ->
            setRecyclerData(repos)
        })
    }

    private fun setRecyclerData(data: List<AmbushReposResponse>) {
        repositoryAdapter.submitList(data)
    }

    private fun initRecycler() {
        RepositoryAdapter().also {
            repositoryAdapter = it
            binding.rvRepositoryFilter.adapter = it
        }
    }
}
