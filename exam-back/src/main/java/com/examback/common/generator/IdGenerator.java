package com.examback.common.generator;

import com.sun.jdi.LongType;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.enhanced.TableGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Properties;
import java.util.UUID;

public class IdGenerator implements IdentifierGenerator, Configurable {
    public static final String ENTITY_TYPE = "entityType";
    private String entityType;

    @Override
    public Serializable generate(SharedSessionContractImplementor session,
                                 Object object) throws HibernateException {
        return "%s%s%s".formatted(
                LocalDate.now().toString().replace("-", ""),
                this.entityType,
                UUID.randomUUID().toString()
        );
    }
    @Override
    public void configure(Type type,
                          Properties params,
                          ServiceRegistry serviceRegistry) throws MappingException {
        this.entityType = ConfigurationHelper.getString(ENTITY_TYPE, params);
    }
}
