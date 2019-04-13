package com.robert.dd.doordashserver.repository;

import com.robert.dd.doordashserver.model.BaseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UpsertRepositoryImpl implements UpsertRepository<BaseModel,String> {

    private static final Logger logger = LoggerFactory.getLogger(UpsertRepository.class);
    @PersistenceContext
    private EntityManager em;

    @Override
    public <S extends BaseModel> S save(S entity) {
        logger.info("Save method called");
        if (entity.getId() == null) {
            this.em.persist(entity);
        } else {
            this.em.merge(entity);
        }

        return null;
    }
}
