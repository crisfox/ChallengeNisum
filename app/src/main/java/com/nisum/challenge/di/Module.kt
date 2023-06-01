package com.nisum.challenge.di

import com.nisum.challenge.core.Client
import com.nisum.challenge.data.database.AppDatabase
import com.nisum.challenge.data.network.PokeApi
import com.nisum.challenge.data.network.PokeService
import com.nisum.challenge.data.network.PokeServiceImpl
import com.nisum.challenge.data.repositories.PokeInfoRepository
import com.nisum.challenge.data.repositories.PokeInfoRepositoryImpl
import com.nisum.challenge.data.repositories.PokeRepository
import com.nisum.challenge.data.repositories.PokeRepositoryImpl
import com.nisum.challenge.domain.GetInfoPokeUseCase
import com.nisum.challenge.domain.GetListPokeUseCase
import com.nisum.challenge.ui.viewmodel.PokeInfoViewModel
import com.nisum.challenge.ui.viewmodel.PokeViewModel
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
 * Módulo de UsesCases.
 */
val useCaseModule = module {
    factory { GetListPokeUseCase(get()) }
    factory { GetInfoPokeUseCase(get()) }
}

/**
 * Módulo de repositorios.
 */
val repositoryModule = module {
    factory<PokeRepository> { PokeRepositoryImpl(get(), db().pokeDao()) }
    factory<PokeInfoRepository> { PokeInfoRepositoryImpl(get(), db().pokeInfoDao()) }
}

/**
 * Módulo de la api.
 */
val apiModule = module {
    fun provideUseService(retrofit: Retrofit): PokeApi {
        return retrofit.create(PokeApi::class.java)
    }
    single { provideUseService(get()) }
    single<PokeService> { PokeServiceImpl(get(), androidContext().resources) }
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
