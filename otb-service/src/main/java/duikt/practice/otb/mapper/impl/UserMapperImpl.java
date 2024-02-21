package duikt.practice.otb.mapper.impl;

import duikt.practice.otb.dto.UserRegisterRequest;
import duikt.practice.otb.dto.UserResponse;
import duikt.practice.otb.entity.User;
import duikt.practice.otb.mapper.UserMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.springframework.security.core.userdetails.User.builder;

public class UserMapperImpl implements UserMapper{

    @Override
    public User getEntityFromUserRegisterRequest(UserRegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());

        return user;
    }

    @Override
    public UserResponse getUserResponseFromEntity(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .username(user.getUsername())
                .userRole(user.getUserRole())
                .build();
    }

    @Override
    public UserDetails toUserDetails(User user) {
        return builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(Collections.singleton(new SimpleGrantedAuthority(user.getUserRole().name())))
                .build();
    }
}
