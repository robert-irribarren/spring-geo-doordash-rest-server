package com.robert.dd.doordashserver.repository;

import com.robert.dd.doordashserver.model.Merchant;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MerchantRepository extends UpsertRepository<Merchant,String>, PagingAndSortingRepository<Merchant, String> {
    @Query(value = "SELECT merch FROM Merchant merch WHERE within(merch.address.location, :bounds) = true")
    List<Merchant> findAllNearby(Pageable page, @Param("bounds") Geometry bounds);

    @Query(value = "SELECT merch FROM Merchant merch WHERE merch.name = :name AND merch.address.address1 = :address")
    Merchant findMerchantByNameAddress(@Param("name") String name, @Param("address") String address);
}
