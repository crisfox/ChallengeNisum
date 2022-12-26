package com.nisum.challenge.common.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nisum.challenge.common.models.Abilities
import com.nisum.challenge.common.models.BaseStat
import com.nisum.challenge.common.models.Evolution
import com.nisum.challenge.common.models.Species
import com.nisum.challenge.common.models.TypeResponse

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
