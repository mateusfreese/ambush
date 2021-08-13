package com.ambush.ambushchallenge

import android.app.Application
import com.ambush.ambushchallenge.di.koinModules
import io.paperdb.Paper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Paper.init(this)
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(koinModules)
        }
    }
}
