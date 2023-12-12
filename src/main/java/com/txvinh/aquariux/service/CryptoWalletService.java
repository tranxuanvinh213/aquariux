package com.txvinh.aquariux.service;

import com.txvinh.aquariux.constant.Crypto;
import com.txvinh.aquariux.domain.CryptoWallet;
import com.txvinh.aquariux.mapper.CryptoWalletMapper;
import com.txvinh.aquariux.repository.CryptoWalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CryptoWalletService {
    private final CryptoWalletRepository cryptoWalletRepository;
    private final UserService userService;
    
    public CryptoWallet create(CryptoWallet cryptoWallet) {
        var entity = cryptoWalletRepository.save(CryptoWalletMapper.INSTANCE.domainToEntity(cryptoWallet));
        return CryptoWalletMapper.INSTANCE.entityToDomain(entity);
    }
    
    public CryptoWallet getBalance () {
        var user = userService.getUserByEmail("vinhit213@gmail.com");
        var cryptoWallet = cryptoWalletRepository.findByUserIdAndType(user.getId(), Crypto.USDT);
        return CryptoWalletMapper.INSTANCE.entityToDomain(cryptoWallet);
    }
}
