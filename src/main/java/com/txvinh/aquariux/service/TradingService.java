package com.txvinh.aquariux.service;

import com.txvinh.aquariux.constant.Crypto;
import com.txvinh.aquariux.domain.CryptoWallet;
import com.txvinh.aquariux.domain.TradeRequest;
import com.txvinh.aquariux.domain.TradeResponse;
import com.txvinh.aquariux.domain.TradingHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class TradingService {
    private final CryptoWalletService cryptoWalletService;
    private final UserService userService;
    private final TradingTransactionService tradingTransactionService;
    private final PriceAggregateService priceAggregateService;
    
    public TradeResponse sell (TradeRequest request) {
        var user = userService.getUserByEmail("vinhit213@gmail.com");
        //get best price from aggregation.
        var bestPrice = priceAggregateService.getLatestPriceAggregation();
        request.setPrice(bestPrice.getBidPrice());
        //get wallet of current user
        var cryptoWallet = cryptoWalletService.getWalletByType(request.getSymbol());
        // check amount
        if(request.getAmount().doubleValue() > cryptoWallet.getAmount().doubleValue()) {
            return TradeResponse.builder().status(Crypto.FAIL)
                    .message("The quantity you want to sell exceeds the quantity you currently have").build();
        }
        cryptoWallet.setAmount(BigDecimal.valueOf(cryptoWallet.getAmount().doubleValue() - request.getAmount().doubleValue()));
        cryptoWalletService.update(cryptoWallet);

        var cryptoWalletUsdt = cryptoWalletService.getWalletByType(Crypto.USDT);
        Double totalPrice = (request.getAmount().doubleValue() * request.getPrice().doubleValue()) - request.getFee().doubleValue();
        cryptoWalletUsdt.setAmount(BigDecimal.valueOf(cryptoWalletUsdt.getAmount().doubleValue() + totalPrice));
        cryptoWalletService.update(cryptoWalletUsdt);
        
        // save transaction
        TradingHistory tradingHistory = TradingHistory.builder()
                .userId(user.getId())
                .amount(request.getAmount())
                .type(request.getAction())
                .price(request.getPrice())
                .symbol(request.getSymbol())
                .status(Crypto.SUCCESS)
                .build();
        tradingTransactionService.save(tradingHistory);
        
        return TradeResponse.builder().status(Crypto.SUCCESS).message("Sell successful.").build();
    }

    public TradeResponse buy (TradeRequest request) {
        var user = userService.getUserByEmail("vinhit213@gmail.com");
        //get best price from aggregation.
        var bestPrice = priceAggregateService.getLatestPriceAggregation();
        request.setPrice(bestPrice.getAskPrice());
        
        // check amount
        if(request.getAmount().compareTo(bestPrice.getAskQty()) > 0 ) {
            return TradeResponse.builder().status(Crypto.FAIL)
                    .message("The quantity you want to buy exceeds the quantity market currently have").build();
        }
        
        Double totalPrice = (request.getAmount().doubleValue() * request.getPrice().doubleValue()) - request.getFee().doubleValue();
        var cryptoWalletUsdt = cryptoWalletService.getWalletByType(Crypto.USDT);
        // check balance
        if(totalPrice > cryptoWalletUsdt.getAmount().doubleValue()) {
            return TradeResponse.builder().status(Crypto.FAIL)
                    .message("The balance not enough.").build();
        }
        //get wallet of current user
        var cryptoWallet = cryptoWalletService.getWalletByType(request.getSymbol());
        if(cryptoWallet != null) {
            cryptoWallet.setAmount(BigDecimal.valueOf(cryptoWallet.getAmount().doubleValue() + request.getAmount().doubleValue()));
            cryptoWalletService.update(cryptoWallet);
        } else {
            CryptoWallet cryptoWalletNew  = new com.txvinh.aquariux.domain.CryptoWallet();
            cryptoWalletNew.setUserId(user.getId());
            cryptoWalletNew.setType(request.getSymbol());
            cryptoWalletNew.setAmount(request.getAmount());
            cryptoWalletService.create(cryptoWalletNew);
        }
        
        cryptoWalletUsdt.setAmount(BigDecimal.valueOf(cryptoWalletUsdt.getAmount().doubleValue() - totalPrice));
        cryptoWalletService.update(cryptoWalletUsdt);

        // save transaction
        TradingHistory tradingHistory = TradingHistory.builder()
                .userId(user.getId())
                .amount(request.getAmount())
                .type(request.getAction())
                .price(request.getPrice())
                .symbol(request.getSymbol())
                .status(Crypto.SUCCESS)
                .build();
        tradingTransactionService.save(tradingHistory);

        return TradeResponse.builder().status(Crypto.SUCCESS).message("Buy successful.").build();
    }
}
