package com.nisum.challenge.common.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PokeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(list: List<PokeDaoModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: PokeDaoModel)

    @Query("SELECT * FROM PokeDaoModel")
    fun all(): Flow<List<PokeDaoModel>>

    @Query("DELETE FROM PokeDaoModel")
    fun deleteAll()
}