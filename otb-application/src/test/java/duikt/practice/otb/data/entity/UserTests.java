package duikt.practice.otb.data.entity;

import duikt.practice.otb.entity.User;
import duikt.practice.otb.entity.addition.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.stream.Stream;

import static duikt.practice.otb.data.TestAdvice.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration
@ActiveProfiles("test")
public class UserTests {

    private User user;

    @BeforeEach
    public void setUser() {
        user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setPassword("1234");
        user.setEmail("user@mail.co");
        user.setUserRole(Role.USER);
    }

    @Test
    public void testValidUser() {
        assertEquals(0, getViolation(user).size());
    }

    @ParameterizedTest
    @MethodSource("emptyAndNullArguments")
    public void testInvalidUsername(String username) {
        user.setUsername(username);

        assertEquals(1, getViolation(user).size());
        assertEquals(username, getViolation(user).iterator().next().getInvalidValue());
        assertEquals("Fill in your name please!",
                getViolation(user).iterator().next().getMessage());
    }

    @ParameterizedTest
    @MethodSource("emptyAndNullArguments")
    public void testInvalidPassword(String password) {
        user.setPassword(password);

        assertEquals(1, getViolation(user).size());
        assertEquals(password, getViolation(user).iterator().next().getInvalidValue());
        assertEquals("Fill in your password please!",
                getViolation(user).iterator().next().getMessage());
    }

    private static Stream<String> emptyAndNullArguments() {
        return Stream.of("", null);
    }

    @ParameterizedTest
    @MethodSource("argumentsForEmail")
    public void testInvalidEmail(String email, String error) {
        user.setEmail(email);

        assertEquals(1, getViolation(user).size());
        assertEquals(error, getViolation(user).iterator().next().getInvalidValue());
        assertEquals("Must be a valid e-mail address",
                getViolation(user).iterator().next().getMessage());
    }

    private static Stream<Arguments> argumentsForEmail() {
        return Stream.of(
                Arguments.arguments("", ""),
                Arguments.arguments("email", "email"),
                Arguments.arguments("ema@il", "ema@il"),
                Arguments.arguments("user@.", "user@."),
                Arguments.arguments("user@mail", "user@mail")

        );
    }

    @Test
    public void testInvalidUserRole() {
        user.setUserRole(null);

        assertEquals(1, getViolation(user).size());
        assertNull(getViolation(user).iterator().next().getInvalidValue());
    }
}
