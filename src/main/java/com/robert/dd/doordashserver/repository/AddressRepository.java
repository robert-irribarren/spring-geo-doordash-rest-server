package com.robert.dd.doordashserver.repository;

import com.robert.dd.doordashserver.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address,String>{
}
