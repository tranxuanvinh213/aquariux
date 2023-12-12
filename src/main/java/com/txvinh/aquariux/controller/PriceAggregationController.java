package com.txvinh.aquariux.controller;

import com.txvinh.aquariux.domain.PriceData;
import com.txvinh.aquariux.service.PriceAggregateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prices")
public class PriceAggregationController {
    private final PriceAggregateService priceAggregateService;
    
    @GetMapping("/all")
    public List<PriceData> getAll(){
        return priceAggregateService.getList();
    }

    @GetMapping("/latest")
    public PriceData getLatestPrice(){
        return priceAggregateService.getLatestPriceAggregation();
    }
}
