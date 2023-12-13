package com.txvinh.aquariux.service;

import com.txvinh.aquariux.constant.Crypto;
import com.txvinh.aquariux.domain.CryptoWallet;
import com.txvinh.aquariux.mapper.CryptoWalletMapper;
import com.txvinh.aquariux.repository.CryptoWalletRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CryptoWalletService {
    private final CryptoWalletRepository cryptoWalletRepository;
    private final UserService userService;
    
    public CryptoWallet create(CryptoWallet cryptoWallet) {
        var entity = cryptoWalletRepository.save(CryptoWalletMapper.INSTANCE.domainToEntity(cryptoWallet));
        return CryptoWalletMapper.INSTANCE.entityToDomain(entity);
    }

    public CryptoWallet update(CryptoWallet cryptoWallet) {
        var entity = cryptoWalletRepository.findById(cryptoWallet.getId());
        if (entity.isEmpty()) {
            throw new EntityNotFoundException("Crypto Wallet not found");
        }
        com.txvinh.aquariux.entity.CryptoWallet cryptoWalletEntity = CryptoWalletMapper.INSTANCE.domainToEntity(cryptoWallet);

        var updatedEntity = cryptoWalletRepository.save(cryptoWalletEntity);
        return CryptoWalletMapper.INSTANCE.entityToDomain(updatedEntity);
    }
    
    public CryptoWallet getBalance () {
        var user = userService.getUserByEmail("vinhit213@gmail.com");
        var cryptoWallet = cryptoWalletRepository.findByUserIdAndType(user.getId(), Crypto.USDT);
        return CryptoWalletMapper.INSTANCE.entityToDomain(cryptoWallet);
    }

    public CryptoWallet getWalletByType (String type) {
        var user = userService.getUserByEmail("vinhit213@gmail.com");
        var cryptoWallet = cryptoWalletRepository.findByUserIdAndType(user.getId(), type);
        return CryptoWalletMapper.INSTANCE.entityToDomain(cryptoWallet);
    }

    public List<CryptoWallet> getWalletByUserID(String email) {
        var user = userService.getUserByEmail(email);
        var cryptoWallet = cryptoWalletRepository.findByUserId(user.getId());
        return cryptoWallet.stream().map(CryptoWalletMapper.INSTANCE::entityToDomain).toList();
    }
}
