package duikt.practice.otb.service.impl;

import duikt.practice.otb.entity.BusTicket;
import duikt.practice.otb.repository.BusTicketRepository;
import duikt.practice.otb.service.BusTicketService;
import duikt.practice.otb.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static duikt.practice.otb.entity.addition.City.stringToEnum;
import static duikt.practice.otb.service.SortAdvice.getDirectionForSort;

import duikt.practice.otb.exception.BookedTicketException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class BusTicketServiceImpl implements BusTicketService {

    private final BusTicketRepository busTicketRepository;
    private final UserService userService;

    @Override
    public BusTicket getTicketById(Long id) {
        return busTicketRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Bus ticket not found!"));
    }

    @Override
    @Cacheable(value = "sorted_bus_tickets", key = "{#cityFrom, #cityTo}")
    public List<BusTicket> getSortedTickets(String cityFrom, String cityTo, String direction) {
        return busTicketRepository
                .findBusTicketsByFromAndToAndOwnerIsNull(
                        stringToEnum(cityFrom), stringToEnum(cityTo),
                        Sort.by(getDirectionForSort(direction),
                                "dayOfDeparture", "timeOfDeparture",
                                "arrivalDay", "arrivalTime"));
    }

    @Override
    public BusTicket returnBusTicket(Long ownerId, Long ticketId) {
        BusTicket ticketToReturn = getTicketById(ticketId);
        ticketToReturn.setOwner(null);
        busTicketRepository.save(ticketToReturn);
        return ticketToReturn;
    }

    @Override
    public boolean isUserTicketOwner(Long ownerId, Long ticketId) {
        return busTicketRepository
                .findBusTicketByOwnerIdAndId(ownerId, ticketId)
                .isPresent();
    }

    @Override
    public BusTicket buyTicket(Long userId, Long id) {
        ifTicketBookedThrowExc(id);
        BusTicket toBuy = getTicketById(id);
        toBuy.setOwner(userService.getUserById(userId));
        return busTicketRepository.save(toBuy);
    }

    @Override
    public boolean isTicketAvailable(Long id) {
        return getTicketById(id).getOwner() == null;
    }

    private void ifTicketBookedThrowExc(Long id){
        if (!(getTicketById(id).getOwner() == null)){
            throw new BookedTicketException("this is not your ticket!");
        }
    }
}
