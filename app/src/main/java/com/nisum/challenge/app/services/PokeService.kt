package com.nisum.challenge.app.services

import android.content.res.Resources
import com.nisum.challenge.app.repositories.PokeRepository.Companion.ITEMS_PER_PAGE
import com.nisum.challenge.common.models.Evolution
import com.nisum.challenge.common.models.PokeInfo
import com.nisum.challenge.common.models.ResultSearchModel
import com.nisum.challenge.common.models.Species
import com.nisum.challenge.common.networking.AppNetworkResult
import com.nisum.challenge.common.networking.execute
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Api que intercepta las llamadas a la api para mapear la respuesta y asignarle un estado.
 *
 * @property service PokeService servicio de la api.
 * @property resources Resources para obtener los string del res.
 */
class PokeApi(private val service: PokeService, val resources: Resources) {

    suspend fun get(): AppNetworkResult<ResultSearchModel> {
        return execute(resources) { service.getSearchPokes() }
    }

    suspend fun getInfo(name: String): AppNetworkResult<PokeInfo> {
        return execute(resources) { service.getPokeInfo(name) }
    }

    suspend fun getSpeciesInfo(id: String): AppNetworkResult<Species> {
        return execute(resources) { service.getSpeciesInfo(id) }
    }

    suspend fun getEvolutionInfo(id: String): AppNetworkResult<Evolution> {
        return execute(resources) { service.getEvolutionInfo(id) }
    }
}

/**
 * Interface donde se encuentran las llamadas a la api.
 */
interface PokeService {

    /**
     * Servicio encargado de traer los pokemons.
     */
    @GET("pokemon")
    suspend fun getSearchPokes(
        @Query("limit") limit: Number = ITEMS_PER_PAGE,
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
