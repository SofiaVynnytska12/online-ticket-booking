package duikt.practice.otb.data.repository;

import duikt.practice.otb.entity.BusTicket;
import duikt.practice.otb.repository.BusTicketRepository;
import duikt.practice.otb.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;



@Transactional
@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BusTicketRepositoryTests {
    private final BusTicketRepository busTicketRepository;
    private final UserService userService;

    @Autowired
    public BusTicketRepositoryTests(BusTicketRepository busTicketRepository,
                                      UserService userService) {
        this.busTicketRepository = busTicketRepository;
        this.userService = userService;
    }
    @Test
    public void testValidFindByOwnerIdAndId(){

        Long owner = 1L;
        Long id = 5L;

        BusTicket saveTicket = busTicketRepository.findById(id).get();
        saveTicket.setOwner(userService.getUserById(owner));
        busTicketRepository.save(saveTicket);
        Assertions.assertTrue(busTicketRepository.findByOwnerIdAndId(owner,id).isPresent());
    }
    @Test
    public void testNotEmptyFindByOwnerIdAndId(){

        Long owner = 1L;
        Long id = 5L;

        BusTicket saveTicket = busTicketRepository.findById(id).get();
        saveTicket.setOwner(userService.getUserById(owner));
        busTicketRepository.save(saveTicket);
        Assertions.assertFalse(busTicketRepository.findByOwnerIdAndId(owner,id).isEmpty());
    }
}
