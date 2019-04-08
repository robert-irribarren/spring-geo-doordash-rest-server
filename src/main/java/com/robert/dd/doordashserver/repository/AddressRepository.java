package com.robert.dd.doordashserver.repository;

import com.robert.dd.doordashserver.model.Address;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AddressRepository extends GenericRepository<Address,String> {
}
