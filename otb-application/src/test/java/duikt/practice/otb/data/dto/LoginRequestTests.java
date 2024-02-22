package duikt.practice.otb.data.dto;

import duikt.practice.otb.data.TestAdvice;
import duikt.practice.otb.dto.LoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.Stream;

import static duikt.practice.otb.data.TestAdvice.getViolation;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class LoginRequestTests {

    private LoginRequest loginRequest;

    @BeforeEach
    public void setLoginRequest() {
        loginRequest = new LoginRequest("username", "password");
    }

    @Test
    public void testValidLoginRequest() {
        assertEquals(0, TestAdvice.getViolation(loginRequest).size());
    }

    @ParameterizedTest
    @MethodSource("emptyAndNullArguments")
    public void testInvalidUsername(String username) {
        loginRequest.setUsername(username);

        assertEquals(1, getViolation(loginRequest).size());
        assertEquals(username, getViolation(loginRequest).iterator().next().getInvalidValue());
        assertEquals("Fill in your name please!",
                getViolation(loginRequest).iterator().next().getMessage());
    }

    @ParameterizedTest
    @MethodSource("emptyAndNullArguments")
    public void testInvalidPassword(String password) {
        loginRequest.setPassword(password);

        assertEquals(1, getViolation(loginRequest).size());
        assertEquals(password, getViolation(loginRequest).iterator().next().getInvalidValue());
        assertEquals("Fill in your password please!",
                getViolation(loginRequest).iterator().next().getMessage());
    }

    private static Stream<String> emptyAndNullArguments() {
        return Stream.of("", null);
    }

}
