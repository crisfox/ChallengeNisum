package com.nisum.challenge.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokeInfo(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val baseExperience: Int,
    val types: List<TypeResponse>,
    val stats: List<BaseStat>,
    val abilities: List<Abilities>,
    var evolution: Evolution? = null,
    var species: Species? = null
) : Parcelable

@Parcelize
data class BaseStat(
    @field:SerializedName("base_stat") val baseStat: Int,
    val stat: Stat
) : Parcelable

@Parcelize
data class Stat(
    val name: String,
) : Parcelable

@Parcelize
data class TypeResponse(
    val slot: Int,
    val type: Type
) : Parcelable

@Parcelize
data class Type(
    val name: String
) : Parcelable

fun PokeInfo.getWeightString() = String.format("%.1f KG", weight.toFloat() / 10)
fun PokeInfo.getHeightString() = String.format("%.1f M", height.toFloat() / 10)
