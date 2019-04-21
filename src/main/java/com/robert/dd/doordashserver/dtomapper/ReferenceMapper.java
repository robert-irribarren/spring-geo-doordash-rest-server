package com.robert.dd.doordashserver.dtomapper;

import org.dom4j.tree.AbstractEntity;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class ReferenceMapper {
 
    @PersistenceContext
    private EntityManager em;
 
    @ObjectFactory
    public <T extends AbstractEntity> T resolve(AbstractDto sourceDto, @TargetType Class<T> type) {
        T entity = em.find( type, sourceDto.getId() );
        return entity != null ? entity : type.newInstance();
    }
}