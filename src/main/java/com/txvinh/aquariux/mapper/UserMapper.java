package com.txvinh.aquariux.mapper;
import com.txvinh.aquariux.domain.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @BeanMapping(qualifiedByName = "entityToDomain")
    User entityToDomain(com.txvinh.aquariux.entity.User user);

    @BeanMapping(qualifiedByName = "domainToEntity")
    com.txvinh.aquariux.entity.User huobiPriceToPrice(User user);
    
}
