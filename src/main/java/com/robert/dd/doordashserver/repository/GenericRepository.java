package com.robert.dd.doordashserver.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface GenericRepository<T,U> extends CrudRepository<T, U> {

    @Override
    List<T> findAll();

}