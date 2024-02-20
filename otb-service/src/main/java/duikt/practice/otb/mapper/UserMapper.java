package duikt.practice.otb.mapper;

import duikt.practice.otb.dto.UserRegisterRequest;
import duikt.practice.otb.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User getEntityFromUserRegisterRequest(UserRegisterRequest registerRequest);
}