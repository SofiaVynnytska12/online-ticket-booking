package duikt.practice.otb.data.repository;

import duikt.practice.otb.entity.BusTicket;
import duikt.practice.otb.repository.BusTicketRepository;
import duikt.practice.otb.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static duikt.practice.otb.TestAdvice.*;
import static duikt.practice.otb.entity.addition.City.stringToEnum;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class BusTicketRepositoryTests {

    private final BusTicketRepository busTicketRepository;
    private final UserService userService;

    @Autowired
    public BusTicketRepositoryTests(BusTicketRepository busTicketRepository,
                                    UserService userService) {
        this.busTicketRepository = busTicketRepository;
        this.userService = userService;
    }

    @Test
    public void testDESCSortingFindBusTicketsByFromAndToAndOwnerIsNull() {
        String cityFrom = "Kyiv";
        String cityTo = "Dnipro";

        List<BusTicket> expected = getSortedBusTickets(busTicketRepository, cityFrom,
                cityTo, "-");

        List<BusTicket> actual = busTicketRepository
                .findBusTicketsByFromAndToAndOwnerIsNull(
                        stringToEnum(cityFrom), stringToEnum(cityTo),
                        Sort.by(DESC,
                                "dayOfDeparture", "timeOfDeparture",
                                "arrivalDay", "arrivalTime"));

        assertEquals(expected, actual);
    }

    @Test
    public void testASCSortingFindBusTicketsByFromAndToAndOwnerIsNull() {
        String cityFrom = "Kyiv";
        String cityTo = "Dnipro";

        List<BusTicket> expected = getSortedBusTickets(busTicketRepository, cityFrom,
                cityTo, "+");

        List<BusTicket> actual = busTicketRepository
                .findBusTicketsByFromAndToAndOwnerIsNull(
                        stringToEnum(cityFrom), stringToEnum(cityTo),
                        Sort.by(ASC,
                                "dayOfDeparture", "timeOfDeparture",
                                "arrivalDay", "arrivalTime"));

        assertEquals(expected, actual);
    }

    @Test
    public void testPresentFindBusTicketByOwnerIdAndId() {
        Long ownerId = 1L;
        Long ticketId = 9L;

        busTicketRepository.findById(ticketId).get()
                .setOwner(userService.getUserById(ownerId));
        Optional<BusTicket> actualTicket = busTicketRepository
                .findBusTicketByOwnerIdAndId(ownerId, ticketId);

        assertTrue(actualTicket.isPresent());
    }

    @Test
    public void testEmptyFindBusTicketByOwnerIdAndId() {
        Long ownerId = 1L;
        Long ticketId = 9L;

        Optional<BusTicket> actualTicket = busTicketRepository
                .findBusTicketByOwnerIdAndId(ownerId, ticketId);

        assertTrue(actualTicket.isEmpty());
    }

    @Test
    public void testValidFindByOwnerIdAndId(){

        Long owner = 1L;
        Long id = 5L;

        BusTicket saveTicket = busTicketRepository.findById(id).get();
        saveTicket.setOwner(userService.getUserById(owner));
        busTicketRepository.save(saveTicket);
        Assertions.assertTrue(busTicketRepository.findBusTicketByOwnerIdAndId(owner,id).isPresent());
    }
    @Test
    public void testNotEmptyFindByOwnerIdAndId(){

        Long owner = 1L;
        Long id = 5L;

        BusTicket saveTicket = busTicketRepository.findById(id).get();
        saveTicket.setOwner(userService.getUserById(owner));
        busTicketRepository.save(saveTicket);
        Assertions.assertFalse(busTicketRepository.findBusTicketByOwnerIdAndId(owner,id).isEmpty());
    }

}
