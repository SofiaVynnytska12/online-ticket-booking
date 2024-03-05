package duikt.practice.otb.service.impl;

import duikt.practice.otb.entity.TrainTicket;
import duikt.practice.otb.repository.TrainTicketRepository;
import duikt.practice.otb.service.TrainTicketService;
import duikt.practice.otb.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityNotFoundException;

@Slf4j
@AllArgsConstructor
public class TrainTicketServiceIml implements TrainTicketService {

    private final TrainTicketRepository trainTicketRepository;
    private final UserService userService;

    @Override
    public TrainTicket getTicketById(Long id) {
        return trainTicketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket is not found!"));
    }

    @Override
    public TrainTicket buyTicket(Long userId, Long id) {
        return trainTicketRepository.save(buyTicketImplAngGetIt(userId, id));
    }

    private TrainTicket buyTicketImplAngGetIt(Long userId, Long id) {
        TrainTicket ticketToBuy = getTicketById(id);
        ticketToBuy.setOwner(userService.getUserById(userId));
        return ticketToBuy;
    }

    @Override
    public TrainTicket returnTicket(Long id) {
        return trainTicketRepository.save(returnTicketImplAngGetIt(id));
    }

    private TrainTicket returnTicketImplAngGetIt(Long id) {
        TrainTicket ticketToBuy = getTicketById(id);
        ticketToBuy.setOwner(null);
        return ticketToBuy;
    }

    @Override
    public boolean isUserTicketOwner(Long ownerId, Long id) {
        return trainTicketRepository.isUserTicketOwner(ownerId, id);
    }

}
