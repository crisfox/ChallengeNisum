package com.nisum.challenge.common.models

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