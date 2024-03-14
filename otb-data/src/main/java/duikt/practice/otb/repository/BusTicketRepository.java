package duikt.practice.otb.repository;

import duikt.practice.otb.entity.BusTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusTicketRepository extends JpaRepository<BusTicket, Long> {
    Optional<BusTicket> findByOwnerIdAndId(Long ownerId, Long id);
}
