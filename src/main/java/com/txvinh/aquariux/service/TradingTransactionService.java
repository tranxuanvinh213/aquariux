package com.txvinh.aquariux.service;

import com.txvinh.aquariux.domain.TradingTransaction;
import com.txvinh.aquariux.mapper.TradingTransactionMapper;
import com.txvinh.aquariux.repository.TradingTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TradingTransactionService {
    private final TradingTransactionRepository tradingTransactionRepository;
    private final UserService userService;
    
    public TradingTransaction save(TradingTransaction tradingTransaction) {
        var entity = tradingTransactionRepository.save(TradingTransactionMapper.INSTANCE.domainToEntity(tradingTransaction));
        return TradingTransactionMapper.INSTANCE.entityToDomain(entity);
    }

    public List<TradingTransaction> getAllTransactionOfCurrentUser(String email) {
        var user  = userService.getUserByEmail(email);
        var tradingTransactions = tradingTransactionRepository.findByUserId(user.getId());
        return tradingTransactions.stream().map(TradingTransactionMapper.INSTANCE::entityToDomain).toList();
    }
}
