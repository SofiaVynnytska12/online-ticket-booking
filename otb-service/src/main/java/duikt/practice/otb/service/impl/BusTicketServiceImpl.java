package duikt.practice.otb.service.impl;

import duikt.practice.otb.entity.BusTicket;
import duikt.practice.otb.entity.TrainTicket;
import duikt.practice.otb.exception.BookedTicketException;
import duikt.practice.otb.repository.BusTicketRepository;
import duikt.practice.otb.repository.TrainTicketRepository;
import duikt.practice.otb.service.BusTicketService;
import duikt.practice.otb.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityNotFoundException;
@Slf4j
@AllArgsConstructor
public class BusTicketServiceImpl implements BusTicketService {

    private final BusTicketRepository busTicketRepository;
    private final UserService userService;

    @Override
    public BusTicket getTicketById(Long id) {
        return busTicketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket is not found!"));
    }

    @Override
    public BusTicket buyTicket(Long userId, Long id) {
        ifTicketBookedThrowExc(id);
        BusTicket toBuy = getTicketById(id);
        toBuy.setOwner(userService.getUserById(userId));
        return busTicketRepository.save(toBuy);
    }

    @Override
    public boolean isUserTicketOwner(Long ownerId, Long id) {
        return busTicketRepository.findByOwnerIdAndId(ownerId, id)
                .isPresent();
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
