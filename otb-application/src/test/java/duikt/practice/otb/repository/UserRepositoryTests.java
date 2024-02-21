package duikt.practice.otb.repository;

import duikt.practice.otb.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static duikt.practice.otb.entity.addition.Role.USER;
import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class UserRepositoryTests {

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTests(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    public void testValidFindByUserName() {
        User expected = new User();
        expected.setEmail("email@mail.com");
        expected.setPassword("password");
        expected.setUserRole(USER);
        expected.setUsername("aboba");
        userRepository.saveAndFlush(expected);

        Optional<User> actual = userRepository.findByUsername("aboba");
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    public void testInvalidFindByUserName() {
        Optional<User> actual = userRepository.findByUsername("invalid");
        assertTrue(actual.isEmpty());
    }
}
