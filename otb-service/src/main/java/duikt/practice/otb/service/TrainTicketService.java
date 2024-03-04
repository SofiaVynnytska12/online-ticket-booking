package duikt.practice.otb.service;

import duikt.practice.otb.entity.TrainTicket;
import org.springframework.stereotype.Service;

@Service
public interface TrainTicketService {

    TrainTicket getTicketById(Long id);

}
