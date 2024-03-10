package duikt.practice.otb.repository;

import duikt.practice.otb.entity.TrainTicket;
import duikt.practice.otb.entity.User;
import duikt.practice.otb.entity.addition.City;
import duikt.practice.otb.entity.addition.TypeOfTrainClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class TrainTicketRepositoryTests {

    private final TrainTicketRepository trainTicketRepository;
    @Autowired
    public TrainTicketRepositoryTests(TrainTicketRepository trainTicketRepository) {
        this.trainTicketRepository = trainTicketRepository;
    }

    @Test
    public void testFindTicketsByFromAndToAndOwnerIsNull() {

        Long id = 1L;
        Long id2 = 2L;
        String name = "abc";
        String name2 = "bca";
        int seatNumber = 23;
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

        TrainTicket ticket1 = new TrainTicket();

        ticket1.setId(id);
        ticket1.setTypeOfTrainClass(typeOfTrainClass);
        ticket1.setTo(to);
        ticket1.setFrom(from);
        ticket1.setDayOfDeparture(dayOfDeparture);
        ticket1.setArrivalDay(arrivalDay);
        ticket1.setCarNumber(carNumber);
        ticket1.setOwner(null);
        ticket1.setPrice(price);
        ticket1.setName(name);
        ticket1.setArrivalTime(arrivalTime);
        ticket1.setTimeOfDeparture(departureTime);
        ticket1.setSeatNumber(seatNumber);

        TrainTicket ticket2 = new TrainTicket();

        ticket2.setId(id2);
        ticket2.setTypeOfTrainClass(typeOfTrainClass);
        ticket2.setTo(to);
        ticket2.setFrom(from);
        ticket2.setDayOfDeparture(dayOfDeparture);
        ticket2.setArrivalDay(arrivalDay);
        ticket2.setCarNumber(carNumber);
        ticket2.setOwner(owner);
        ticket2.setPrice(price);
        ticket2.setName(name2);
        ticket2.setArrivalTime(arrivalTime);
        ticket2.setTimeOfDeparture(departureTime);
        ticket2.setSeatNumber(seatNumber);

        trainTicketRepository.saveAndFlush(ticket1);
       trainTicketRepository.saveAndFlush(ticket2);

        List<TrainTicket> tickets = trainTicketRepository.findTicketsByFromAndToAndOwnerIsNull(from, to,null);

        assertNotNull(tickets);
        assertTrue(tickets.contains(ticket1));
        assertTrue(tickets.contains(ticket2));
    }
}

