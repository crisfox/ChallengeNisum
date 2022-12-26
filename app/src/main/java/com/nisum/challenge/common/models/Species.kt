package com.nisum.challenge.common.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Species(
    val id: String,
    @field:SerializedName("evolution_chain") val evolutionChain: EvolutionChain,
    @field:SerializedName("pal_park_encounters") val palParkEncounters: List<PalParkEncounter>
) : Parcelable

@Parcelize
data class EvolutionChain(
    val url: String
) : Parcelable

@Parcelize
data class PalParkEncounter(
    val area: Area
) : Parcelable

@Parcelize
data class Area(
    val name: String
) : Parcelable
