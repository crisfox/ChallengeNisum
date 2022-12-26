package com.nisum.challenge.common.database.mappers

import com.nisum.challenge.common.database.entity.PokeInfoEntity
import com.nisum.challenge.common.models.PokeInfo

object PokemonInfoIEntityMapper : IEntityMapper<PokeInfo, PokeInfoEntity> {

    override fun asEntity(domain: PokeInfo): PokeInfoEntity {
        return PokeInfoEntity(
            id = domain.id,
            name = domain.name,
            height = domain.height,
            weight = domain.weight,
            baseExperience = domain.baseExperience,
            types = domain.types,
            evolution = domain.evolution,
            species = domain.species,
            abilities = domain.abilities,
            stats = domain.stats
        )
    }

    override fun asDomain(entity: PokeInfoEntity): PokeInfo {
        return PokeInfo(
            id = entity.id,
            name = entity.name,
            height = entity.height,
            weight = entity.weight,
            baseExperience = entity.baseExperience,
            types = entity.types,
            evolution = entity.evolution,
            species = entity.species,
            abilities = entity.abilities,
            stats = entity.stats
        )
    }
}

fun PokeInfo.asEntity() = PokemonInfoIEntityMapper.asEntity(this)

fun PokeInfoEntity.asDomain() = PokemonInfoIEntityMapper.asDomain(this)
