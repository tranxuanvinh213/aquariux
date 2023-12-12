package com.txvinh.aquariux.service;

import com.txvinh.aquariux.domain.PriceData;
import com.txvinh.aquariux.entity.PriceAggregation;
import com.txvinh.aquariux.mapper.PriceAggregationMapper;
import com.txvinh.aquariux.repository.PriceAggregateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PriceAggregateService {
    private final PriceAggregateRepository priceAggregateRepository;
    public PriceData save(PriceData priceData) {
        PriceAggregation price = priceAggregateRepository.save(PriceAggregationMapper.INSTANCE.map(priceData));
        return PriceAggregationMapper.INSTANCE.entityToDomain(price);
    }
    
    public List<PriceData> getList() {
        List<PriceAggregation> priceAggregations = priceAggregateRepository.findAll();
        return priceAggregations.stream().map(PriceAggregationMapper.INSTANCE::entityToDomain).toList();
    }

    public PriceData getLatestPriceAggregation() {
        Optional<PriceAggregation> priceAggregation = priceAggregateRepository.findLatestPriceAggregation();
        return priceAggregation.map(PriceAggregationMapper.INSTANCE::entityToDomain).orElse(null);
    }
}
