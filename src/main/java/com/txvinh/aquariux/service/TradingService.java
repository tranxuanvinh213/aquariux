package com.txvinh.aquariux.service;

import com.txvinh.aquariux.constant.Crypto;
import com.txvinh.aquariux.domain.CryptoWallet;
import com.txvinh.aquariux.domain.TradeRequest;
import com.txvinh.aquariux.domain.TradeResponse;
import com.txvinh.aquariux.domain.TradingTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        if(request.getAmount() > cryptoWallet.getAmount()) {
            return TradeResponse.builder().status(Crypto.FAIL)
                    .message("The quantity you want to sell exceeds the quantity you currently have").build();
        }
        cryptoWallet.setAmount(cryptoWallet.getAmount() - request.getAmount());
        cryptoWalletService.update(cryptoWallet);

        var cryptoWalletUsdt = cryptoWalletService.getWalletByType(Crypto.USDT);
        Double totalPrice = (request.getAmount() * request.getPrice()) - request.getFee();
        cryptoWalletUsdt.setAmount(cryptoWalletUsdt.getAmount() + totalPrice);
        cryptoWalletService.update(cryptoWalletUsdt);
        
        // save transaction
        TradingTransaction tradingTransaction = TradingTransaction.builder()
                .userId(user.getId())
                .amount(request.getAmount())
                .type(request.getAction())
                .price(request.getPrice())
                .symbol(request.getSymbol())
                .status(Crypto.SUCCESS)
                .build();
        tradingTransactionService.save(tradingTransaction);
        
        return TradeResponse.builder().status(Crypto.SUCCESS).build();
    }

    public TradeResponse buy (TradeRequest request) {
        var user = userService.getUserByEmail("vinhit213@gmail.com");
        //get best price from aggregation.
        var bestPrice = priceAggregateService.getLatestPriceAggregation();
        request.setPrice(bestPrice.getAskPrice());
        
        // check amount
        if(request.getAmount() > bestPrice.getAskQty()) {
            return TradeResponse.builder().status(Crypto.FAIL)
                    .message("The quantity you want to buy exceeds the quantity market currently have").build();
        }
        
        Double totalPrice = (request.getAmount() * request.getPrice()) - request.getFee();
        var cryptoWalletUsdt = cryptoWalletService.getWalletByType(Crypto.USDT);
        // check balance
        if(totalPrice > cryptoWalletUsdt.getAmount()) {
            return TradeResponse.builder().status(Crypto.FAIL)
                    .message("The balance not enough.").build();
        }
        //get wallet of current user
        var cryptoWallet = cryptoWalletService.getWalletByType(request.getSymbol());
        if(cryptoWallet != null) {
            cryptoWallet.setAmount(cryptoWallet.getAmount() + request.getAmount());
            cryptoWalletService.update(cryptoWallet);
        } else {
            CryptoWallet cryptoWalletNew  = new com.txvinh.aquariux.domain.CryptoWallet();
            cryptoWalletNew.setUserId(user.getId());
            cryptoWalletNew.setType(request.getSymbol());
            cryptoWalletNew.setAmount(request.getAmount());
            cryptoWalletService.create(cryptoWalletNew);
        }
        
        cryptoWalletUsdt.setAmount(cryptoWalletUsdt.getAmount() - totalPrice);
        cryptoWalletService.update(cryptoWalletUsdt);

        // save transaction
        TradingTransaction tradingTransaction = TradingTransaction.builder()
                .userId(user.getId())
                .amount(request.getAmount())
                .type(request.getAction())
                .price(request.getPrice())
                .symbol(request.getSymbol())
                .status(Crypto.SUCCESS)
                .build();
        tradingTransactionService.save(tradingTransaction);

        return TradeResponse.builder().status(Crypto.SUCCESS).build();
    }
}
