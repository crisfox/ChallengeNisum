package com.nisum.challenge.app.services

import com.nisum.challenge.common.models.Evolution
import com.nisum.challenge.common.models.PokeInfo
import com.nisum.challenge.common.models.ResultSearchModel
import com.nisum.challenge.common.models.Species
import com.nisum.challenge.common.networking.AppNetworkResult

interface IPokeApi {
    suspend fun get(): AppNetworkResult<ResultSearchModel>

    suspend fun getInfo(name: String): AppNetworkResult<PokeInfo>

    suspend fun getSpeciesInfo(id: String): AppNetworkResult<Species>

    suspend fun getEvolutionInfo(id: String): AppNetworkResult<Evolution>
}