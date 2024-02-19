package duikt.practice.otb.service.mapper;

import duikt.practice.otb.dto.UserRegisterRequest;
import duikt.practice.otb.dto.UserResponse;
import duikt.practice.otb.entity.User;
import duikt.practice.otb.entity.addition.Role;
import duikt.practice.otb.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserMapperTests {

    private final UserMapper userMapper;

    @Autowired
    public UserMapperTests(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Test
    public void testValidMappingUserFromUserRequest() {
        String expectedUsername = "username";
        String expectedEmail = "email@mail.co";
        String expectedPassword = "password";

        User expected = new User();
        expected.setUsername(expectedUsername);
        expected.setEmail(expectedEmail);
        expected.setPassword(expectedPassword);

        UserRegisterRequest userRegisterRequest = new UserRegisterRequest(
                expectedUsername, expectedEmail, expectedPassword);
        User actual = userMapper.getEntityFromUserRegisterRequest(userRegisterRequest);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testValidMappingUserResponseFromUser() {
        long expectedId = 1L;
        String expectedUsername = "username";
        String expectedEmail = "email@mail.co";
        String expectedPassword = "password";
        Role expectedRole = Role.ADMIN;

        User user = new User();
        user.setUsername(expectedUsername);
        user.setEmail(expectedEmail);
        user.setPassword(expectedPassword);
        user.setId(expectedId);
        user.setUserRole(expectedRole);

        UserResponse expected = UserResponse.builder().build();
        expected.setUsername(expectedUsername);
        expected.setEmail(expectedEmail);
        expected.setPassword(expectedPassword);
        expected.setId(expectedId);
        expected.setUserRole(expectedRole);

        UserResponse actual = userMapper.getUserResponseFromEntity(user);

        Assertions.assertEquals(expected, actual);
    }
}
