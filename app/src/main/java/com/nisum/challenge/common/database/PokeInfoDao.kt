package com.nisum.challenge.common.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nisum.challenge.common.database.entity.PokeInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokeInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(item: PokeInfoEntity)

    @Query("SELECT * FROM PokeInfoEntity WHERE name = :name_")
    fun get(name_: String): Flow<PokeInfoEntity?>
}
