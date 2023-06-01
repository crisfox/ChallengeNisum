package com.nisum.challenge.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokeEntity(
    @PrimaryKey
    val name: String,
    val url: String
)
