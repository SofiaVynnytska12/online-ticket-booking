package duikt.practice.otb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import duikt.practice.otb.entity.BusTicket;
import duikt.practice.otb.entity.Ticket;
import duikt.practice.otb.repository.BusTicketRepository;
import org.springframework.test.web.servlet.ResultMatcher;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    public static <T> String asJsonString(final T obj) {
        try {
            return new ObjectMapper().registerModule(new JavaTimeModule())
                    .configure(WRITE_DATES_AS_TIMESTAMPS, false)
                    .writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<BusTicket> getSortedBusTickets(
            BusTicketRepository busTicketRepository,
            String cityFrom, String cityTo, String direction) {
        return busTicketRepository.findAll().stream()
                .filter(busTicket -> busTicket.getFrom().getName().equals(cityFrom)
                        && busTicket.getTo().getName().equals(cityTo)
                        && busTicket.getOwner() == null)
                .sorted(chooseSortingDirection(direction))
                .collect(Collectors.toList());
    }

    private static <T extends Ticket> Comparator<T> chooseSortingDirection(String direction) {
        return (o1, o2) -> {
            if (direction.equals("-")) {
                return getComparatorForDESCSorting(o1, o2);
            }
            return getComparatorForASCSorting(o1, o2);
        };
    }

    private static <T extends Ticket, E extends Ticket> int getComparatorForASCSorting(T b1, E b2) {
        int result = b1.getDayOfDeparture().compareTo(b2.getDayOfDeparture());
        if (result == 0) {
            result = b1.getTimeOfDeparture().compareTo(b2.getTimeOfDeparture());
            if (result == 0) {
                result = b1.getArrivalDay().compareTo(b2.getArrivalDay());
                if (result == 0) {
                    result = b1.getArrivalTime().compareTo(b2.getArrivalTime());
                }
            }
        }
        return result;
    }

    private static <T extends Ticket, E extends Ticket> int getComparatorForDESCSorting(T b1, E b2) {
        int result = b2.getDayOfDeparture().compareTo(b1.getDayOfDeparture());
        if (result == 0) {
            result = b2.getTimeOfDeparture().compareTo(b1.getTimeOfDeparture());
            if (result == 0) {
                result = b2.getArrivalDay().compareTo(b1.getArrivalDay());
                if (result == 0) {
                    result = b2.getArrivalTime().compareTo(b1.getArrivalTime());
                }
            }
        }
        return result;
    }

    public static ResultMatcher forbiddenResult() {
        return result -> assertTrue(result
                .getResponse()
                .getContentAsString()
                .contains("\"status\":\"FORBIDDEN\",\"message\"" +
                        ":\"Access is denied\""));
    }

}
