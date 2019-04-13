package com.robert.dd.doordashserver.repository;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UpsertRepository<T,U> {
    <S extends T> S save(S entity);
}