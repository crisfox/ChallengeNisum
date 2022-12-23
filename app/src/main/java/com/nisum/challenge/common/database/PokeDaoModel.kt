package com.nisum.challenge.common.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokeDaoModel(
    @PrimaryKey
    val name: String,
    val url: String
)