package com.txvinh.aquariux.mapper;
import com.txvinh.aquariux.domain.TradingHistory;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TradingHistoryMapper {
    TradingHistoryMapper INSTANCE = Mappers.getMapper(TradingHistoryMapper.class);

    @BeanMapping(qualifiedByName = "entityToDomain")
    TradingHistory entityToDomain(com.txvinh.aquariux.entity.TradingHistory tradingHistory);

    @BeanMapping(qualifiedByName = "domainToEntity")
    com.txvinh.aquariux.entity.TradingHistory domainToEntity(TradingHistory tradingHistory);
    
}
