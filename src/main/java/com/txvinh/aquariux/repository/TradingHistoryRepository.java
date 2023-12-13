package com.txvinh.aquariux.repository;

import com.txvinh.aquariux.entity.TradingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradingHistoryRepository extends JpaRepository<TradingHistory, Long> {
    List<TradingHistory> findByUserId(Long userId);
}
