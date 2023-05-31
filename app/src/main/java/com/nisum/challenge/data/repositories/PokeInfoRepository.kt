package com.nisum.challenge.data.repositories

import com.nisum.challenge.data.model.PokeInfo
import com.nisum.challenge.data.network.model.AppNetworkResult
import kotlinx.coroutines.flow.Flow

interface PokeInfoRepository {
    fun getInfo(id: String, name: String): Flow<AppNetworkResult<PokeInfo>>
}