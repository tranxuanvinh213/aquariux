package com.txvinh.aquariux.service;

import com.txvinh.aquariux.domain.TradingHistory;
import com.txvinh.aquariux.mapper.TradingHistoryMapper;
import com.txvinh.aquariux.repository.TradingHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TradingTransactionService {
    private final TradingHistoryRepository tradingHistoryRepository;
    private final UserService userService;
    
    public TradingHistory save(TradingHistory tradingHistory) {
        var entity = tradingHistoryRepository.save(TradingHistoryMapper.INSTANCE.domainToEntity(tradingHistory));
        return TradingHistoryMapper.INSTANCE.entityToDomain(entity);
    }

    public List<TradingHistory> getTradingHistoryOfCurrentUser(String email) {
        var user  = userService.getUserByEmail(email);
        var tradingTransactions = tradingHistoryRepository.findByUserId(user.getId());
        return tradingTransactions.stream().map(TradingHistoryMapper.INSTANCE::entityToDomain).toList();
    }
}
