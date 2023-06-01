package com.nisum.challenge.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Abilities(
    val ability: Ability
) : Parcelable

@Parcelize
data class Ability(
    val name: String,
) : Parcelable
