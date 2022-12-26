package com.nisum.challenge.common.networking

import com.nisum.challenge.common.database.entity.PokeInfoEntity
import com.nisum.challenge.common.models.Chain
import com.nisum.challenge.common.models.Evolution
import com.nisum.challenge.common.models.EvolutionChain
import com.nisum.challenge.common.models.Species

fun mockPokeInfoEntity() = PokeInfoEntity(
    1,
    "test",
    2,
    3,
    4,
    listOf(),
    null,
    listOf(),
    listOf(),
    null
)

fun mockEvolution() = Evolution(
    "1",
    Chain(
        listOf(),
        null
    ),
    listOf()
)

fun mockSpecies() = Species(
    "1",
    EvolutionChain(
        "https://test.test/url/1/"
    ),
    listOf()
)