package com.nisum.challenge.data.network

import android.content.res.Resources
import com.nisum.challenge.core.execute
import com.nisum.challenge.domain.model.Evolution
import com.nisum.challenge.domain.model.PokeInfo
import com.nisum.challenge.domain.model.ResultSearchModel
import com.nisum.challenge.domain.model.Species
import com.nisum.challenge.data.network.model.AppNetworkResult

/**
 * Api que intercepta las llamadas a la api para mapear la respuesta y asignarle un estado.
 *
 * @property service PokeService servicio de la api.
 * @property resources Resources para obtener los string del res.
 */
class PokeServiceImpl(private val service: PokeApi, val resources: Resources) : PokeService {

    override suspend fun get(): AppNetworkResult<ResultSearchModel> {
        return execute(resources) { service.getSearchPokes() }
    }

    override suspend fun getInfo(name: String): AppNetworkResult<PokeInfo> {
        return execute(resources) { service.getPokeInfo(name) }
    }

    override suspend fun getSpeciesInfo(id: String): AppNetworkResult<Species> {
        return execute(resources) { service.getSpeciesInfo(id) }
    }

    override suspend fun getEvolutionInfo(id: String): AppNetworkResult<Evolution> {
        return execute(resources) { service.getEvolutionInfo(id) }
    }
}
