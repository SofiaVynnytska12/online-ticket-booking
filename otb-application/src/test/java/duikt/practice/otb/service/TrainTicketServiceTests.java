package duikt.practice.otb.service;

import duikt.practice.otb.entity.TrainTicket;
import duikt.practice.otb.entity.addition.City;
import duikt.practice.otb.repository.TrainTicketRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;


@Transactional
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class TrainTicketServiceTests {
    private final TrainTicketService trainTicketService;
    private final TrainTicketRepository trainTicketRepository;

    @Autowired
    public TrainTicketServiceTests(TrainTicketService trainTicketService, TrainTicketRepository trainTicketRepository) {
        this.trainTicketService = trainTicketService;
        this.trainTicketRepository = trainTicketRepository;
    }

    @Test
    public void testValidGetSortedByDayAndTime() {
        String direction = "desc";
        Sort sort = Sort.by(Sort.Direction.fromString(direction), "dayOfDeparture", "timeOfDeparture", "arrivalTime");
        String from = "Kyiv";
        String to = "Kharkiv";

        List<TrainTicket> expected = trainTicketRepository.findTicketsByFromAndToAndOwnerIsNull(City.stringToEnum(from), City.stringToEnum(to), sort);
        List<TrainTicket> actual = trainTicketService.sortedByDateAndTime(direction, from, to);

        Assertions.assertEquals(expected, actual);
    }
}
