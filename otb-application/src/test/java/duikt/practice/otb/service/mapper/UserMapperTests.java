package duikt.practice.otb.service.mapper;

import duikt.practice.otb.dto.UserRegisterRequest;
import duikt.practice.otb.entity.User;
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
    public void testValidMapping() {
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
}
