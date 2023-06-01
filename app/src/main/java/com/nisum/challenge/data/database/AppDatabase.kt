package com.nisum.challenge.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nisum.challenge.data.database.entity.PokeEntity
import com.nisum.challenge.data.database.entity.PokeInfoEntity

@Database(entities = [PokeEntity::class, PokeInfoEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokeDao(): PokeDao
    abstract fun pokeInfoDao(): PokeInfoDao

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
