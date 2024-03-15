package duikt.practice.otb.mapper;

import duikt.practice.otb.dto.BusTicketResponse;
import duikt.practice.otb.entity.BusTicket;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BusTicketMapper {

    BusTicketResponse getResponseFromEntity(BusTicket busTicket);

    List<BusTicketResponse> getResponseListFromEntityList(List<BusTicket> busTicketList);

}
