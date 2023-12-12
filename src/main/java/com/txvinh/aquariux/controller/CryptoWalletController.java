package com.txvinh.aquariux.controller;

import com.txvinh.aquariux.domain.CryptoWallet;
import com.txvinh.aquariux.service.CryptoWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("wallet")
public class CryptoWalletController {
    private final CryptoWalletService cryptoWalletService;
    
    @GetMapping("/balance")
    public CryptoWallet getBalance() {
        return cryptoWalletService.getBalance();
    }
}
