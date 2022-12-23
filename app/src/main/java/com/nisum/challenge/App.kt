package com.nisum.challenge

import android.app.Application
import com.nisum.challenge.common.di.apiModule
import com.nisum.challenge.common.di.daoModule
import com.nisum.challenge.common.di.repositoryModule
import com.nisum.challenge.common.di.retrofitModule
import com.nisum.challenge.common.di.viewModelModule
import com.nisum.challenge.common.networking.NetworkMonitor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

/**
 * Clase de configuración para los módulos de Koin.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        NetworkMonitor(this).startNetworkCallback()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(
                listOf(
                    retrofitModule,
                    daoModule,
                    apiModule,
                    repositoryModule,
                    viewModelModule,
                )
            )
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        NetworkMonitor(this).stopNetworkCallback()
    }
}
