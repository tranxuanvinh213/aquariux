package com.txvinh.aquariux.mapper;

import com.txvinh.aquariux.domain.PriceData;
import com.txvinh.aquariux.entity.PriceAggregation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PriceAggregationMapper {
    PriceAggregationMapper INSTANCE = Mappers.getMapper(PriceAggregationMapper.class);

    @Mapping(target = "id", ignore = true)
    PriceAggregation map(PriceData priceData);
    
    PriceData entityToDomain(PriceAggregation priceAggregation);
}
