package duikt.practice.otb.service.impl;

import duikt.practice.otb.entity.TrainTicket;
import duikt.practice.otb.repository.TrainTicketRepository;
import duikt.practice.otb.service.TrainTicketService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityNotFoundException;

@Slf4j
@AllArgsConstructor
public class TrainTicketServiceIml implements TrainTicketService {

    private final TrainTicketRepository trainTicketRepository;

    @Override
    public TrainTicket getTicketById(Long id) {
        return trainTicketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket is not found!"));
    }
}
