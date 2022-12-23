package com.nisum.challenge.home.services

import com.nisum.challenge.common.models.ResultSearchModel
import com.nisum.challenge.common.networking.AppNetworkResult
import com.nisum.challenge.common.networking.execute
import com.nisum.challenge.home.repositories.PokeRepository.Companion.ITEMS_PER_PAGE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

class PokeApi(private val service: PokeService) {

    suspend fun get(): AppNetworkResult<ResultSearchModel> {
        return execute { service.getSearchPokes() }
    }
}

/**
 * Interface donde se encuentran las llamadas a la api.
 */
interface PokeService {

    /**
     * Servicio encargado de traer los repositorios de github.
     */
    @GET("pokemon")
    suspend fun getSearchPokes(
        @Query("limit") limit: Number = ITEMS_PER_PAGE,
        @Query("offset") offset: Number = 0
    ): Response<ResultSearchModel>
}
