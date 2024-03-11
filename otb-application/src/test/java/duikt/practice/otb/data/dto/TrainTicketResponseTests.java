package duikt.practice.otb.data.dto;

import duikt.practice.otb.dto.TrainTicketResponse;
import duikt.practice.otb.entity.addition.City;
import duikt.practice.otb.entity.addition.TypeOfTrainClass;
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

import static duikt.practice.otb.data.TestAdvice.getViolation;
import static duikt.practice.otb.data.TestAdvice.testInvalidTicketArgs;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class TrainTicketResponseTests {

    private TrainTicketResponse trainTicketResponse;

    @BeforeEach
    public void setTrainTicketResponse() {
        trainTicketResponse = TrainTicketResponse.builder()
                .name("Name")
                .to(City.KYIV.getName())
                .from(City.DNIPRO.getName())
                .dayOfDeparture(LocalDate.of(2025, 2, 15))
                .arrivalDay(LocalDate.of(2025, 2, 15))
                .timeOfDeparture(LocalTime.of(10, 54))
                .arrivalTime(LocalTime.of(15, 32))
                .price(BigDecimal.valueOf(230))
                .typeOfTrainClass(TypeOfTrainClass.LUXURY.name())
                .seatNumber(23)
                .carNumber(12)
                .build();
    }

    @Test
    public void testValidTrainTicketResponse() {
        assertEquals(0, getViolation(trainTicketResponse).size());
    }

    @ParameterizedTest
    @MethodSource("emptyAndNullArguments")
    public void testInvalidName(String name) {
        trainTicketResponse.setName(name);

        testInvalidTicketArgs(trainTicketResponse, name,
                "Name must be filled in!");
    }

    @ParameterizedTest
    @MethodSource("emptyAndNullArguments")
    public void testInvalidCityFrom(String cityFrom) {
        trainTicketResponse.setFrom(cityFrom);

        testInvalidTicketArgs(trainTicketResponse, cityFrom,
                "City from must be filled in!");
    }

    @ParameterizedTest
    @MethodSource("emptyAndNullArguments")
    public void testInvalidCityTo(String cityTo) {
        trainTicketResponse.setTo(cityTo);

        testInvalidTicketArgs(trainTicketResponse, cityTo,
                "City to must be filled in!");
    }

    @ParameterizedTest
    @MethodSource("emptyAndNullArguments")
    public void testInvalidTypeOfTrainClass(String typeOfTrainClass) {
        trainTicketResponse.setTypeOfTrainClass(typeOfTrainClass);

        testInvalidTicketArgs(trainTicketResponse, typeOfTrainClass,
                "Type of train class cannot be null!");
    }

    private static Stream<String> emptyAndNullArguments() {
        return Stream.of("", null);
    }


    @Test
    public void testInvalidDayOfDeparture() {
        trainTicketResponse.setDayOfDeparture(null);

        testInvalidTicketArgs(trainTicketResponse, null,
                "Day of departure must be filled in!");
    }

    @Test
    public void testInvalidArrivalDay() {
        trainTicketResponse.setArrivalDay(null);

        testInvalidTicketArgs(trainTicketResponse, null,
                "Arrival day must be filled in!");
    }

    @Test
    public void testInvalidTimeOfDeparture() {
        trainTicketResponse.setTimeOfDeparture(null);

        testInvalidTicketArgs(trainTicketResponse, null,
                "Time of departure must be filled in!");
    }

    @Test
    public void testInvalidArrivalTime() {
        trainTicketResponse.setArrivalTime(null);

        testInvalidTicketArgs(trainTicketResponse, null,
                "Arrival time must be filled in!");
    }

    @Test
    public void testInvalidPriceNull() {
        trainTicketResponse.setPrice(null);

        testInvalidTicketArgs(trainTicketResponse, null,
                "Price cannot be null!");
    }

    @ParameterizedTest
    @MethodSource("getInvalidPriceArgs")
    public void testInvalidPriceArgs(BigDecimal price) {
        trainTicketResponse.setPrice(price);

        testInvalidTicketArgs(trainTicketResponse, price,
                "Price of ticket cannot be lower that zero!");
    }

    private static Stream<BigDecimal> getInvalidPriceArgs() {
        return Stream.of(BigDecimal.valueOf(-0.1),
                BigDecimal.valueOf(-1), BigDecimal.valueOf(-12));
    }

    @ParameterizedTest
    @MethodSource("getInvalidNumberArgs")
    public void testInvalidSeatNumber(int seatNumber) {
        trainTicketResponse.setSeatNumber(seatNumber);

        testInvalidTicketArgs(trainTicketResponse, seatNumber,
                "Number of seats cannot be lower that zero!");
    }

    @ParameterizedTest
    @MethodSource("getInvalidNumberArgs")
    public void testInvalidCarNumber(int carNumber) {
        trainTicketResponse.setCarNumber(carNumber);

        testInvalidTicketArgs(trainTicketResponse, carNumber,
                "Car number cannot be lower that zero!");
    }

    private static Stream<Integer> getInvalidNumberArgs() {
        return Stream.of(-2, -1, -12);
    }

}
