package com.nisum.challenge.common.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ability(
    val name: String,
) : Parcelable