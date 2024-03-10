package duikt.practice.otb.resources.controller;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import duikt.practice.otb.dto.TicketSorted;
import duikt.practice.otb.entity.addition.City;
import duikt.practice.otb.mapper.TrainTicketMapper;
import duikt.practice.otb.mapper.UserMapper;
import duikt.practice.otb.service.TrainTicketService;
import duikt.practice.otb.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = MOCK)
public class TrainTicketControllerTests {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy,M,d");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H,m");
    private static final String BASIC_PATH = "/user/{user_id}/train_ticket";

    private final MockMvc mockMvc;
    private final TrainTicketService trainTicketService;
    private final TrainTicketMapper trainTicketMapper;
    @Autowired
    public TrainTicketControllerTests(MockMvc mockMvc, TrainTicketService trainTicketService, TrainTicketMapper trainTicketMapper) {
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
    public void testInvalidGetSortedTickets(){

    }


    private static <T> String asJsonString(final T obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
