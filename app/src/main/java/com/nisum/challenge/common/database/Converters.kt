package com.nisum.challenge.common.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.nisum.challenge.common.models.Abilities
import com.nisum.challenge.common.models.BaseStat
import com.nisum.challenge.common.models.Evolution
import com.nisum.challenge.common.models.Species
import com.nisum.challenge.common.models.TypeResponse

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
    fun jsonStringToEvolution(value: String) = Gson().fromJson(value, Evolution::class.java)

    @TypeConverter
    fun speciesToJsonString(value: Species?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToSpecies(value: String) = Gson().fromJson(value, Species::class.java)

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
