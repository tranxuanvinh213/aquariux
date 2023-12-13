package com.txvinh.aquariux.controller;

import com.txvinh.aquariux.constant.Crypto;
import com.txvinh.aquariux.domain.TradeRequest;
import com.txvinh.aquariux.domain.TradeResponse;
import com.txvinh.aquariux.service.TradingService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/trade")
public class TradeController {
    private final TradingService tradingService;

    @PostMapping
    public TradeResponse trade(@RequestBody @Validated TradeRequest tradeRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return TradeResponse.builder().status(Crypto.FAIL).message("Invalid request data").build();
        }
        switch (tradeRequest.getAction()) {
            case Crypto.SELL:
                return tradingService.sell(tradeRequest);
            case Crypto.BUY:
                return tradingService.buy(tradeRequest);
            default:
                System.out.println("Action invalid");
        }
        return null;
    }
}
