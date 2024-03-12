package duikt.practice.otb.mapper;

import duikt.practice.otb.dto.BusTicketResponse;
import duikt.practice.otb.dto.TrainTicketResponse;
import duikt.practice.otb.entity.BusTicket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BusTicketsMapper {
    BusTicketResponse entityToBusTicketResponse(BusTicket busTicket);
}
