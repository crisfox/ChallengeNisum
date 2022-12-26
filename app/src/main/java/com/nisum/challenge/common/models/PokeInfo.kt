package com.nisum.challenge.common.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

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
    val hp: Int = Random.nextInt(maxHp),
    val attack: Int = Random.nextInt(maxAttack),
    val defense: Int = Random.nextInt(maxDefense),
    val speed: Int = Random.nextInt(maxSpeed),
    val exp: Int = Random.nextInt(maxExp),
    var evolution: Evolution? = null,
    var species: Species? = null
) : Parcelable {

    companion object {
        const val maxHp = 300
        const val maxAttack = 300
        const val maxDefense = 300
        const val maxSpeed = 300
        const val maxExp = 1000
    }
}

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

fun PokeInfo.getIdString() = String.format("#%03d", id)
fun PokeInfo.getWeightString() = String.format("%.1f KG", weight.toFloat() / 10)
fun PokeInfo.getHeightString() = String.format("%.1f M", height.toFloat() / 10)
fun PokeInfo.getHpString() = " $hp/${PokeInfo.maxHp}"
fun PokeInfo.getAttackString() = " $attack/${PokeInfo.maxAttack}"
fun PokeInfo.getDefenseString() = " $defense/${PokeInfo.maxDefense}"
fun PokeInfo.getSpeedString() = " $speed/${PokeInfo.maxSpeed}"
fun PokeInfo.getExpString() = " $exp/${PokeInfo.maxExp}"