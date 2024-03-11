package duikt.practice.otb.resources.controller;

import duikt.practice.otb.dto.TicketSorted;
import duikt.practice.otb.mapper.TrainTicketMapper;
import duikt.practice.otb.service.TrainTicketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static duikt.practice.otb.resources.controller.ControllerAdvice.asJsonString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = MOCK)
public class TrainTicketControllerTests {

    private static final String BASIC_PATH = "/user/{user_id}/train_ticket";

    private final MockMvc mockMvc;
    private final TrainTicketService trainTicketService;
    private final TrainTicketMapper trainTicketMapper;

    @Autowired
    public TrainTicketControllerTests(MockMvc mockMvc, TrainTicketService trainTicketService,
                                      TrainTicketMapper trainTicketMapper) {
        this.mockMvc = mockMvc;
        this.trainTicketService = trainTicketService;
        this.trainTicketMapper = trainTicketMapper;
    }
    @Test
    @WithMockUser(username = "user")
    public void testValidGetSortedTickets() throws Exception{
        String sortDirection = "-";
        String from = "Kyiv";
        String to = "Kharkiv";
        Long id = 1L;
        List<TicketSorted> sortedTickets = trainTicketService.sortedByDateAndTime(sortDirection,from,to)
                .stream()
                .map(trainTicketMapper::entityToTicketSorted)
                .collect(Collectors.toList());
        String expectedResponse = asJsonString(sortedTickets);

        mockMvc.perform(get(BASIC_PATH + "/{from}/{to}",id,from,to)
                .param("direction", sortDirection)
        )
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(expectedResponse,
                        result.getResponse().getContentAsString()));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testValidGetOneTrainTicket() throws Exception {
        Long userId = 2L;
        Long trainId = 12L;
        String expectedTrainJson = asJsonString(trainTicketMapper
                .entityToTrainTicketResponse(trainTicketService
                        .getTicketById(trainId)));
        mockMvc.perform(get(BASIC_PATH + "/{id}", userId, trainId))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(expectedTrainJson,
                        result.getResponse().getContentAsString()));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testForbiddenGetOneTrainTicket() throws Exception {
        Long userId = 1L;
        Long trainId = 12L;
        mockMvc.perform(get(BASIC_PATH + "/{id}",
                        userId, trainId))
                .andExpect(status().isForbidden())
                .andExpect(forbiddenResult());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testNotFoundGetOneTrainTicket() throws Exception {
        Long userId = 2L;
        Long trainId = 0L;
        mockMvc.perform(get(BASIC_PATH + "/{id}",
                        userId, trainId))
                .andExpect(status().isNotFound())
                .andExpect(notFoundResult());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testValidTrainTicketBuy() throws Exception {
        Long adminId = 1L;
        Long ticketToButId = 22L;
        mockMvc.perform(post(BASIC_PATH + "/buy/{id}",
                        adminId, ticketToButId))
                .andExpect(status().isCreated());

        assertNotNull(trainTicketService.getTicketById(ticketToButId).getOwner());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testTicketIsAlreadyBought() throws Exception {
        Long adminId = 1L;
        Long bookedTicketId = 10L;
        trainTicketService.buyTicket(2L, bookedTicketId);

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
        Long ticketId = 24L;

        mockMvc.perform(post(BASIC_PATH + "/buy/{id}",
                        adminId, ticketId))
                .andExpect(status().isForbidden())
                .andExpect(forbiddenResult());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testValidTrainTicketReturn() throws Exception {
        Long adminId = 1L;
        Long ticketToReturnId = 14L;
        trainTicketService.buyTicket(adminId, ticketToReturnId);
        assertNotNull(trainTicketService.getTicketById(ticketToReturnId).getOwner());

        mockMvc.perform(delete(BASIC_PATH + "/return/{id}",
                        adminId, ticketToReturnId))
                .andExpect(status().isOk());

        assertNull(trainTicketService.getTicketById(ticketToReturnId).getOwner());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testTicketIsNotBought_TicketReturn() throws Exception {
        Long adminId = 1L;
        Long bookedTicketId = 10L;

        mockMvc.perform(delete(BASIC_PATH + "/return/{id}",
                        adminId, bookedTicketId))
                .andExpect(status().isForbidden())
                .andExpect(forbiddenResult());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testNotFoundReturnTicket() throws Exception {
        Long userId = 2L;
        Long invalidTrainId = 0L;
        mockMvc.perform(delete(BASIC_PATH + "/return/{id}",
                        userId, invalidTrainId))
                .andExpect(status().isForbidden())
                .andExpect(forbiddenResult());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testUsersNotSameReturnTicket() throws Exception {
        Long adminId = 1L;
        Long ticketId = 24L;

        mockMvc.perform(delete(BASIC_PATH + "/return/{id}", adminId, ticketId))
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
