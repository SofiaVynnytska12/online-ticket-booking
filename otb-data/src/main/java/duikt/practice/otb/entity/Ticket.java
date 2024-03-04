package duikt.practice.otb.entity;

import duikt.practice.otb.entity.addition.City;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@MappedSuperclass
public class Ticket {

    @NotEmpty(message = "Name must be filled in!")
    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @NotEmpty(message = "City from must be filled in!")
    @Column(name = "from_city", nullable = false)
    private City from;

    @Enumerated(EnumType.STRING)
    @NotEmpty(message = "City to must be filled in!")
    @Column(name = "to_city", nullable = false)
    private City to;

    @NotEmpty(message = "Day of departure must be filled in!")
    @Column(name = "day_of_departure", nullable = false)
    private LocalDate dayOfDeparture;

    @NotEmpty(message = "Arrival day must be filled in!")
    @Column(name = "arrival_day", nullable = false)
    private LocalDate arrivalDay;

    @NotEmpty(message = "Time of departure must be filled in!")
    @Column(name = "time_of_departure", nullable = false)
    private LocalTime timeOfDeparture;

    @NotEmpty(message = "Arrival time must be filled in!")
    @Column(name = "arrival_time", nullable = false)
    private LocalTime arrivalTime;

    @Min(value = 0, message = "Price of ticket cannot be lower that zero!")
    @Column(nullable = false)
    private BigDecimal price;

    @Min(value = 0, message = "Number of seats cannot be lower that zero!")
    @Column(name = "seat_number")
    private int seatNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return seatNumber == ticket.seatNumber && Objects.equals(name, ticket.name)
                && from == ticket.from && to == ticket.to
                && Objects.equals(dayOfDeparture, ticket.dayOfDeparture) &&
                Objects.equals(arrivalDay, ticket.arrivalDay) &&
                Objects.equals(timeOfDeparture, ticket.timeOfDeparture) &&
                Objects.equals(arrivalTime, ticket.arrivalTime) &&
                Objects.equals(price, ticket.price);
    }

    @Override
    public int hashCode() {
        return 33 * Objects.hash(name, from, to, dayOfDeparture,
                arrivalDay, timeOfDeparture, arrivalTime, price, seatNumber);
    }
}
