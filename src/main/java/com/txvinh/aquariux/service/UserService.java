package com.txvinh.aquariux.service;

import com.txvinh.aquariux.domain.User;
import com.txvinh.aquariux.mapper.UserMapper;
import com.txvinh.aquariux.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    public User createUser(User user) {
        com.txvinh.aquariux.entity.User entity = userRepository.save(UserMapper.INSTANCE.domainToEntity(user));
        return UserMapper.INSTANCE.entityToDomain(entity);
    }
    
    public User getUserByEmail(String email) {
        com.txvinh.aquariux.entity.User user = userRepository.findByEmail(email);
        return UserMapper.INSTANCE.entityToDomain(user);
    }
}
