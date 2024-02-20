package duikt.practice.otb.mapper.impl;

import duikt.practice.otb.dto.UserRegisterRequest;
import duikt.practice.otb.entity.User;
import duikt.practice.otb.mapper.UserMapper;

public class UserMapperImpl implements UserMapper{

    @Override
    public User getEntityFromUserRegisterRequest(UserRegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());

        return user;
    }
}
