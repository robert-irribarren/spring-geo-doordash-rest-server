package com.robert.dd.doordashserver.repository;

import com.robert.dd.doordashserver.model.Address;
import com.robert.dd.doordashserver.model.CustomerOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerOrderRepository extends UpsertRepository<CustomerOrder,String>, PagingAndSortingRepository<CustomerOrder,String> {

    @Query("SELECT co FROM CustomerOrder co WHERE co.customer.id = :userId ORDER BY co.updatedAt")
    public CustomerOrder findLatestByUserId(@Param("userId") String userId);


    public List<CustomerOrder> findAllByCustomerId(String customerId);

}
