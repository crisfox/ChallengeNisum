package com.nisum.challenge.app.repositories

import com.nisum.challenge.common.models.PokeInfo
import com.nisum.challenge.common.networking.AppNetworkResult
import kotlinx.coroutines.flow.Flow

interface IPokeInfoRepository {
    suspend fun getInfo(id: String, name: String): Flow<AppNetworkResult<PokeInfo>>
}