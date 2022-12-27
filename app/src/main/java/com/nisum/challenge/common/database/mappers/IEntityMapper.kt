package com.nisum.challenge.common.database.mappers

interface IEntityMapper<Domain, Entity> {

    fun asEntity(domain: Domain): Entity

    fun asDomain(entity: Entity): Domain
}
