package com.jaadutona.yuvaaptest

import android.app.Application

import com.jaadutona.yuvaaptest.di.authModule
import com.jaadutona.yuvaaptest.di.homeModule
import com.jaadutona.yuvaaptest.di.networkModule
import com.jaadutona.yuvaaptest.di.profileModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp) // Context pass karo
           // modules(appModule)         // Modules register karo

            // you can use feature wise in list

            modules(
                listOf(
                    networkModule,
                    homeModule
                )
            )
        }
    }
}