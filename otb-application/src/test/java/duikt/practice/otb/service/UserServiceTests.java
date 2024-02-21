package duikt.practice.otb.service;

import duikt.practice.otb.dto.UserResponse;
import duikt.practice.otb.entity.User;
import duikt.practice.otb.entity.addition.Role;
import duikt.practice.otb.exception.InvalidDataException;
import duikt.practice.otb.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class UserServiceTests {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceTests(UserService userService, PasswordEncoder passwordEncoder,
                            UserRepository userRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
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


    @Test
    public void testValidGetAll() {
        String direction = "desc";
        String properties = "username";
        Sort sort = Sort.by(Sort.Direction.fromString(direction), properties);

        List<User> expected = userRepository.findAll(sort);
        List<User> actual = userService.getAll(direction, new String[]{properties});

        assertEquals(expected, actual);
    }

    @Test
    public void testInvalidPropertyGetAll() {
        String direction = "desc";
        String properties = "invalid";
        assertThrows(PropertyReferenceException.class, () ->
                userService.getAll(direction, new String[]{properties}));
    }
    @Test
    public void testValidGetUserById() {
        User expected = new User();
        expected.setUsername("username");
        expected.setEmail("user@mail.co");
        expected.setPassword("password");
        expected = userService.registerUser(expected);

        User actual = userService.getUserById(expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void testValidGetUserByName() {
        User expected = new User();
        expected.setUsername("username");
        expected.setEmail("user@mail.co");
        expected.setPassword("password");
        expected = userService.registerUser(expected);

        User actual = userService.getUserByName("username");

        assertEquals(expected, actual);
    }

    @Test
    public void testInvalidGetUserById() {
        Long userId = -1L;
        assertThrows(EntityNotFoundException.class, () ->
                userService.getUserById(userId));
    }

    @Test
    public void testInvalidGetUserByName() {
        String userName = "InvalidName";
        assertThrows(EntityNotFoundException.class, () ->
                userService.getUserByName(userName));
    }

    @ParameterizedTest
    @MethodSource("emptyAndNullArguments")
    public void testIllegalArgumentException(String invalidName) {
        assertThrows(IllegalArgumentException.class, () ->
                userService.getUserByName(invalidName));
    }
    private static Stream<String> emptyAndNullArguments() {
        return Stream.of("", null);
    }
}
