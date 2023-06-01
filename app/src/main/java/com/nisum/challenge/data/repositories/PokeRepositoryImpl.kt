package com.nisum.challenge.data.repositories

import com.nisum.challenge.data.network.PokeService
import com.nisum.challenge.data.database.PokeDao
import com.nisum.challenge.data.database.mappers.asDomain
import com.nisum.challenge.data.database.mappers.asEntity
import com.nisum.challenge.domain.model.ResultSearchModel
import com.nisum.challenge.data.network.model.AppNetworkResult
import com.nisum.challenge.data.network.model.Loading
import com.nisum.challenge.data.network.model.Success
import com.nisum.challenge.data.network.Variables
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/**
 * Repositorio que trae la lista de pokemons.
 *
 * @property pokeService PokeApi para realizar el llamado a la api.
 * @property pokeDao PokeDao para realizar el llamado a la database del dispositivo.
 * @constructor
 */
class PokeRepositoryImpl(
    private val pokeService: PokeService,
    private val pokeDao: PokeDao
) : PokeRepository {

    override fun get(): Flow<AppNetworkResult<ResultSearchModel>> {
        return if (Variables.isNetworkConnected) {
            fetchPokeList()
        } else {
            val result = pokeDao.all()
            result.map { pokeEntity ->
                Success(ResultSearchModel(items = pokeEntity.asDomain()), 200)
            }
        }
    }

    private fun fetchPokeList() = flow {
        emit(Loading())
        val result = pokeService.get()
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
