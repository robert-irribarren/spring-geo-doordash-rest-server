package com.robert.dd.doordashserver.repository;

import com.robert.dd.doordashserver.model.Address;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface AddressRepository extends UpsertRepository<Address,String>, PagingAndSortingRepository<Address,String> {

    @Query(value = "SELECT addr.* FROM Address addr inner join CustomerAddressMap ca ON addr.id=ca.address_id "+
            " WHERE ca.customer_id = :custId",
    nativeQuery = true)
    List<Address> getAddressesByCustomerId(Pageable page, @Param("custId") String id);
}
