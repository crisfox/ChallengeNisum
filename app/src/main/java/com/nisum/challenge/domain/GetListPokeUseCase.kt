package com.nisum.challenge.domain

import com.nisum.challenge.domain.model.ResultSearchModel
import com.nisum.challenge.data.network.model.AppNetworkResult
import com.nisum.challenge.data.repositories.PokeRepository
import kotlinx.coroutines.flow.Flow

class GetListPokeUseCase(
    private val repository: PokeRepository,
) {
    operator fun invoke(): Flow<AppNetworkResult<ResultSearchModel>> {
        return repository.get()
    }
}