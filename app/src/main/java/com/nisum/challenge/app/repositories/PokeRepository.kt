package com.nisum.challenge.app.repositories

import com.nisum.challenge.app.services.IPokeApi
import com.nisum.challenge.common.database.PokeDao
import com.nisum.challenge.common.database.mappers.asDomain
import com.nisum.challenge.common.database.mappers.asEntity
import com.nisum.challenge.common.models.ResultSearchModel
import com.nisum.challenge.common.networking.AppNetworkResult
import com.nisum.challenge.common.networking.Loading
import com.nisum.challenge.common.networking.Success
import com.nisum.challenge.common.networking.Variables
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/**
 * Repositorio que trae la lista de pokemons.
 *
 * @property pokeApi PokeApi para realizar el llamado a la api.
 * @property pokeDao PokeDao para realizar el llamado a la database del dispositivo.
 * @constructor
 */
class PokeRepository(
    private val pokeApi: IPokeApi,
    private val pokeDao: PokeDao
) : IPokeRepository {

    override suspend fun get(): Flow<AppNetworkResult<ResultSearchModel>> {
        return if (Variables.isNetworkConnected) {
            fetchPokeList()
        } else {
            val result = pokeDao.all()
            result.map { pokeEntity ->
                Success(ResultSearchModel(items = pokeEntity.asDomain()), 200)
            }
        }
    }

    private suspend fun fetchPokeList() = flow {
        emit(Loading())
        val result = pokeApi.get()
        if (result is Success) {
            pokeDao.deleteAll()
            pokeDao.insert(result.data.items.asEntity())
        }
        emit(result)
    }

    companion object {
        const val ITEMS_PER_PAGE = 150
    }
}
