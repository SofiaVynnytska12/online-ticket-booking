package duikt.practice.otb.data.dto;

import duikt.practice.otb.dto.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static duikt.practice.otb.TestAdvice.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ErrorResponseTests {

    private ErrorResponse errorResponse;

    @BeforeEach
    public void setErrorResponse() {
        errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_GATEWAY,
                "error",
                "url..."
        );
    }

    @Test
    public void testValidErrorResponse() {
        assertEquals(0, getViolation(errorResponse).size());
    }

    @Test
    public void testInvalidTimestamp() {
        errorResponse.setTimestamp(null);
        assertionsForNull(errorResponse);
    }

    @Test
    public void testInvalidStatus() {
        errorResponse.setStatus(null);
        assertionsForNull(errorResponse);
    }

    private void assertionsForNull(ErrorResponse errorResponse) {
        assertEquals(1, getViolation(errorResponse).size());
        assertNull(getViolation(errorResponse).iterator().next().getInvalidValue());
    }

    @ParameterizedTest
    @MethodSource("emptyAndNullArguments")
    public void testInvalidMessage(String message) {
        errorResponse.setMessage(message);

        assertEquals(1, getViolation(errorResponse).size());
        assertEquals(message, getViolation(errorResponse).iterator().next().getInvalidValue());
        assertEquals("Message must be filled in!",
                getViolation(errorResponse).iterator().next().getMessage());
    }

    @ParameterizedTest
    @MethodSource("emptyAndNullArguments")
    public void testInvalidPath(String path) {
        errorResponse.setPath(path);

        assertEquals(1, getViolation(errorResponse).size());
        assertEquals(path, getViolation(errorResponse).iterator().next().getInvalidValue());
        assertEquals("Path cannot be empty or null!",
                getViolation(errorResponse).iterator().next().getMessage());
    }

    private static Stream<String> emptyAndNullArguments() {
        return Stream.of("", null);
    }

}


