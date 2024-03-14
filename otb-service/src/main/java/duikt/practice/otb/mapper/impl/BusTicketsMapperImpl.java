package duikt.practice.otb.mapper.impl;

import duikt.practice.otb.dto.BusTicketResponse;
import duikt.practice.otb.dto.TrainTicketResponse;
import duikt.practice.otb.entity.BusTicket;
import duikt.practice.otb.entity.TrainTicket;
import duikt.practice.otb.mapper.BusTicketsMapper;

public class BusTicketsMapperImpl implements BusTicketsMapper {
    @Override
    public BusTicketResponse entityToBusTicketResponse(BusTicket busTicket) {
        return BusTicketResponse.builder()
                .name(busTicket.getName())
                .from(busTicket.getFrom().getName())
                .to(busTicket.getTo().getName())
                .dayOfDeparture(busTicket.getDayOfDeparture())
                .arrivalDay(busTicket.getArrivalDay())
                .timeOfDeparture(busTicket.getTimeOfDeparture())
                .arrivalTime(busTicket.getArrivalTime())
                .price(busTicket.getPrice())
                .seatNumber(busTicket.getSeatNumber())
                .build();
    }
}
