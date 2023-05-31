package com.nisum.challenge.data.database

import com.nisum.challenge.data.database.entity.PokeEntity
import com.nisum.challenge.data.database.entity.PokeInfoEntity
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow

class FakePokemonDao : PokeDao, PokeInfoDao {

    private val channelList = Channel<List<PokeEntity>>(1)
    private val channelInfo = Channel<PokeInfoEntity>(1)

    private val data = mutableListOf<PokeEntity>()
    var dataInfo: PokeInfoEntity? = null

    override fun insert(list: List<PokeEntity>) {
        data.addAll(list.toList())
        invalidate()
    }

    override fun insertOne(item: PokeInfoEntity) {
        dataInfo = item
        invalidate()
    }

    override fun all(): Flow<List<PokeEntity>> = channelList.receiveAsFlow()

    override fun deleteAll() {
        data.clear()
        dataInfo = null
        invalidate()
    }

    private fun invalidate() {
        channelList.trySend(data)
        dataInfo?.let { channelInfo.trySend(it) }
    }

    override fun get(name_: String): Flow<PokeInfoEntity> = flow { dataInfo }
}
