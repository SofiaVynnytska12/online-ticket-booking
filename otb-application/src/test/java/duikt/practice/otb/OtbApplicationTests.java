package duikt.practice.otb;

import duikt.practice.otb.config.ResourcesConfig;
import duikt.practice.otb.config.ServiceConfig;
import duikt.practice.otb.controller.UserController;
import duikt.practice.otb.exceptionhandler.GlobalExceptionHandler;
import duikt.practice.otb.mapper.UserMapper;
import duikt.practice.otb.repository.UserRepository;
import duikt.practice.otb.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("test")
class OtbApplicationTests {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final ServiceConfig serviceConfig;
    private final UserController userController;
    private final ResourcesConfig resourcesConfig;
    private final GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    public OtbApplicationTests(UserRepository userRepository, UserService userService,
                               UserMapper userMapper, ServiceConfig serviceConfig,
                               UserController userController, ResourcesConfig resourcesConfig,
                               GlobalExceptionHandler globalExceptionHandler) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.userMapper = userMapper;
        this.serviceConfig = serviceConfig;
        this.userController = userController;
        this.resourcesConfig = resourcesConfig;
        this.globalExceptionHandler = globalExceptionHandler;
    }

    @Test
    void contextLoads() {
        Assertions.assertNotNull(userRepository);
        Assertions.assertNotNull(userService);
        Assertions.assertNotNull(userMapper);
        Assertions.assertNotNull(serviceConfig);
        Assertions.assertNotNull(userController);
        Assertions.assertNotNull(resourcesConfig);
        Assertions.assertNotNull(globalExceptionHandler);
    }

}
