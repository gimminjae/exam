package com.example.kotlinback.common.generator

import org.hibernate.HibernateException
import org.hibernate.MappingException
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.Configurable
import org.hibernate.id.IdentifierGenerator
import org.hibernate.internal.util.config.ConfigurationHelper
import org.hibernate.service.ServiceRegistry
import org.hibernate.type.Type
import java.io.Serializable
import java.time.LocalDate
import java.util.*

class IdGenerator : IdentifierGenerator, Configurable {
    private var entityType: String? = null

    @Throws(HibernateException::class)
    override fun generate(
        session: SharedSessionContractImplementor,
        `object`: Any
    ): Serializable {
        return "%s%s%s".formatted(
            LocalDate.now().toString().replace("-", ""),
            entityType,
            UUID.randomUUID().toString()
        )
    }

    @Throws(MappingException::class)
    override fun configure(
        type: Type,
        params: Properties,
        serviceRegistry: ServiceRegistry
    ) {
        entityType = ConfigurationHelper.getString(ENTITY_TYPE, params)
    }

    companion object {
        const val ENTITY_TYPE = "entityType"
    }
}
