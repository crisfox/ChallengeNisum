package com.nisum.challenge.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.nisum.challenge.domain.model.Abilities
import com.nisum.challenge.domain.model.BaseStat
import com.nisum.challenge.domain.model.Evolution
import com.nisum.challenge.domain.model.Species
import com.nisum.challenge.domain.model.TypeResponse

/**
 * Soporte para mapear la información guardada en database como json.
 */
class Converters {

    @TypeConverter
    fun listToJsonString(value: List<TypeResponse>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String) = Gson()
        .fromJson(value, Array<TypeResponse>::class.java)
        .toList()

    @TypeConverter
    fun evolutionToJsonString(value: Evolution?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToEvolution(value: String): Evolution = Gson().fromJson(value, Evolution::class.java)

    @TypeConverter
    fun speciesToJsonString(value: Species?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToSpecies(value: String): Species = Gson().fromJson(value, Species::class.java)

    @TypeConverter
    fun listAbilitiesToJsonString(value: List<Abilities>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToListAbilities(value: String) = Gson()
        .fromJson(value, Array<Abilities>::class.java)
        .toList()

    @TypeConverter
    fun listBaseStatToJsonString(value: List<BaseStat>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToListBaseStat(value: String) = Gson()
        .fromJson(value, Array<BaseStat>::class.java)
        .toList()
}
