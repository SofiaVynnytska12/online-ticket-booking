package duikt.practice.otb.service.servicetests;

import duikt.practice.otb.entity.BusTicket;
import duikt.practice.otb.entity.addition.City;
import duikt.practice.otb.repository.BusTicketRepository;
import duikt.practice.otb.service.BusTicketService;
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
import java.util.List;

import static duikt.practice.otb.TestAdvice.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class BusTicketServiceTests {

    private final BusTicketRepository busTicketRepository;
    private final UserService userService;
    private final BusTicketService busTicketService;

    @Autowired
    public BusTicketServiceTests(
            BusTicketRepository busTicketRepository, UserService userService,
            BusTicketService busTicketService) {
        this.busTicketRepository = busTicketRepository;
        this.userService = userService;
        this.busTicketService = busTicketService;
    }

    @Test
    public void testValidReadById() {
        BusTicket expected = getBusTicket();
        busTicketRepository.save(expected);
        BusTicket actual = busTicketService.getTicketById(expected.getId());

        assertEquals(expected, actual);
    }

    private BusTicket getBusTicket() {
        BusTicket busTicket = new BusTicket();
        busTicket.setName("Name");
        busTicket.setTo(City.KYIV);
        busTicket.setFrom(City.DNIPRO);
        busTicket.setPrice(BigDecimal.valueOf(124));
        busTicket.setArrivalDay(LocalDate.of(2024, 4, 4));
        busTicket.setDayOfDeparture(LocalDate.of(2024, 4, 4));
        busTicket.setArrivalTime(LocalTime.of(12, 4));
        busTicket.setTimeOfDeparture(LocalTime.of(21, 4));
        busTicket.setSeatNumber(124);

        return busTicket;
    }

    @Test
    public void testEntityNotFoundExcReadById() {
        assertThrows(EntityNotFoundException.class, () -> busTicketService.getTicketById(0L));
    }

    @Test
    public void testDESCGetSortedTickets() {
        String cityFrom = "Dnipro";
        String cityTo = "Kharkiv";
        String descDirection = "-";

        List<BusTicket> expected = getSortedBusTickets(busTicketRepository, cityFrom,
                cityTo, descDirection);

        List<BusTicket> actual = busTicketService
                .getSortedTickets(cityFrom, cityTo, descDirection);

        assertEquals(expected, actual);
    }

    @Test
    public void testASCGetSortedTickets() {
        String cityFrom = "Kharkiv";
        String cityTo = "Kyiv";
        String ascDirection = "+";

        List<BusTicket> expected = getSortedBusTickets(busTicketRepository, cityFrom,
                cityTo, ascDirection);

        List<BusTicket> actual = busTicketService
                .getSortedTickets(cityFrom, cityTo, ascDirection);

        assertEquals(expected, actual);
    }

    @Test
    public void testValidReturnBusTicket() {
        Long ownerId = 2L;
        Long busTicketId = 4L;

        buyTicketSimulation(ownerId, busTicketId);
        assertNotNull(busTicketService.getTicketById(busTicketId).getOwner());

        busTicketService.returnBusTicket(ownerId, busTicketId);
        assertNull(busTicketService.getTicketById(busTicketId).getOwner());
    }

    @Test
    public void testTicketNotFoundReturnBusTicket() {
        assertThrows(EntityNotFoundException.class, () ->
                busTicketService.returnBusTicket(1L, 0L));
    }

    @Test
    public void testValidIsUserTicketOwner() {
        Long userId = 2L;
        Long busTicketId = 10L;
        buyTicketSimulation(userId, busTicketId);

        assertTrue(busTicketService.isUserTicketOwner(userId, busTicketId));
    }

    @Test
    public void testInvalidIsUserTicketOwner() {
        Long userId = 1L;
        Long busTicketId = 3L;

        assertFalse(busTicketService.isUserTicketOwner(userId, busTicketId));
    }

    private void buyTicketSimulation(Long userId, Long ticketId) {
        BusTicket busTicket = busTicketService.getTicketById(ticketId);
        busTicket.setOwner(userService.getUserById(userId));
        busTicketRepository.save(busTicket);
    }

}
