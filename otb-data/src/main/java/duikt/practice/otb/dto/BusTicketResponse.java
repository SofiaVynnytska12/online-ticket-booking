package duikt.practice.otb.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Data
@Getter
@Builder
public class BusTicketResponse {    @NotEmpty(message = "Name must be filled in!")

    @NotEmpty
    private String name;

    @NotEmpty(message = "City from must be filled in!")
    private String from;

    @NotEmpty(message = "City to must be filled in!")
    private String to;

    @NotNull(message = "Day of departure must be filled in!")
    private LocalDate dayOfDeparture;

    @NotNull(message = "Arrival day must be filled in!")
    private LocalDate arrivalDay;

    @NotNull(message = "Time of departure must be filled in!")
    private LocalTime timeOfDeparture;

    @NotNull(message = "Arrival time must be filled in!")
    private LocalTime arrivalTime;

    @NotNull(message = "Price cannot be null!")
    @Min(value = 0, message = "Price of ticket cannot be lower that zero!")
    private BigDecimal price;

    @Min(value = 0, message = "Number of seats cannot be lower that zero!")
    private int seatNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusTicketResponse that = (BusTicketResponse) o;
        return seatNumber == that.seatNumber &&
                Objects.equals(name, that.name) &&
                Objects.equals(from, that.from) &&
                Objects.equals(to, that.to) &&
                Objects.equals(dayOfDeparture, that.dayOfDeparture) &&
                Objects.equals(arrivalDay, that.arrivalDay) &&
                Objects.equals(timeOfDeparture, that.timeOfDeparture) &&
                Objects.equals(arrivalTime, that.arrivalTime) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return 51 * Objects.hash(name, from, to, dayOfDeparture, arrivalDay, timeOfDeparture, arrivalTime, price, seatNumber);
    }

}
