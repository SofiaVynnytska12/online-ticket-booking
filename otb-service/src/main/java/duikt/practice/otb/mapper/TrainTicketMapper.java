package duikt.practice.otb.mapper;

import duikt.practice.otb.dto.TrainTicketResponse;
import duikt.practice.otb.entity.TrainTicket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrainTicketMapper {

    @Mapping(target = "from", expression = "java(trainTicket.getFrom().getName())")
    @Mapping(target = "to", expression = "java(trainTicket.getTo().getName())")
    @Mapping(target = "typeOfTrainClass", expression = "java(trainTicket.getTypeOfTrainClass().name())")
    TrainTicketResponse entityToTrainTicketResponse(TrainTicket trainTicket);

}
