package com.nisum.challenge.home.repositories

import com.nisum.challenge.common.database.PokeInfoDao
import com.nisum.challenge.common.database.mappers.asDomain
import com.nisum.challenge.common.database.mappers.asEntity
import com.nisum.challenge.common.getLasPath
import com.nisum.challenge.common.models.PokeInfo
import com.nisum.challenge.common.networking.AppNetworkResult
import com.nisum.challenge.common.networking.Loading
import com.nisum.challenge.common.networking.Success
import com.nisum.challenge.common.networking.Unsuccessful
import com.nisum.challenge.common.networking.Variables
import com.nisum.challenge.home.services.PokeApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/**
 * Repositorio que trae los proyectos de github.
 *
 * @property pokeApi RepoService para realizar el llamado
 * @constructor
 */
class PokeInfoRepository(
    private val pokeApi: PokeApi,
    private val pokeInfoDao: PokeInfoDao
) : IPokeInfoRepository {

    override suspend fun getInfo(id: String, name: String): Flow<AppNetworkResult<PokeInfo>> {
        return if (Variables.isNetworkConnected) {
            fetchPokeInfo(id, name)
        } else {
            val result = pokeInfoDao.getPokeInfo(name)
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
        val resultEvolution = pokeApi.getEvolutionInfo(resultSpecies.data?.evolutionChain?.url?.getLasPath() ?: "")

        if (result is Success && resultSpecies is Success && resultEvolution is Success) {
            result.data.species = resultSpecies.data
            result.data.evolution = resultEvolution.data
            pokeInfoDao.insertPokeInfo(result.data.asEntity())
            emit(result)
        } else {
            emit(Unsuccessful(code = 400, error = "Error database", message = "Not found poke info."))
        }
    }
}
