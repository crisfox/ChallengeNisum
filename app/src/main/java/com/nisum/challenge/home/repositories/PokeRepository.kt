package com.nisum.challenge.home.repositories

import com.nisum.challenge.common.database.PokeDao
import com.nisum.challenge.common.database.mappers.asDomain
import com.nisum.challenge.common.database.mappers.asEntity
import com.nisum.challenge.common.models.ResultSearchModel
import com.nisum.challenge.common.networking.AppNetworkResult
import com.nisum.challenge.common.networking.Loading
import com.nisum.challenge.common.networking.Success
import com.nisum.challenge.common.networking.Variables
import com.nisum.challenge.home.services.PokeApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Repositorio que trae los proyectos de github.
 *
 * @property pokeApi RepoService para realizar el llamado
 * @constructor
 */
class PokeRepository(
    private val pokeApi: PokeApi,
    private val pokeDao: PokeDao
) : IPokeRepository {

    override suspend fun get(): Flow<AppNetworkResult<ResultSearchModel>> {
        return if (Variables.isNetworkConnected) {
            fetchPokeList()
        } else {
            val result = pokeDao.all()
            flow { Success(ResultSearchModel(items = result.asDomain()), 200) }
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
