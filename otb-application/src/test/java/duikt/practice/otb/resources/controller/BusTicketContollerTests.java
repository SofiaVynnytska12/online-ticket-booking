package duikt.practice.otb.resources.controller;

import duikt.practice.otb.dto.BusTicketResponse;
import duikt.practice.otb.entity.BusTicket;
import duikt.practice.otb.mapper.BusTicketsMapper;
import duikt.practice.otb.service.BusTicketService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import static duikt.practice.otb.resources.controller.ControllerAdvice.asJsonString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = MOCK)

public class BusTicketContollerTests {
    private static final String BASIC_PATH = "/user/{user_id}/bus_ticket";

    private final MockMvc mockMvc;
    private final BusTicketService busTicketService;
    private final BusTicketsMapper busTicketsMapper;

    @Autowired
    public BusTicketContollerTests(MockMvc mockMvc, BusTicketService busTicketService,
                                    BusTicketsMapper busTicketsMapper) {
        this.mockMvc = mockMvc;
        this.busTicketService = busTicketService;
        this.busTicketsMapper = busTicketsMapper;
    }
    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testValidGetOne() throws Exception{
        Long userId = 2L;
        Long ticketId = 10L;
        String expected = asJsonString(busTicketsMapper.
                entityToBusTicketResponse(busTicketService.getTicketById(ticketId)));
        mockMvc.perform(get(BASIC_PATH + "/{id}",userId,ticketId))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(expected,result.getResponse().getContentAsString()));

    }
    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testForbiddenGetBusTrainTicket() throws Exception {
        Long userId = 1L;
        Long busId = 12L;
        mockMvc.perform(get(BASIC_PATH + "/{id}",
                        userId, busId))
                .andExpect(status().isForbidden())
                .andExpect(forbiddenResult());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testNotFoundGetOneBusTicket() throws Exception {
        Long userId = 2L;
        Long busId = 0L;
        mockMvc.perform(get(BASIC_PATH + "/{id}",
                        userId, busId))
                .andExpect(status().isNotFound())
                .andExpect(notFoundResult());
    }
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testValidTrainTicketBuy() throws Exception{
        Long adminId = 1L;
        Long ticketToBuyId = 9L;
        mockMvc.perform(post(BASIC_PATH + "/buy/{id}",
                adminId, ticketToBuyId))
                .andExpect(status().isCreated());
        assertNotNull(busTicketService.getTicketById(ticketToBuyId).getOwner());

    }
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testTicketIsAlreadyBought() throws Exception {
        Long adminId = 2L;
        Long bookedTicketId = 10L;
        busTicketService.buyTicket(1L, bookedTicketId);

        mockMvc.perform(post(BASIC_PATH + "/buy/{id}",
                        adminId, bookedTicketId))
                .andExpect(status().isForbidden())
                .andExpect(forbiddenResult());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testNotFoundBuyTicket() throws Exception {
        Long userId = 2L;
        Long trainId = 0L;
        mockMvc.perform(post(BASIC_PATH + "/buy/{id}",
                        userId, trainId))
                .andExpect(status().isNotFound())
                .andExpect(notFoundResult());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testUsersNotSameBuyTicket() throws Exception {
        Long adminId = 1L;
        Long ticketId = 9L;

        mockMvc.perform(post(BASIC_PATH + "/buy/{id}",
                        adminId, ticketId))
                .andExpect(status().isForbidden())
                .andExpect(forbiddenResult());
    }


    private ResultMatcher forbiddenResult() {
        return result -> assertTrue(result
                .getResponse()
                .getContentAsString()
                .contains("\"status\":\"FORBIDDEN\",\"message\"" +
                        ":\"Access is denied\""));
    }

    private ResultMatcher notFoundResult() {
        return result -> assertTrue(result
                .getResponse()
                .getContentAsString()
                .contains("\"status\":\"NOT_FOUND\",\"message\"" +
                        ":\"Ticket is not found!\""));
    }
}
