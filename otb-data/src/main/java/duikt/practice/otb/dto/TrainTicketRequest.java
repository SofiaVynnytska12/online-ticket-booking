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

@Data
@Builder
@Getter
public class TrainTicketRequest {

    @NotEmpty(message = "Name must be filled in!")
    private String name;

    @NotEmpty(message = "City from must be filled in!")
    private String from;

    @NotEmpty(message = "City to must be filled in!")
    private String to;

    @NotEmpty(message = "Day of departure must be filled in!")
    private LocalDate dayOfDeparture;

    @NotEmpty(message = "Arrival day must be filled in!")
    private LocalDate arrivalDay;

    @NotEmpty(message = "Time of departure must be filled in!")
    private LocalTime timeOfDeparture;

    @NotEmpty(message = "Arrival time must be filled in!")
    private LocalTime arrivalTime;

    @Min(value = 0, message = "Price of ticket cannot be lower that zero!")
    private BigDecimal price;

    @Min(value = 0, message = "Number of seats cannot be lower that zero!")
    private int seatNumber;

    @NotNull(message = "Type of train class cannot be null!")
    private String typeOfTrainClass;

    @Min(value = 0, message = "Car number cannot be lower that zero!")
    private int carNumber;

}
