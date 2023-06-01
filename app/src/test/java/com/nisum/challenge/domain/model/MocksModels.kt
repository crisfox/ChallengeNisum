package com.nisum.challenge.domain.model

import com.nisum.challenge.data.database.entity.PokeInfoEntity

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