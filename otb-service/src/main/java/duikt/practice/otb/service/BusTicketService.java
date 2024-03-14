package duikt.practice.otb.service;

import duikt.practice.otb.entity.BusTicket;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BusTicketService {

    BusTicket getTicketById(Long id);

    List<BusTicket> getSortedTickets(String cityFrom, String cityTo, String direction);

    BusTicket returnBusTicket(Long ownerId, Long ticketId);

    boolean isUserTicketOwner(Long ownerId, Long ticketId);

    BusTicket buyTicket(Long userId, Long id);

    boolean isTicketAvailable(Long id);
}
