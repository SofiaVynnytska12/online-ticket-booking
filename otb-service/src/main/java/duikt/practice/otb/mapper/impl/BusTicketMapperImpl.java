package duikt.practice.otb.mapper.impl;

import duikt.practice.otb.dto.BusTicketResponse;
import duikt.practice.otb.entity.BusTicket;
import duikt.practice.otb.mapper.BusTicketMapper;

import java.util.List;

import static java.util.stream.Collectors.*;

public class BusTicketMapperImpl implements BusTicketMapper {

    @Override
    public BusTicketResponse getResponseFromEntity(BusTicket busTicket) {
        return BusTicketResponse.builder()
                .name(busTicket.getName())
                .from(busTicket.getFrom().getName())
                .to(busTicket.getTo().getName())
                .price(busTicket.getPrice())
                .dayOfDeparture(busTicket.getDayOfDeparture())
                .arrivalDay(busTicket.getArrivalDay())
                .timeOfDeparture(busTicket.getTimeOfDeparture())
                .arrivalTime(busTicket.getArrivalTime())
                .seatNumber(busTicket.getSeatNumber())
                .build();
    }

    @Override
    public List<BusTicketResponse> getResponseListFromEntityList(List<BusTicket> busTicketList) {
        return busTicketList.stream()
                .map(this::getResponseFromEntity)
                .collect(toList());
    }
}
