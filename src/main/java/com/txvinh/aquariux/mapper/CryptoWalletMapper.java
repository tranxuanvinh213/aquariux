package com.txvinh.aquariux.mapper;
import com.txvinh.aquariux.domain.CryptoWallet;
import com.txvinh.aquariux.domain.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CryptoWalletMapper {
    CryptoWalletMapper INSTANCE = Mappers.getMapper(CryptoWalletMapper.class);

    @BeanMapping(qualifiedByName = "entityToDomain")
    CryptoWallet entityToDomain(com.txvinh.aquariux.entity.CryptoWallet cryptoWallet);

    @BeanMapping(qualifiedByName = "domainToEntity")
    com.txvinh.aquariux.entity.CryptoWallet domainToEntity(CryptoWallet cryptoWallet);
    
}
