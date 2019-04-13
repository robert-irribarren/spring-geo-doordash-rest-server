package com.robert.dd.doordashserver.repository;

import com.robert.dd.doordashserver.model.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CustomerRepository extends UpsertRepository<Customer,String>, PagingAndSortingRepository<Customer,String> {
}
