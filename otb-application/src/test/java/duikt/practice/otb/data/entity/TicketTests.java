package duikt.practice.otb.data.entity;

import duikt.practice.otb.entity.Ticket;
import duikt.practice.otb.entity.addition.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import static duikt.practice.otb.data.TestAdvice.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class TicketTests {

    private Ticket ticket;

    @BeforeEach
    public void setTicket() {
        ticket = new Ticket();
        ticket.setName("Test");
        ticket.setFrom(City.DNIPRO);
        ticket.setTo(City.KYIV);
        ticket.setPrice(BigDecimal.valueOf(200));
        ticket.setDayOfDeparture(LocalDate.of(2024, 3, 20));
        ticket.setArrivalDay(LocalDate.of(2024, 3, 21));
        ticket.setTimeOfDeparture(LocalTime.of(21, 25));
        ticket.setArrivalTime(LocalTime.of(3, 12));
        ticket.setSeatNumber(12);
    }

    @Test
    public void testValidTicket() {
        assertEquals(0, getViolation(ticket).size());
    }

    @ParameterizedTest
    @MethodSource("emptyAndNullArguments")
    public void testInvalidName(String name) {
        ticket.setName(name);

        testInvalidTicketArgs(ticket, name,
                "Name must be filled in!");
    }

    private static Stream<String> emptyAndNullArguments() {
        return Stream.of("", null);
    }

    @Test
    public void testInvalidCityFrom() {
        ticket.setFrom(null);

        testInvalidTicketArgs(ticket, null,
                "City from must be filled in!");
    }

    @Test
    public void testInvalidCityTo() {
        ticket.setTo(null);

        testInvalidTicketArgs(ticket, null,
                "City to must be filled in!");
    }

    @Test
    public void testInvalidDayOfDeparture() {
        ticket.setDayOfDeparture(null);

        testInvalidTicketArgs(ticket, null,
                "Day of departure must be filled in!");
    }

    @Test
    public void testInvalidArrivalDay() {
        ticket.setArrivalDay(null);

        testInvalidTicketArgs(ticket, null,
                "Arrival day must be filled in!");
    }

    @Test
    public void testInvalidTimeOfDeparture() {
        ticket.setTimeOfDeparture(null);

        testInvalidTicketArgs(ticket, null,
                "Time of departure must be filled in!");
    }

    @Test
    public void testInvalidArrivalTime() {
        ticket.setArrivalTime(null);

        testInvalidTicketArgs(ticket, null,
                "Arrival time must be filled in!");
    }

    @Test
    public void testInvalidPriceNull() {
        ticket.setPrice(null);

        testInvalidTicketArgs(ticket, null,
                "Price cannot be null!");
    }

    @ParameterizedTest
    @MethodSource("getInvalidPriceArgs")
    public void testInvalidPriceArgs(BigDecimal price) {
        ticket.setPrice(price);

        testInvalidTicketArgs(ticket, price,
                "Price of ticket cannot be lower that zero!");
    }

    private static Stream<BigDecimal> getInvalidPriceArgs() {
        return Stream.of(BigDecimal.valueOf(-0.1),
                BigDecimal.valueOf(-1), BigDecimal.valueOf(-12));
    }

    @ParameterizedTest
    @MethodSource("getInvalidSeatNumberArgs")
    public void testInvalidSeatNumber(int seatNumber) {
        ticket.setSeatNumber(seatNumber);

        testInvalidTicketArgs(ticket, seatNumber,
                "Number of seats cannot be lower that zero!");
    }

    private static Stream<Integer> getInvalidSeatNumberArgs() {
        return Stream.of(-2, -1, -12);
    }

}
