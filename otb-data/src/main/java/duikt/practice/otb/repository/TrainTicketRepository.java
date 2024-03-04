package duikt.practice.otb.repository;

import duikt.practice.otb.entity.TrainTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainTicketRepository extends JpaRepository<TrainTicket, Long> {
}
