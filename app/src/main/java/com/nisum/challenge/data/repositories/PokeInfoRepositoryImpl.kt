package com.nisum.challenge.data.repositories

import androidx.core.net.toUri
import com.nisum.challenge.data.network.PokeService
import com.nisum.challenge.data.database.PokeInfoDao
import com.nisum.challenge.data.database.mappers.asDomain
import com.nisum.challenge.data.database.mappers.asEntity
import com.nisum.challenge.data.model.PokeInfo
import com.nisum.challenge.data.network.model.AppNetworkResult
import com.nisum.challenge.data.network.model.Loading
import com.nisum.challenge.data.network.model.Success
import com.nisum.challenge.data.network.model.Unsuccessful
import com.nisum.challenge.data.network.Variables
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/**
 * Repositorio que trae la información detallada de los pokemons.
 *
 * @property pokeService PokeApi para realizar el llamado a la api.
 * @property pokeInfoDao PokeDao para realizar el llamado a la database del dispositivo.
 * @constructor
 */
class PokeInfoRepositoryImpl(
    private val pokeService: PokeService,
    private val pokeInfoDao: PokeInfoDao
) : PokeInfoRepository {

    override fun getInfo(id: String, name: String): Flow<AppNetworkResult<PokeInfo>> {
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

    private fun fetchPokeInfo(id: String, name: String) = flow {
        emit(Loading())
        val result = pokeService.getInfo(name)
        val resultSpecies = pokeService.getSpeciesInfo(id)
        val resultEvolution =
            pokeService.getEvolutionInfo(resultSpecies.data?.evolutionChain?.url?.toUri()?.lastPathSegment ?: "")

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
