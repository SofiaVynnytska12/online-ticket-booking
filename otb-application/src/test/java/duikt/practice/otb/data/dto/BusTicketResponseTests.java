package duikt.practice.otb.data.dto;

import duikt.practice.otb.dto.BusTicketResponse;
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

import static duikt.practice.otb.TestAdvice.getViolation;
import static duikt.practice.otb.TestAdvice.testInvalidTicketArgs;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class BusTicketResponseTests {

    private BusTicketResponse busTicketResponse;

    @BeforeEach
    public void setBusTicketResponse() {
        busTicketResponse = BusTicketResponse.builder()
                .seatNumber(12)
                .arrivalTime(LocalTime.of(12, 35))
                .timeOfDeparture(LocalTime.of(15, 33))
                .price(BigDecimal.valueOf(120))
                .to("Kyiv")
                .from("Dnipro")
                .arrivalDay(LocalDate.of(2024, 12, 1))
                .dayOfDeparture(LocalDate.of(2024, 12, 1))
                .name("Name")
                .build();
    }

    @Test
    public void testValidTrainTicketResponse() {
        assertEquals(0, getViolation(busTicketResponse).size());
    }

    @ParameterizedTest
    @MethodSource("emptyAndNullArguments")
    public void testInvalidName(String name) {
        busTicketResponse.setName(name);

        testInvalidTicketArgs(busTicketResponse, name,
                "Name must be filled in!");
    }

    @ParameterizedTest
    @MethodSource("emptyAndNullArguments")
    public void testInvalidCityFrom(String cityFrom) {
        busTicketResponse.setFrom(cityFrom);

        testInvalidTicketArgs(busTicketResponse, cityFrom,
                "City from must be filled in!");
    }

    @ParameterizedTest
    @MethodSource("emptyAndNullArguments")
    public void testInvalidCityTo(String cityTo) {
        busTicketResponse.setTo(cityTo);

        testInvalidTicketArgs(busTicketResponse, cityTo,
                "City to must be filled in!");
    }

    private static Stream<String> emptyAndNullArguments() {
        return Stream.of("", null);
    }


    @Test
    public void testInvalidDayOfDeparture() {
        busTicketResponse.setDayOfDeparture(null);

        testInvalidTicketArgs(busTicketResponse, null,
                "Day of departure must be filled in!");
    }

    @Test
    public void testInvalidArrivalDay() {
        busTicketResponse.setArrivalDay(null);

        testInvalidTicketArgs(busTicketResponse, null,
                "Arrival day must be filled in!");
    }

    @Test
    public void testInvalidTimeOfDeparture() {
        busTicketResponse.setTimeOfDeparture(null);

        testInvalidTicketArgs(busTicketResponse, null,
                "Time of departure must be filled in!");
    }

    @Test
    public void testInvalidArrivalTime() {
        busTicketResponse.setArrivalTime(null);

        testInvalidTicketArgs(busTicketResponse, null,
                "Arrival time must be filled in!");
    }

    @Test
    public void testInvalidPriceNull() {
        busTicketResponse.setPrice(null);

        testInvalidTicketArgs(busTicketResponse, null,
                "Price cannot be null!");
    }

    @ParameterizedTest
    @MethodSource("getInvalidPriceArgs")
    public void testInvalidPriceArgs(BigDecimal price) {
        busTicketResponse.setPrice(price);

        testInvalidTicketArgs(busTicketResponse, price,
                "Price of ticket cannot be lower that zero!");
    }

    private static Stream<BigDecimal> getInvalidPriceArgs() {
        return Stream.of(BigDecimal.valueOf(-0.1),
                BigDecimal.valueOf(-1), BigDecimal.valueOf(-12));
    }

    @ParameterizedTest
    @MethodSource("getInvalidNumberArgs")
    public void testInvalidSeatNumber(int seatNumber) {
        busTicketResponse.setSeatNumber(seatNumber);

        testInvalidTicketArgs(busTicketResponse, seatNumber,
                "Number of seats cannot be lower that zero!");
    }

    private static Stream<Integer> getInvalidNumberArgs() {
        return Stream.of(-2, -1, -12);
    }



}
