package duikt.practice.otb.service.impl;

import duikt.practice.otb.entity.TrainTicket;
import duikt.practice.otb.exception.BookedTicketException;
import duikt.practice.otb.repository.TrainTicketRepository;
import duikt.practice.otb.service.TrainTicketService;
import duikt.practice.otb.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import duikt.practice.otb.entity.addition.City;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static duikt.practice.otb.service.SortAdvice.getDirectionForSort;
import static org.springframework.data.domain.Sort.by;

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
        ifTicketBookedThrowExc(id);
        TrainTicket ticketToBuy = getTicketById(id);
        ticketToBuy.setOwner(userService.getUserById(userId));
        return ticketToBuy;
    }

    private void ifTicketBookedThrowExc(Long id) {
        if (!isTicketAvailable(id)) {
            throw new BookedTicketException("This ticket has already booked!");
        }
    }

    @Override
    public boolean isTicketAvailable(Long id) {
        return getTicketById(id).getOwner() == null;
    }

    @Override
    public TrainTicket returnTicket(Long userId, Long id) {
        return trainTicketRepository.save(returnTicketImplAngGetIt(userId, id));
    }

    private TrainTicket returnTicketImplAngGetIt(Long userId, Long id) {
        ifUserNotTicketOwnerThrowExc(userId, id);
        TrainTicket ticketToBuy = getTicketById(id);
        ticketToBuy.setOwner(null);
        return ticketToBuy;
    }

    private void ifUserNotTicketOwnerThrowExc(Long userId, Long id) {
        if (!isUserTicketOwner(userId, id)) {
            throw new BookedTicketException("This ticket is not yours!");
        }
    }

    @Override
    public boolean isUserTicketOwner(Long ownerId, Long id) {
        return trainTicketRepository.findByOwnerIdAndId(ownerId, id)
                .isPresent();
    }

    @Override
    public List<TrainTicket> sortedByDateAndTime(String direction, String from, String to) {
        return trainTicketRepository.findTicketsByFromAndToAndOwnerIsNull(
                City.stringToEnum(from), City.stringToEnum(to), by(getDirectionForSort(direction),
                        "dayOfDeparture", "timeOfDeparture", "arrivalTime"));
    }

}
