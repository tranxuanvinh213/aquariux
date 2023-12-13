package com.txvinh.aquariux.repository;

import com.txvinh.aquariux.entity.TradingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradingTransactionRepository extends JpaRepository<TradingTransaction, Long> {
    List<TradingTransaction> findByUserId(Long userId);
}
