package com.robert.dd.doordashserver.repository;

import com.robert.dd.doordashserver.model.Address;
import com.robert.dd.doordashserver.model.CustomerOrder;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerOrderRepository extends UpsertRepository<CustomerOrder,String>, PagingAndSortingRepository<CustomerOrder,String> {


}
