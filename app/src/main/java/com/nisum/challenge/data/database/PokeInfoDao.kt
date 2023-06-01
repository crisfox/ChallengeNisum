package com.nisum.challenge.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nisum.challenge.data.database.entity.PokeInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokeInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(item: PokeInfoEntity)

    @Query("SELECT * FROM PokeInfoEntity WHERE name = :name")
    fun get(name: String): Flow<PokeInfoEntity?>
}
