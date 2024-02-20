package duikt.practice.otb.service;

import duikt.practice.otb.entity.User;
import duikt.practice.otb.entity.addition.Role;
import duikt.practice.otb.exception.InvalidDataException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class UserServiceTests {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceTests(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Test
    public void testValidRegisterUser() {
        String password = "pass";

        User expected = new User();
        expected.setId(1L);
        expected.setUsername("username");
        expected.setEmail("user@mail.co");
        expected.setPassword(password);
        expected.setUserRole(Role.USER);

        User actual = userService.registerUser(expected);
        expected.setId(actual.getId());
        expected.setPassword(actual.getPassword());

        assertEquals(expected, actual);

        // in created user password must be encoded
        assertNotEquals(password, actual.getPassword());
        assertTrue(passwordEncoder.matches(password, actual.getPassword()));
    }

    @Test
    public void testNullRegisterUser() {
        assertThrows(InvalidDataException.class, () -> userService.registerUser(null));
    }

    @Test
    public void testNoArgsRegisterUser() {
        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(new User()));
    }

    @Test
    public void testConstraintViolationExceptionRegisterUser() {
        User exceptionUser = new User();
        exceptionUser.setPassword("1244");

        assertThrows(ConstraintViolationException.class, () -> userService.registerUser(exceptionUser));
    }

}
