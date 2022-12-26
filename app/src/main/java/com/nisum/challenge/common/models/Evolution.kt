package com.nisum.challenge.common.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Evolution(
    val id: String,
    val chain: Chain,
    val palParkEncounters: List<PalParkEncounter>
) : Parcelable

@Parcelize
data class Chain(
    @field:SerializedName("evolves_to") val evolvesTo: List<Chain>,
    val species: SpeciesName?
) : Parcelable

@Parcelize
data class SpeciesName(
    val name: String,
    val url: String
) : Parcelable