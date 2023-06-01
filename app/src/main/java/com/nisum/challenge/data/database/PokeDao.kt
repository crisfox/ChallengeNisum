package com.nisum.challenge.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nisum.challenge.data.database.entity.PokeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(list: List<PokeEntity>)

    @Query("SELECT * FROM PokeEntity")
    fun all(): Flow<List<PokeEntity>>

    @Query("DELETE FROM PokeEntity")
    fun deleteAll()
}
