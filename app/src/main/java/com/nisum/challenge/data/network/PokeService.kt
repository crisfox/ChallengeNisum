package com.nisum.challenge.data.network

import com.nisum.challenge.data.model.Evolution
import com.nisum.challenge.data.model.PokeInfo
import com.nisum.challenge.data.model.ResultSearchModel
import com.nisum.challenge.data.model.Species
import com.nisum.challenge.data.network.model.AppNetworkResult

interface PokeService {
    suspend fun get(): AppNetworkResult<ResultSearchModel>

    suspend fun getInfo(name: String): AppNetworkResult<PokeInfo>

    suspend fun getSpeciesInfo(id: String): AppNetworkResult<Species>

    suspend fun getEvolutionInfo(id: String): AppNetworkResult<Evolution>
}