package com.ambush.ambushchallenge.di

import com.ambush.ambushchallenge.data.Repository
import com.ambush.ambushchallenge.data.RepositoryImpl
import com.ambush.ambushchallenge.data.local.LocalDataSource
import com.ambush.ambushchallenge.data.local.LocalDataSourceImpl
import com.ambush.ambushchallenge.data.local.paperProvider
import com.ambush.ambushchallenge.data.remote.Service
import com.ambush.ambushchallenge.data.remote.network.createWebService
import com.ambush.ambushchallenge.data.remote.repository.RemoteDataSource
import com.ambush.ambushchallenge.data.remote.repository.RemoteDataSourceImpl
import com.ambush.ambushchallenge.ui.viewmodel.RepositoryViewModel
import io.paperdb.Book
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModules = module {
    single<Service> { createWebService() }
    single<Book> { paperProvider() }
    single<Repository> { RepositoryImpl(get(), get()) }
    single<RemoteDataSource> { RemoteDataSourceImpl(get()) }
    single<LocalDataSource> { LocalDataSourceImpl(get()) }
    viewModel { RepositoryViewModel(get()) }
}
