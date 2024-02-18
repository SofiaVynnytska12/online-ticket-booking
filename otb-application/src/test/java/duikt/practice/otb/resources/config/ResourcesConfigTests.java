package duikt.practice.otb.resources.config;

import duikt.practice.otb.config.ResourcesConfig;
import duikt.practice.otb.mapper.UserMapper;
import duikt.practice.otb.repository.UserRepository;
import duikt.practice.otb.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class ResourcesConfigTests {

    private final ResourcesConfig resourcesConfig;
    private final UserService expectedUserService;
    private final UserMapper expectedUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public ResourcesConfigTests(ResourcesConfig resourcesConfig, UserService userService,
                                UserMapper userMapper, PasswordEncoder passwordEncoder,
                                UserRepository userRepository) {
        this.resourcesConfig = resourcesConfig;
        this.expectedUserService = userService;
        this.expectedUserMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Test
    public void testValidUserServiceBean() {
        UserService actualUserService = resourcesConfig.userService(userRepository, passwordEncoder);

        Assertions.assertEquals(expectedUserService, actualUserService);
    }

    @Test
    public void testValidUserMapperBean() {
        UserMapper actualUserMapper = resourcesConfig.userMapper();

        Assertions.assertEquals(expectedUserMapper, actualUserMapper);
    }

}
