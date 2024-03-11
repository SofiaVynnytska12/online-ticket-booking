package duikt.practice.otb.data.entity;

import duikt.practice.otb.entity.TrainTicket;
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

import static duikt.practice.otb.data.TestAdvice.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class TrainTicketTests {

    private TrainTicket trainTicket;

    @BeforeEach
    public void setTrainTicket() {
        trainTicket = new TrainTicket();
        trainTicket.setName("Test");
        trainTicket.setFrom(City.DNIPRO);
        trainTicket.setTo(City.KYIV);
        trainTicket.setPrice(BigDecimal.valueOf(200));
        trainTicket.setDayOfDeparture(LocalDate.of(2024, 3, 20));
        trainTicket.setArrivalDay(LocalDate.of(2024, 3, 21));
        trainTicket.setTimeOfDeparture(LocalTime.of(21, 25));
        trainTicket.setArrivalTime(LocalTime.of(3, 12));
        trainTicket.setSeatNumber(12);
        trainTicket.setTypeOfTrainClass(TypeOfTrainClass.FIRST_CLASS);
        trainTicket.setCarNumber(4);
    }


    @Test
    public void testValidTrainTicket() {
        assertEquals(0, getViolation(trainTicket).size());
    }

    @Test
    public void testInvalidTypeOfTrainClass() {
        trainTicket.setTypeOfTrainClass(null);

        testInvalidTicketArgs(trainTicket, null,
                "Type of train class cannot be null!");
    }

    @ParameterizedTest
    @MethodSource("getInvalidCarNumberArgs")
    public void testInvalidCarNumber(int carNumber) {
        trainTicket.setCarNumber(carNumber);

        testInvalidTicketArgs(trainTicket, carNumber,
                "Car number cannot be lower that zero!");
    }

    private static Stream<Integer> getInvalidCarNumberArgs() {
        return Stream.of(-2, -1, -12);
    }
}
