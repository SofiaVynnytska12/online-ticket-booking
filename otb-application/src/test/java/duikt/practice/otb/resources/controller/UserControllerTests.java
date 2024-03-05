package duikt.practice.otb.resources.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import duikt.practice.otb.dto.UserResponse;
import duikt.practice.otb.mapper.UserMapper;
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

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = MOCK)
public class UserControllerTests {

    private static final String BASIC_PATH = "/user";

    private final MockMvc mockMvc;
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserControllerTests(MockMvc mockMvc, UserService userService, UserMapper userMapper) {
        this.mockMvc = mockMvc;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testValidGetAll() throws Exception {
        String sortDirection = "-";
        String[] properties = new String[]{"email", "id"};
        List<UserResponse> userResponses = userService.getAll(sortDirection, properties)
                .stream()
                .map(userMapper::getUserResponseFromEntity)
                .collect(toList());
        String expectedResponse = asJsonString(userResponses);

        mockMvc.perform(get(BASIC_PATH + "/getAll")
                        .param("direction", sortDirection)
                        .param("properties", properties)
                )
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(expectedResponse,
                        result.getResponse().getContentAsString()));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testValidGetUserByName() throws Exception {
        String name = "admin";
        String expectedResponse = asJsonString(userMapper.getUserResponseFromEntity(userService.getUserByName(name)));
        mockMvc.perform(get(BASIC_PATH + "/username/{name}", name))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(expectedResponse,
                        result.getResponse().getContentAsString()));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testValidGetUserById() throws Exception {
        Long id = 1L;
        String expectedResponse = asJsonString(userMapper.getUserResponseFromEntity(userService.getUserById(id)));
        mockMvc.perform(get(BASIC_PATH + "/id/{id}", id))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(expectedResponse,
                        result.getResponse().getContentAsString()));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testInvalidGetUserById() throws Exception {
        Long id = -1L;
        String expectedResponse = "\"status\":\"NOT_FOUND\",\"message\":\"User not found!\"";
        mockMvc.perform(get(BASIC_PATH + "/id/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(
                        result.getResponse().getContentAsString().contains(expectedResponse)));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testInvalidGetUserByName() throws Exception {
        String name = "aboba";
        String expectedResponse = "\"status\":\"NOT_FOUND\",\"message\":\"User not found!\"";
        mockMvc.perform(get(BASIC_PATH + "/username/{name}", name))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(
                        result.getResponse().getContentAsString().contains(expectedResponse)));
    }

    private static <T> String asJsonString(final T obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
