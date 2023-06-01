package com.nisum.challenge.domain

import com.nisum.challenge.domain.model.PokeInfo
import com.nisum.challenge.data.network.model.AppNetworkResult
import com.nisum.challenge.data.repositories.PokeInfoRepository
import kotlinx.coroutines.flow.Flow

class GetInfoPokeUseCase(
    private val repository: PokeInfoRepository,
) {
    operator fun invoke(id: String, name: String): Flow<AppNetworkResult<PokeInfo>> {
        return repository.getInfo(id, name)
    }
}