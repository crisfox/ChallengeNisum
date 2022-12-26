package com.nisum.challenge.app.repositories

import androidx.core.net.toUri
import com.nisum.challenge.app.services.IPokeApi
import com.nisum.challenge.common.database.PokeInfoDao
import com.nisum.challenge.common.database.mappers.asDomain
import com.nisum.challenge.common.database.mappers.asEntity
import com.nisum.challenge.common.models.PokeInfo
import com.nisum.challenge.common.networking.AppNetworkResult
import com.nisum.challenge.common.networking.Loading
import com.nisum.challenge.common.networking.Success
import com.nisum.challenge.common.networking.Unsuccessful
import com.nisum.challenge.common.networking.Variables
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/**
 * Repositorio que trae la información detallada de los pokemons.
 *
 * @property pokeApi PokeApi para realizar el llamado a la api.
 * @property pokeInfoDao PokeDao para realizar el llamado a la database del dispositivo.
 * @constructor
 */
class PokeInfoRepository(
    private val pokeApi: IPokeApi,
    private val pokeInfoDao: PokeInfoDao
) : IPokeInfoRepository {

    override suspend fun getInfo(id: String, name: String): Flow<AppNetworkResult<PokeInfo>> {
        return if (Variables.isNetworkConnected) {
            fetchPokeInfo(id, name)
        } else {
            val result = pokeInfoDao.get(name)
            result.map {
                it?.let {
                    Success(it.asDomain(), 200)
                } ?: Unsuccessful(code = 400, error = "Error database", message = "Not found poke info.")
            }
        }
    }

    private suspend fun fetchPokeInfo(id: String, name: String) = flow {
        emit(Loading())
        val result = pokeApi.getInfo(name)
        val resultSpecies = pokeApi.getSpeciesInfo(id)
        val resultEvolution =
            pokeApi.getEvolutionInfo(resultSpecies.data?.evolutionChain?.url?.toUri()?.lastPathSegment ?: "")

        if (result is Success && resultSpecies is Success && resultEvolution is Success) {
            result.data.species = resultSpecies.data
            result.data.evolution = resultEvolution.data
            pokeInfoDao.insertOne(result.data.asEntity())
            emit(result)
        } else {
            emit(Unsuccessful(code = 400, error = "Error network", message = "Not found poke info."))
        }
    }
}
