package com.robert.dd.doordashserver.repository;

import com.robert.dd.doordashserver.model.MerchantProduct;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MerchantProductItemRepository extends UpsertRepository<MerchantProduct,String>, PagingAndSortingRepository<MerchantProduct,String> {

    @Query("SELECT mpi FROM MerchantProduct mpi WHERE mpi.merchantId = :merchId")
    List<MerchantProduct> getProductsForMerchant(Pageable page, @Param("merchId") String id);
}
