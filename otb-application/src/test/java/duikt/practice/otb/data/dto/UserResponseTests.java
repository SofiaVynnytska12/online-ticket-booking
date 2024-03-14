package duikt.practice.otb.data.dto;

import duikt.practice.otb.dto.UserResponse;
import duikt.practice.otb.entity.addition.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.Stream;

import static duikt.practice.otb.TestAdvice.getViolation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ActiveProfiles("test")
public class UserResponseTests {

    private UserResponse userResponse;

    @BeforeEach
    public void setUserResponse() {
        userResponse = UserResponse.builder()
                .id(1L)
                .password("1234")
                .email("email@mail.co")
                .username("username")
                .userRole(Role.USER)
                .build();
    }

    @Test
    public void testValidUserResponse() {
        assertEquals(0, getViolation(userResponse).size());
    }

    @ParameterizedTest
    @MethodSource("emptyAndNullArguments")
    public void testInvalidUsername(String username) {
        userResponse.setUsername(username);

        assertEquals(1, getViolation(userResponse).size());
        assertEquals(username, getViolation(userResponse).iterator().next().getInvalidValue());
        assertEquals("Fill in your name please!",
                getViolation(userResponse).iterator().next().getMessage());
    }

    @ParameterizedTest
    @MethodSource("emptyAndNullArguments")
    public void testInvalidPassword(String password) {
        userResponse.setPassword(password);

        assertEquals(1, getViolation(userResponse).size());
        assertEquals(password, getViolation(userResponse).iterator().next().getInvalidValue());
        assertEquals("Fill in your password please!",
                getViolation(userResponse).iterator().next().getMessage());
    }

    private static Stream<String> emptyAndNullArguments() {
        return Stream.of("", null);
    }

    @ParameterizedTest
    @MethodSource("argumentsForEmail")
    public void testInvalidEmail(String email, String error) {
        userResponse.setEmail(email);

        assertEquals(1, getViolation(userResponse).size());
        assertEquals(error, getViolation(userResponse).iterator().next().getInvalidValue());
        assertEquals("Must be a valid e-mail address",
                getViolation(userResponse).iterator().next().getMessage());
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
        userResponse.setUserRole(null);

        assertEquals(1, getViolation(userResponse).size());
        assertNull(getViolation(userResponse).iterator().next().getInvalidValue());
    }

}
