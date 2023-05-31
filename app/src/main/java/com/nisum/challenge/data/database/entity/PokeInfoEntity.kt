package com.nisum.challenge.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nisum.challenge.data.model.Abilities
import com.nisum.challenge.data.model.BaseStat
import com.nisum.challenge.data.model.Evolution
import com.nisum.challenge.data.model.Species
import com.nisum.challenge.data.model.TypeResponse

@Entity
data class PokeInfoEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val baseExperience: Int,
    val types: List<TypeResponse>,
    val evolution: Evolution?,
    val abilities: List<Abilities>,
    val stats: List<BaseStat>,
    val species: Species?
)
