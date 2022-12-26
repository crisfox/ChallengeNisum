package com.nisum.challenge.common.di

import com.nisum.challenge.common.database.AppDatabase
import com.nisum.challenge.common.networking.Client
import com.nisum.challenge.app.repositories.PokeInfoRepository
import com.nisum.challenge.app.repositories.PokeRepository
import com.nisum.challenge.app.services.PokeApi
import com.nisum.challenge.app.services.PokeService
import com.nisum.challenge.app.ui.viewmodel.PokeInfoViewModel
import com.nisum.challenge.app.ui.viewmodel.PokeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Módulo de ViewModels.
 */
val viewModelModule = module {
    viewModel { PokeViewModel(get()) }
    viewModel { PokeInfoViewModel(get(), androidContext().resources) }
}

/**
 * Módulo de repositorios.
 */
val repositoryModule = module {
    factory { PokeRepository(get(), db().pokeDao()) }
    factory { PokeInfoRepository(get(), db().pokeInfoDao()) }
}

/**
 * Módulo de la api.
 */
val apiModule = module {
    fun provideUseService(retrofit: Retrofit): PokeService {
        return retrofit.create(PokeService::class.java)
    }
    single { provideUseService(get()) }
    single { PokeApi(get(), androidContext().resources) }
}

/**
 * Módulo de la database.
 */
fun Scope.db() = get<AppDatabase>()

val daoModule = module {
    single { AppDatabase.newInstance(androidContext()) }
    single { db().pokeDao() }
    single { db().pokeInfoDao() }
}

/**
 * Módulo de retrofit.
 */
val retrofitModule = module {
    single { Client.provideGson }
    single { Client.okHttpClient }
    single { Client.provideRetrofit(get(), get()) }
}
