package com.nisum.challenge.app.repositories

import com.nisum.challenge.common.models.ResultSearchModel
import com.nisum.challenge.common.networking.AppNetworkResult
import kotlinx.coroutines.flow.Flow

interface IPokeRepository {
    suspend fun get(): Flow<AppNetworkResult<ResultSearchModel>>
}