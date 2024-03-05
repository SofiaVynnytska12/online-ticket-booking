package duikt.practice.otb.service.authorize;

import duikt.practice.otb.entity.User;
import duikt.practice.otb.service.TrainTicketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrainTicketAuthorizationService {

    private final UserAuthorizationService userAuthorizationService;
    private final TrainTicketService trainTicketService;

    public boolean isUserSameAndTicketAvailable(
            Long userId, String username, Long ticketId) {
        return userAuthorizationService.isUserSame(userId, username)
                && getTrainTicketOwner(ticketId) == null;
    }

    public boolean isUserSameAndTicketOwner(
            Long userId, String username, Long ticketId) {
        return userAuthorizationService.isUserSame(userId, username)
                && trainTicketService.isUserTicketOwner(userId, ticketId);
    }

    private User getTrainTicketOwner(Long id) {
        return trainTicketService.getTicketById(id).getOwner();
    }

}
