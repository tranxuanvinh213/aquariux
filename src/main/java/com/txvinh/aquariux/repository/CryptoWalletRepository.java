package com.txvinh.aquariux.repository;

import com.txvinh.aquariux.entity.CryptoWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoWalletRepository extends JpaRepository<CryptoWallet, Long> {
    CryptoWallet findByUserIdAndType(Long userId, String type);
}
