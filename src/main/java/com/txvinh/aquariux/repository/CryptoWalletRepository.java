package com.txvinh.aquariux.repository;

import com.txvinh.aquariux.entity.CryptoWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CryptoWalletRepository extends JpaRepository<CryptoWallet, Long> {
    CryptoWallet findByUserIdAndType(Long userId, String type);
    List<CryptoWallet> findByUserId(Long userId);
}
