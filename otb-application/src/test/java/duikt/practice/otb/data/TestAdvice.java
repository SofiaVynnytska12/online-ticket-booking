package duikt.practice.otb.data;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAdvice {

    public static <T> Set<ConstraintViolation<T>> getViolation(T testedClass) {
        return Validation.buildDefaultValidatorFactory()
                .getValidator()
                .validate(testedClass);
    }

    public static <T, E> void testInvalidTicketArgs(T checkClass, E testField, String message) {
        assertEquals(1, getViolation(checkClass).size());
        assertEquals(testField, getViolation(checkClass).iterator()
                .next().getInvalidValue());
        assertEquals(message, getViolation(checkClass).iterator().next().getMessage());
    }

}
