package duikt.practice.otb.service;

import duikt.practice.otb.entity.TrainTicket;
import org.springframework.stereotype.Service;

@Service
public interface TrainTicketService {

    TrainTicket getTicketById(Long id);

    TrainTicket buyTicket(Long userId, Long id);

    TrainTicket returnTicket(Long id);

    boolean isUserTicketOwner(Long ownerId, Long id);

}
