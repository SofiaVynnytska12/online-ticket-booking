package duikt.practice.otb.service.authorize;

import duikt.practice.otb.service.BusTicketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BusTicketAuthorizationService {

    private final BusTicketService busTicketService;
    private final UserAuthorizationService userAuthorizationService;

    public boolean isUserSameAndTicketOwner(
            Long userId, String username, Long busTicketId) {
        return userAuthorizationService.isUserSame(userId, username) &&
                busTicketService.isUserTicketOwner(userId, busTicketId);
    }

    public boolean isUserSameAndTicketAvailable(
            Long userId, String username, Long ticketId) {
        return userAuthorizationService.isUserSame(userId, username)
                && busTicketService.isTicketAvailable(ticketId);
    }

}

