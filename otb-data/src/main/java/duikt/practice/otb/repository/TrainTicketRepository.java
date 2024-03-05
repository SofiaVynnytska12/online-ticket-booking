package duikt.practice.otb.repository;

import duikt.practice.otb.entity.TrainTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainTicketRepository extends JpaRepository<TrainTicket, Long> {

    @Query("SELECT CASE WHEN t.owner.id = :owner_id THEN true " +
            "ELSE false END FROM TrainTicket t WHERE t.id = :id")
    boolean isUserTicketOwner(@Param("owner_id") Long ownerId, @Param("id") Long id);


}
