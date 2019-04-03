package com.robert.dd.doordashserver.repository;

import com.robert.dd.doordashserver.model.Merchant;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MerchantRepository extends GenericRepository<Merchant,String> {

    @Override
    @Query("SELECT merch FROM Merchant merch, Address addr left join fetch merch.address")
    List<Merchant> findAll();

    @Override
    @Query("SELECT merch FROM Merchant merch left join fetch merch.address WHERE merch.id = :id")
    Optional<Merchant> findById(@Param("id") String id);

    /*@Query("SELECT tmp.merch FROM (SELECT merch,(ST_Length("+
            "LineString(" +
            "addr.location," +
            "ST_GeomFromText('POINT(:pLat :pLon)')))) AS distance" +
    "FROM Merchant merch, Address addr "+
    "WHERE merch.address_id=addr.id ORDER BY distance ASC) tmp")*/
    /*@Query( "SELECT ST_Distance_Sphere( ST_GeomFromText('POINT(37.7654262 -122.410096)'),ST_GeomFromText('POINT(37.7654262 -122.410096)')) FROM Merchant merch left join fetch merch.address")
    List<Object[]> findClosest(String lat, String lon);*/
    @Query(value = "SELECT merch FROM Merchant merch left join fetch merch.address WHERE within(merch.address.location, :bounds) = true")
    List<Merchant> findAllNearby(@Param("bounds") Geometry bounds);
}
