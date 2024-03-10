package duikt.practice.otb.service;

import duikt.practice.otb.entity.TrainTicket;
import duikt.practice.otb.entity.addition.City;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TrainTicketService {

    TrainTicket getTicketById(Long id);

    TrainTicket buyTicket(Long userId, Long id);

    TrainTicket returnTicket(Long id);

    boolean isUserTicketOwner(Long ownerId, Long id);
    List<TrainTicket> sortedByDateAndTime(String direction, String from, String to);
}
