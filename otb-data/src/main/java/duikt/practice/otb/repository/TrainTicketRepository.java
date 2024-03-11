package duikt.practice.otb.repository;

import duikt.practice.otb.entity.TrainTicket;
import duikt.practice.otb.entity.addition.City;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainTicketRepository extends JpaRepository<TrainTicket, Long> {

    Optional<TrainTicket> findByOwnerIdAndId(Long ownerId, Long id);

    List<TrainTicket> findTicketsByFromAndToAndOwnerIsNull(@NotNull(message = "City from must be filled in!") City from,
                                                           @NotNull(message = "City to must be filled in!") City to, Sort sort);
}