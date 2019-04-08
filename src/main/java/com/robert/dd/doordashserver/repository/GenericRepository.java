package com.robert.dd.doordashserver.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface GenericRepository<T,U> extends PagingAndSortingRepository<T, U> {

}