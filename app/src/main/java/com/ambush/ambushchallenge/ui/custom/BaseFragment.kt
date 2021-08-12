package com.ambush.ambushchallenge.ui.custom

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.viewbinding.ViewBinding
import com.ambush.ambushchallenge.R
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    private var loadingDialog = CustomLoading()
    protected val isConnected = MutableLiveData<Boolean>()
    protected lateinit var binding: T
    protected abstract fun getViewBinding(): T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpBinding().also { return binding.root }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setupObservers()
        isConnected()
        getData()
    }

    private fun setupObservers() {
        observeConnection()
        observeData()
    }

    private fun observeConnection() {
        isConnected.observe(requireActivity(), {
            if (!it) {
                showIsOfflineSnackbar()
            }
        })
    }

    private fun setUpBinding() {
        binding = getViewBinding()
    }

    private fun showIsOfflineSnackbar() {
        Snackbar.make(
            binding.root,
            getString(R.string.you_are_offline),
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction(getString(R.string.go_to_online)) {
                isConnected()
                getData()
            }
        }.also {
            it.show()
        }
    }

    protected fun showLoading(shouldShow: Boolean) {
        loadingDialog.runCatching {
            if (shouldShow) {
                show(this@BaseFragment.parentFragmentManager, null)
            } else {
                dismiss()
            }
        }
    }

    private fun isConnected() {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        isConnected.value = activeNetwork?.isConnectedOrConnecting == true
    }

    open fun getData() {}

    open fun setUpViews() {}

    open fun observeData() {}
}
