package com.robert.dd.doordashserver.repository;

import com.robert.dd.doordashserver.model.Merchant;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface MerchantRepository extends GenericRepository<Merchant,String> {
    @Query(value = "SELECT merch FROM Merchant merch WHERE within(merch.address.location, :bounds) = true")
    List<Merchant> findAllNearby(@Param("bounds") Geometry bounds);
}
