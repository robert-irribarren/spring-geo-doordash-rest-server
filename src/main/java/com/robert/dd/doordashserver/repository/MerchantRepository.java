package com.robert.dd.doordashserver.repository;

import com.robert.dd.doordashserver.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant,String>{
}
