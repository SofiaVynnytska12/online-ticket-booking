package duikt.practice.otb.config;

import duikt.practice.otb.mapper.UserMapper;
import duikt.practice.otb.mapper.impl.UserMapperImpl;
import duikt.practice.otb.repository.UserRepository;
import duikt.practice.otb.service.UserService;
import duikt.practice.otb.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ResourcesConfig {

    @Bean
    public UserService userService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Bean
    public UserMapper userMapper() {
        return new UserMapperImpl();
    }
}
