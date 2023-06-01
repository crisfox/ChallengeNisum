package com.nisum.challenge.data.network

import com.nisum.challenge.domain.model.Evolution
import com.nisum.challenge.domain.model.PokeInfo
import com.nisum.challenge.domain.model.ResultSearchModel
import com.nisum.challenge.domain.model.Species
import com.nisum.challenge.data.repositories.PokeRepositoryImpl
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface donde se encuentran las llamadas a la api.
 */
interface PokeApi {

    /**
     * Servicio encargado de traer los pokemons.
     */
    @GET("pokemon")
    suspend fun getSearchPokes(
        @Query("limit") limit: Number = PokeRepositoryImpl.ITEMS_PER_PAGE,
        @Query("offset") offset: Number = 0
    ): Response<ResultSearchModel>

    /**
     * Servicio encargado de traer la información detallada del pokemon.
     */
    @GET("pokemon/{name}")
    suspend fun getPokeInfo(@Path("name") name: String): Response<PokeInfo>

    /**
     * Servicio encargado de traer las especies.
     */
    @GET("pokemon-species/{id}")
    suspend fun getSpeciesInfo(@Path("id") id: String): Response<Species>

    /**
     * Servicio encargado de traer la evoluciones del pokemon.
     */
    @GET("evolution-chain/{id}")
    suspend fun getEvolutionInfo(@Path("id") id: String): Response<Evolution>
}
