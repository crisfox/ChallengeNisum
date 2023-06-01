package com.nisum.challenge.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultSearchModel(
    @SerializedName("count") var totalCount: Int? = null,
    @SerializedName("results") var items: List<PokeModel> = listOf()
) : Parcelable

@Parcelize
data class PokeModel(
    @SerializedName("name") var name: String,
    @SerializedName("url") var url: String
) : Parcelable
