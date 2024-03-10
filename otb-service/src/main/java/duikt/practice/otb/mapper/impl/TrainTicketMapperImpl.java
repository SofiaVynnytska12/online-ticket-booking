package duikt.practice.otb.mapper.impl;

import duikt.practice.otb.dto.TicketSorted;
import duikt.practice.otb.dto.TrainTicketResponse;
import duikt.practice.otb.entity.TrainTicket;
import duikt.practice.otb.mapper.TrainTicketMapper;

public class TrainTicketMapperImpl implements TrainTicketMapper {

    @Override
    public TrainTicketResponse entityToTrainTicketResponse(TrainTicket trainTicket) {
        return TrainTicketResponse.builder()
                .name(trainTicket.getName())
                .from(trainTicket.getFrom().getName())
                .to(trainTicket.getTo().getName())
                .dayOfDeparture(trainTicket.getDayOfDeparture())
                .arrivalDay(trainTicket.getArrivalDay())
                .timeOfDeparture(trainTicket.getTimeOfDeparture())
                .arrivalTime(trainTicket.getArrivalTime())
                .price(trainTicket.getPrice())
                .seatNumber(trainTicket.getSeatNumber())
                .typeOfTrainClass(trainTicket.getTypeOfTrainClass().name())
                .carNumber(trainTicket.getCarNumber())
                .build();
    }

    @Override
    public TicketSorted entityToTicketSorted(TrainTicket trainTicket) {
        return TicketSorted.builder()
                .dayOfDeparture(trainTicket.getDayOfDeparture())
                .from(trainTicket.getFrom().getName())
                .to(trainTicket.getTo().getName())
                .timeOfDeparture(trainTicket.getTimeOfDeparture())
                .arrivalTime(trainTicket.getArrivalTime())
                .typeOfTrainClass(trainTicket.getTypeOfTrainClass().name())
                .price(trainTicket.getPrice())
                .build();
    }

}
