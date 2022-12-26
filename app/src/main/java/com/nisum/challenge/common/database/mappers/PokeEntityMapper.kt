package com.nisum.challenge.common.database.mappers

import com.nisum.challenge.common.database.entity.PokeEntity
import com.nisum.challenge.common.models.PokeModel

object PokeEntityMapper : EntityMapper<List<PokeModel>, List<PokeEntity>> {

    override fun asEntity(domain: List<PokeModel>): List<PokeEntity> {
        return domain.map { pokemon ->
            PokeEntity(
                name = pokemon.name,
                url = pokemon.url
            )
        }
    }

    override fun asDomain(entity: List<PokeEntity>): List<PokeModel> {
        return entity.map { pokemonEntity ->
            PokeModel(
                name = pokemonEntity.name,
                url = pokemonEntity.url
            )
        }
    }
}

fun List<PokeModel>.asEntity(): List<PokeEntity> = PokeEntityMapper.asEntity(this)

fun List<PokeEntity>.asDomain(): List<PokeModel> = PokeEntityMapper.asDomain(this)
