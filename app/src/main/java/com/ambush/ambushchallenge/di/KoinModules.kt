package com.ambush.ambushchallenge.di

import com.ambush.ambushchallenge.data.remote.Service
import com.ambush.ambushchallenge.data.remote.network.createWebService
import com.ambush.ambushchallenge.data.repository.Repository
import com.ambush.ambushchallenge.data.repository.RepositoryImpl
import com.ambush.ambushchallenge.ui.viewmodel.RepositoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModules = module {
    single<Repository> { RepositoryImpl(get()) }
    viewModel { RepositoryViewModel(get()) }
    single<Service> { createWebService() }
}
