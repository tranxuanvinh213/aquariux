package com.txvinh.aquariux.repository;

import com.txvinh.aquariux.entity.PriceAggregation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceAggregateRepository extends JpaRepository<PriceAggregation, Long> {
    @Query("SELECT pa FROM PriceAggregation pa WHERE pa.createdDate = (SELECT MAX(pat.createdDate) FROM PriceAggregation pat)")
    Optional<PriceAggregation> findLatestPriceAggregation();
}
