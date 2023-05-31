package com.nisum.challenge.data.repositories

import com.nisum.challenge.data.model.ResultSearchModel
import com.nisum.challenge.data.network.model.AppNetworkResult
import kotlinx.coroutines.flow.Flow

interface PokeRepository {
    fun get(): Flow<AppNetworkResult<ResultSearchModel>>
}