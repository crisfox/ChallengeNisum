package com.nisum.challenge.common.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokeModel(
    @SerializedName("name") var name: String,
    @SerializedName("url") var url: String
) : Parcelable