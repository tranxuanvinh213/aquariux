package com.txvinh.aquariux.mapper;
import com.txvinh.aquariux.domain.TradingTransaction;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TradingTransactionMapper {
    TradingTransactionMapper INSTANCE = Mappers.getMapper(TradingTransactionMapper.class);

    @BeanMapping(qualifiedByName = "entityToDomain")
    TradingTransaction entityToDomain(com.txvinh.aquariux.entity.TradingTransaction tradingTransaction);

    @BeanMapping(qualifiedByName = "domainToEntity")
    com.txvinh.aquariux.entity.TradingTransaction domainToEntity(TradingTransaction tradingTransaction);
    
}
