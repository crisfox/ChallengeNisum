package com.nisum.challenge.common.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultSearchModel(
    @SerializedName("count") var totalCount: Int? = null,
    @SerializedName("results") var items: List<PokeModel> = listOf()
) : Parcelable
