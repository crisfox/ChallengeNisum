package com.nisum.challenge.common.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PokeDaoModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokeDao(): PokeDao

    companion object {
        fun newInstance(context: Context): AppDatabase {
            return Room
                .databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "POKE_DB"
                )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}