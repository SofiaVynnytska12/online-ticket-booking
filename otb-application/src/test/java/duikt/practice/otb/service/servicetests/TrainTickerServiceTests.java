package duikt.practice.otb.service.servicetests;

import duikt.practice.otb.entity.TrainTicket;
import duikt.practice.otb.entity.addition.City;
import duikt.practice.otb.entity.addition.TypeOfTrainClass;
import duikt.practice.otb.exception.BookedTicketException;
import duikt.practice.otb.repository.TrainTicketRepository;
import duikt.practice.otb.service.TrainTicketService;
import duikt.practice.otb.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class TrainTickerServiceTests {

    private final TrainTicketRepository trainTicketRepository;
    private final UserService userService;
    private final TrainTicketService trainTicketService;

    @Autowired
    public TrainTickerServiceTests(
            TrainTicketRepository trainTicketRepository, UserService userService,
            TrainTicketService trainTicketService) {
        this.trainTicketRepository = trainTicketRepository;
        this.userService = userService;
        this.trainTicketService = trainTicketService;
    }

    @Test
    public void testValidGetTicketById() {
        TrainTicket expected = getTrainTicket();
        trainTicketRepository.save(expected);
        TrainTicket actual = trainTicketService.getTicketById(expected.getId());

        assertEquals(expected, actual);
    }

    private TrainTicket getTrainTicket() {
        TrainTicket trainTicket = new TrainTicket();
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

        return trainTicket;
    }

    @Test
    public void testInvalidGetTicketById() {
        assertThrows(EntityNotFoundException.class,
                () -> trainTicketService.getTicketById(0L));
    }

    @Test
    public void testValidBuyTicket() {
        long userId = 2;
        long ticketId = 22;

        TrainTicket trainTicket = trainTicketService.getTicketById(ticketId);
        assertNull(trainTicket.getOwner());

        trainTicket = buyTicketSimulation(userId, ticketId);
        assertNotNull(trainTicket.getOwner());
    }

    @Test
    public void testAlreadyBookedBuyTicket() {
        long userIdThatBuyTicket = 2;
        long userIdThatWantBuyTicket = 1;
        long ticketId = 22;

        buyTicketSimulation(userIdThatBuyTicket, ticketId);
        assertThrows(BookedTicketException.class,
                () -> trainTicketService.buyTicket(userIdThatWantBuyTicket, ticketId));
    }

    @Test
    public void testTrueIsTicketAvailable() {
        assertTrue(trainTicketService.isTicketAvailable(2L));
    }

    @Test
    public void testFalseIsTicketAvailable() {
        long ticketId = 2L;
        TrainTicket trainTicket = trainTicketService.getTicketById(ticketId);
        trainTicket.setOwner(userService.getUserById(1L));
        trainTicketRepository.save(trainTicket);

        assertFalse(trainTicketService.isTicketAvailable(2L));
    }

    @Test
    public void testValidReturnTicket() {
        long userIdThatBuyTicket = 2;
        long ticketId = 22;

        buyTicketSimulation(userIdThatBuyTicket, ticketId);
        assertNull(trainTicketService.returnTicket(userIdThatBuyTicket, ticketId).getOwner());
    }

    @Test
    public void testInvalidReturnTicket() {
        long userIdThatBuyTicket = 2;
        long userIdThatWantBuyTicket = 1;
        long ticketId = 22;

        buyTicketSimulation(userIdThatBuyTicket, ticketId);
        assertThrows(BookedTicketException.class,
                () -> trainTicketService.returnTicket(userIdThatWantBuyTicket, ticketId));
    }

    @Test
    public void testValidIsUserTicketOwner() {
        long userIdThatBuyTicket = 1;
        long ticketId = 2;

        buyTicketSimulation(userIdThatBuyTicket, ticketId);
        assertTrue(trainTicketService.isUserTicketOwner(userIdThatBuyTicket, ticketId));
    }

    @Test
    public void testFalseIsUserTicketOwner() {
        long userIdThatBuyTicket = 1;
        long ticketId = 2;

        buyTicketSimulation(userIdThatBuyTicket, ticketId);
        assertFalse(trainTicketService.isUserTicketOwner(2L, ticketId));
    }

    private TrainTicket buyTicketSimulation(Long userIdThatBuyTicket, Long ticketId) {
       return trainTicketService.buyTicket(userIdThatBuyTicket, ticketId);
    }

}
