package duikt.practice.otb.entity;

import duikt.practice.otb.entity.addition.TypeOfTrainClass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Table(name = "train_tickets")
public class TrainTicket extends Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Type of train class cannot be null!")
    @Column(name = "type_of_train_class", nullable = false)
    private TypeOfTrainClass typeOfTrainClass;

    @Min(value = 0, message = "Car number cannot be lower that zero!")
    @Column(name = "car_number")
    private int carNumber;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TrainTicket that = (TrainTicket) o;
        return carNumber == that.carNumber && Objects.equals(id, that.id)
                && typeOfTrainClass == that.typeOfTrainClass &&
                Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return 55 * Objects.hash(super.hashCode(), id, typeOfTrainClass, carNumber, owner);
    }
}
