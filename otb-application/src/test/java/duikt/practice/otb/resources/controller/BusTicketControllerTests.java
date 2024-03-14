package duikt.practice.otb.resources.controller;

import duikt.practice.otb.dto.BusTicketResponse;
import duikt.practice.otb.entity.BusTicket;
import duikt.practice.otb.mapper.BusTicketMapper;
import duikt.practice.otb.repository.BusTicketRepository;
import duikt.practice.otb.service.BusTicketService;
import duikt.practice.otb.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static duikt.practice.otb.TestAdvice.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = MOCK)
public class BusTicketControllerTests {

    private static final String BASIC_PATH = "/user/{user_id}/bus_ticket";

    private final MockMvc mockMvc;
    private final BusTicketService busTicketService;
    private final BusTicketMapper busTicketMapper;
    private final BusTicketRepository busTicketRepository;
    private final UserService userService;

    @Autowired
    public BusTicketControllerTests(MockMvc mockMvc, BusTicketService busTicketService,
                                    BusTicketMapper busTicketMapper, UserService userService,
                                    BusTicketRepository busTicketRepository) {
        this.mockMvc = mockMvc;
        this.busTicketService = busTicketService;
        this.busTicketMapper = busTicketMapper;
        this.busTicketRepository = busTicketRepository;
        this.userService = userService;
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testValidGetAll() throws Exception {
        Long adminId = 1L;
        String cityTo = "Kharkiv";
        String cityFrom = "WARSAW";
        String direction = "+";
        List<BusTicketResponse> busTickets = busTicketMapper
                .getResponseListFromEntityList(getSortedBusTickets(busTicketRepository,
                        cityFrom, cityTo, direction));
        String expectedList = asJsonString(busTickets);

        mockMvc.perform(get(BASIC_PATH, adminId)
                        .param("city_from", cityFrom)
                        .param("city_to", cityTo)
                        .param("direction", direction))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(expectedList,
                        result.getResponse().getContentAsString()));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testForbiddenGetAll() throws Exception {
        Long userId = 2L;
        String cityTo = "Kharkiv";
        String cityFrom = "WARSAW";
        String direction = "+";

        mockMvc.perform(get(BASIC_PATH, userId)
                        .param("city_from", cityFrom)
                        .param("city_to", cityTo)
                        .param("direction", direction))
                .andExpect(status().isForbidden())
                .andExpect(forbiddenResult());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testValidReturnBusTicket() throws Exception {
        Long ownerId = 2L;
        Long busTicketId = 4L;

        BusTicket ticketToBuy = busTicketService.getTicketById(busTicketId);
        String expectedTicket = asJsonString(busTicketMapper
                .getResponseFromEntity(ticketToBuy));
        ticketToBuy.setOwner(userService.getUserById(ownerId));
        busTicketRepository.save(ticketToBuy);
        assertNotNull(busTicketService.getTicketById(busTicketId).getOwner());

        mockMvc.perform(delete(BASIC_PATH + "/return/{id}",
                        ownerId, busTicketId))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(expectedTicket,
                        result.getResponse().getContentAsString()));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testIsNotUsersTicketReturnBusTicket() throws Exception {
        Long ownerId = 2L;
        Long busTicketId = 8L;

        mockMvc.perform(delete(BASIC_PATH + "/return/{id}",
                        ownerId, busTicketId))
                .andExpect(status().isForbidden())
                .andExpect(forbiddenResult());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testInvalidTicketIdReturnBusTicket() throws Exception {
        Long ownerId = 1L;
        Long busTicketId = 0L;

        mockMvc.perform(delete(BASIC_PATH + "/return/{id}",
                        ownerId, busTicketId))
                .andExpect(status().isForbidden())
                .andExpect(forbiddenResult());
    }

}
