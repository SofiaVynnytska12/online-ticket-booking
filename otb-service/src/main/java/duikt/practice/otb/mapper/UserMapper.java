package duikt.practice.otb.mapper;

import duikt.practice.otb.dto.UserRegisterRequest;
import duikt.practice.otb.dto.UserResponse;
import duikt.practice.otb.entity.User;
import org.mapstruct.Mapper;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User getEntityFromUserRegisterRequest(UserRegisterRequest registerRequest);

    UserResponse getUserResponseFromEntity(User user);
}
