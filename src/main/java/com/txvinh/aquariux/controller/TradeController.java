package com.txvinh.aquariux.controller;

import com.txvinh.aquariux.domain.TradeRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trade")
public class TradeController {

    @PostMapping
    public void buy(@RequestBody TradeRequest tradeRequest) {

    }
}
