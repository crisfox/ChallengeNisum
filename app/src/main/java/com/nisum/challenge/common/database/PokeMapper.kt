package com.nisum.challenge.common.database

import com.nisum.challenge.common.models.PokeModel

fun PokeDaoModel.toDomainModel(): PokeModel {
    return PokeModel(
        name, url
    )
}

fun PokeModel.toDaoModel(): PokeDaoModel {
    return PokeDaoModel(
        name, url
    )
}
