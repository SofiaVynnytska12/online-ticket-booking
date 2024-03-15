package duikt.practice.otb.service.servicetests.auth;

import duikt.practice.otb.entity.BusTicket;
import duikt.practice.otb.repository.BusTicketRepository;
import duikt.practice.otb.service.BusTicketService;
import duikt.practice.otb.service.UserService;
import duikt.practice.otb.service.authorize.BusTicketAuthorizationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class BusTicketAuthorizationServiceTests {

    private final BusTicketService busTicketService;
    private final BusTicketRepository busTicketRepository;
    private final BusTicketAuthorizationService busTicketAuthorizationService;
    private final UserService userService;

    @Autowired
    public BusTicketAuthorizationServiceTests(
            BusTicketService busTicketService, BusTicketRepository busTicketRepository,
            BusTicketAuthorizationService busTicketAuthorizationService, UserService userService) {
        this.busTicketService = busTicketService;
        this.busTicketRepository = busTicketRepository;
        this.busTicketAuthorizationService = busTicketAuthorizationService;
        this.userService = userService;
    }

    @Test
    public void testValidIsUserSameAndTicketOwner() {
        Long userId = 2L;
        Long ticketId = 6L;
        String username = "user";
        setOwnerToBusTicket(userId, ticketId);

        assertTrue(busTicketAuthorizationService.isUserSameAndTicketOwner(userId, username, ticketId));
    }

    @Test
    public void testUsersNotSameForIsUserSameAndTicketOwner() {
        Long validUserId = 2L;
        Long invalidUserId = 1L;
        Long ticketId = 6L;
        String username = "user";
        setOwnerToBusTicket(validUserId, ticketId);

        assertFalse(busTicketAuthorizationService.isUserSameAndTicketOwner(invalidUserId, username, ticketId));
    }

    private void setOwnerToBusTicket(Long userId, Long ticketId) {
        BusTicket addOwnerToBusTicket = busTicketService.getTicketById(ticketId);
        addOwnerToBusTicket.setOwner(userService.getUserById(userId));
        busTicketRepository.save(addOwnerToBusTicket);
    }

    @Test
    public void testUserIsNotOwnerOfTicketIsUserSameAndTicketOwner() {
        Long validUserId = 2L;
        Long invalidUserId = 1L;
        Long ticketId = 6L;
        String username = "user";
        setOwnerToBusTicket(invalidUserId, ticketId);

        assertFalse(busTicketAuthorizationService.isUserSameAndTicketOwner(validUserId, username, ticketId));
    }


}
