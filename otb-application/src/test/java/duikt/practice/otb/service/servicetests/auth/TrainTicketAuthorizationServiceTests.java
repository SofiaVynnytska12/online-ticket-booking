package duikt.practice.otb.service.servicetests.auth;

import duikt.practice.otb.service.TrainTicketService;
import duikt.practice.otb.service.authorize.TrainTicketAuthorizationService;
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
public class TrainTicketAuthorizationServiceTests {

    private final TrainTicketService trainTicketService;

    private final TrainTicketAuthorizationService authorizationService;

    @Autowired
    public TrainTicketAuthorizationServiceTests(TrainTicketService trainTicketService,
                                                TrainTicketAuthorizationService authorizationService) {
        this.trainTicketService = trainTicketService;
        this.authorizationService = authorizationService;
    }

    @Test
    public void testTrueIsUserSameAndTicketAvailable() {
        Long adminId = 1L;
        String adminName = "admin";
        Long ticketId = 12L;

        assertTrue(authorizationService
                .isUserSameAndTicketAvailable(adminId, adminName, ticketId));
    }

    @Test
    public void testUserIsNotSame_IsUserSameAndTicketAvailable() {
        Long adminId = 1L;
        String username = "user";
        Long ticketId = 12L;

        assertFalse(authorizationService
                .isUserSameAndTicketAvailable(adminId, username, ticketId));
    }

    @Test
    public void testTicketIsNotAvailable_IsUserSameAndTicketAvailable() {
        Long userId = 2L;
        String username = "user";
        Long ticketId = 12L;
        trainTicketService.buyTicket(1L, ticketId);
        assertFalse(authorizationService
                .isUserSameAndTicketAvailable(userId, username, ticketId));
    }

    @Test
    public void testTrueIsUserSameAndTicketOwner() {
        Long adminId = 1L;
        String adminName = "admin";
        Long ticketId = 12L;
        trainTicketService.buyTicket(adminId, ticketId);

        assertTrue(authorizationService
                .isUserSameAndTicketOwner(adminId, adminName, ticketId));
    }

    @Test
    public void testUserIsNotSame_isUserSameAndTicketOwner() {
        Long adminId = 1L;
        String username = "user";
        Long ticketId = 12L;

        assertFalse(authorizationService
                .isUserSameAndTicketOwner(adminId, username, ticketId));
    }

    @Test
    public void testTicketIsNotAvailable_isUserSameAndTicketOwner() {
        Long userId = 2L;
        String username = "user";
        Long ticketId = 12L;

        assertFalse(authorizationService
                .isUserSameAndTicketOwner(userId, username, ticketId));
    }

}
