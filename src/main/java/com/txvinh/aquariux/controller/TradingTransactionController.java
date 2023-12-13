package com.txvinh.aquariux.controller;

import com.txvinh.aquariux.domain.TradingTransaction;
import com.txvinh.aquariux.service.TradingTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/trading-transaction")
public class TradingTransactionController {
    private final TradingTransactionService tradingTransactionService;
    
    @GetMapping
    public List<TradingTransaction> getTransactionOfUser(@RequestParam String email) {
        return tradingTransactionService.getAllTransactionOfCurrentUser(email);
        
    }
    
}
