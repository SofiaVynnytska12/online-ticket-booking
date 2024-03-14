package duikt.practice.otb.data.dto;

import duikt.practice.otb.TestAdvice;
import duikt.practice.otb.dto.UserRegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.Stream;

import static duikt.practice.otb.TestAdvice.getViolation;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserRegisterRequestTests {

    private UserRegisterRequest userRegisterRequest;

    @BeforeEach
    public void setUserRegisterRequest() {
        userRegisterRequest = UserRegisterRequest.builder()
                .username("username")
                .email("email@mail.co")
                .password("password")
                .build();
    }

    @Test
    public void testValidUserRegisterRequest() {
        assertEquals(0, TestAdvice.getViolation(userRegisterRequest).size());
    }

    @ParameterizedTest
    @MethodSource("emptyAndNullArguments")
    public void testInvalidUsername(String username) {
        userRegisterRequest.setUsername(username);

        assertEquals(1, getViolation(userRegisterRequest).size());
        assertEquals(username, getViolation(userRegisterRequest).iterator().next().getInvalidValue());
        assertEquals("Fill in your name please!",
                getViolation(userRegisterRequest).iterator().next().getMessage());
    }

    @ParameterizedTest
    @MethodSource("emptyAndNullArguments")
    public void testInvalidPassword(String password) {
        userRegisterRequest.setPassword(password);

        assertEquals(1, getViolation(userRegisterRequest).size());
        assertEquals(password, getViolation(userRegisterRequest).iterator().next().getInvalidValue());
        assertEquals("Fill in your password please!",
                getViolation(userRegisterRequest).iterator().next().getMessage());
    }

    private static Stream<String> emptyAndNullArguments() {
        return Stream.of("", null);
    }

    @ParameterizedTest
    @MethodSource("argumentsForEmail")
    public void testInvalidEmail(String email, String error) {
        userRegisterRequest.setEmail(email);

        assertEquals(1, getViolation(userRegisterRequest).size());
        assertEquals(error, getViolation(userRegisterRequest).iterator().next().getInvalidValue());
        assertEquals("Must be a valid e-mail address",
                getViolation(userRegisterRequest).iterator().next().getMessage());
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
}
