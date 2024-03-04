package duikt.practice.otb.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Table(name = "bus_tickets")
public class BusTicket extends Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "owner_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private User owner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BusTicket busTicket = (BusTicket) o;
        return Objects.equals(id, busTicket.id) && Objects.equals(owner, busTicket.owner);
    }

    @Override
    public int hashCode() {
        return 59 * Objects.hash(super.hashCode(), id, owner);
    }
}
