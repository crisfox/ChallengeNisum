package com.nisum.challenge.common.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nisum.challenge.common.database.entity.PokeEntity

@Dao
interface PokeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(list: List<PokeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: PokeEntity)

    @Query("SELECT * FROM PokeEntity")
    fun all(): List<PokeEntity>

    @Query("DELETE FROM PokeEntity")
    fun deleteAll()
}