package duikt.practice.otb.repository;

import duikt.practice.otb.entity.BusTicket;
import duikt.practice.otb.entity.addition.City;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusTicketRepository extends JpaRepository<BusTicket, Long> {

    List<BusTicket> findBusTicketsByFromAndToAndOwnerIsNull(City from, City to, Sort sort);

    Optional<BusTicket> findBusTicketByOwnerIdAndId(Long ownerId, Long ticketId);

}
