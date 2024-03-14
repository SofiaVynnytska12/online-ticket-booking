package duikt.practice.otb.service.authorize;

import duikt.practice.otb.service.BusTicketService;
import duikt.practice.otb.service.TrainTicketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BusTicketAuthorizationService {

    private final UserAuthorizationService userAuthorizationService;
    private final BusTicketService busTicketService;

    public boolean isUserSameAndTicketAvailable(
            Long userId, String username, Long ticketId) {
        return userAuthorizationService.isUserSame(userId, username)
                && busTicketService.isTicketAvailable(ticketId);
    }

    public boolean isUserSameAndTicketOwner(
            Long userId, String username, Long ticketId) {
        return userAuthorizationService.isUserSame(userId, username)
                && busTicketService.isUserTicketOwner(userId, ticketId);
    }
}