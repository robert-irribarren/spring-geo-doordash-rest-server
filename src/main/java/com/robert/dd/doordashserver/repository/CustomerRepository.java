package com.robert.dd.doordashserver.repository;

import com.robert.dd.doordashserver.model.Customer;
import com.robert.dd.doordashserver.model.Merchant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CustomerRepository extends GenericRepository<Customer,String>{

    @Override
    @Query("SELECT customer FROM Customer customer, Address addr left join fetch customer.addresses")
    List<Customer> findAll();
}
