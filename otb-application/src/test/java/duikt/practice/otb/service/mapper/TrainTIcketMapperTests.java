package duikt.practice.otb.service.mapper;

import duikt.practice.otb.dto.TicketSorted;
import duikt.practice.otb.entity.TrainTicket;
import duikt.practice.otb.entity.User;
import duikt.practice.otb.entity.addition.City;
import duikt.practice.otb.entity.addition.TypeOfTrainClass;
import duikt.practice.otb.mapper.TrainTicketMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest
@ActiveProfiles("test")
public class TrainTIcketMapperTests {

    private final TrainTicketMapper trainTicketMapper;

    @Autowired
    public TrainTIcketMapperTests(TrainTicketMapper trainTicketMapper) {
        this.trainTicketMapper = trainTicketMapper;
    }

    @Test
    public void testValidMappingFromTicketTrainTicket(){
        Long id = 1L;
        String name = "abc";
        Integer seatNumber = 23;
        int carNumber = 4;
        User owner = null;
        BigDecimal price = BigDecimal.valueOf(500);
        City from = City.KHARKIV;
        City to = City.KYIV;
        LocalDate dayOfDeparture = LocalDate.now();
        LocalDate arrivalDay = LocalDate.of(2024,9,4);
        LocalTime departureTime = LocalTime.now();
        LocalTime arrivalTime = LocalTime.of(23,23);
        TypeOfTrainClass typeOfTrainClass = TypeOfTrainClass.LUXURY;

        TrainTicket trainTicket = new TrainTicket();
        trainTicket.setId(id);
        trainTicket.setTypeOfTrainClass(typeOfTrainClass);
        trainTicket.setTo(to);
        trainTicket.setFrom(from);
        trainTicket.setDayOfDeparture(dayOfDeparture);
        trainTicket.setArrivalDay(arrivalDay);
        trainTicket.setCarNumber(carNumber);
        trainTicket.setOwner(owner);
        trainTicket.setPrice(price);
        trainTicket.setName(name);
        trainTicket.setArrivalTime(arrivalTime);
        trainTicket.setTimeOfDeparture(departureTime);

        TicketSorted expected = TicketSorted.builder().build();
         expected.setArrivalTime(arrivalTime);
         expected.setTo(to.getName());
         expected.setFrom(from.getName());
         expected.setPrice(price);
         expected.setTypeOfTrainClass(typeOfTrainClass.name());
         expected.setDayOfDeparture(dayOfDeparture);
         expected.setTimeOfDeparture(departureTime);
         expected.setArrivalDay(arrivalDay);

         TicketSorted actual = trainTicketMapper.entityToTicketSorted(trainTicket);

        Assertions.assertEquals(expected,actual);
    }
}
