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
    fun insertPokeInfo(pokeInfo: PokeInfoEntity)

    @Query("SELECT * FROM PokeInfoEntity WHERE name = :name_")
    fun getPokeInfo(name_: String): Flow<PokeInfoEntity?>
}
