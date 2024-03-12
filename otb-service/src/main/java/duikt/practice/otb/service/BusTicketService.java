package duikt.practice.otb.service;

import duikt.practice.otb.entity.BusTicket;
import duikt.practice.otb.entity.TrainTicket;
import org.springframework.stereotype.Service;

@Service
public interface BusTicketService {
    BusTicket getTicketById(Long id);

    BusTicket buyTicket(Long userId, Long id);

    boolean isUserTicketOwner(Long ownerId, Long id);
     boolean isTicketAvailable(Long id);

}
