package duikt.practice.otb.resources.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import duikt.practice.otb.dto.UserRegisterRequest;
import duikt.practice.otb.dto.UserResponse;
import duikt.practice.otb.mapper.UserMapper;
import duikt.practice.otb.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.http.MediaType.*;
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
    public void testValidRegisterUser() throws Exception {
        UserRegisterRequest registerRequest = createUserRegisterRequest("user name",
                "user@mail.co", "123456789");
        String jsonStringForRegisterRequest = asJsonString(registerRequest);

        postRegistrationPerforming(jsonStringForRegisterRequest)
                .andExpect(status().isOk())
                .andExpect(result ->
                        assertNotNull(result.getResponse().getContentAsString())
                );
    }

    @Test
    public void testInvalidArgumentRegisterUser() throws Exception {
        UserRegisterRequest registerRequest = createUserRegisterRequest("invalid user",
                "email invalid", "");
        String jsonStringForRegisterRequest = asJsonString(registerRequest);

        postRegistrationPerforming(jsonStringForRegisterRequest)
                .andExpect(status().isBadRequest())
                .andExpectAll(
                        result -> assertTrue(result.getResponse().getContentAsString()
                                .contains("\"status\":\"BAD_REQUEST\"")),

                        result -> assertTrue(result.getResponse().getContentAsString()
                                .contains("Must be a valid e-mail address")),

                        result -> assertTrue(result.getResponse().getContentAsString()
                                .contains("Fill in your password please!"))
                );
    }

    private UserRegisterRequest createUserRegisterRequest(String username, String email, String password) {
        return UserRegisterRequest.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
    }

    @Test
    public void testNullRegisterUser() throws Exception {
        postRegistrationPerforming("")
                .andExpect(status().isInternalServerError())
                .andExpect(result ->
                        assertTrue(result.getResponse().getContentAsString()
                                .contains("\"status\":\"INTERNAL_SERVER_ERROR\","
                                        + "\"message\":\"Required request body is missing"))
                );
    }

    private ResultActions postRegistrationPerforming(String jsonStringForRegisterRequest) throws Exception {
        return mockMvc.perform(post(BASIC_PATH + "/registration")
                .contentType(APPLICATION_JSON)
                .content(jsonStringForRegisterRequest)
        );
    }

    @Test
    @WithMockUser(username = "admin", roles = "USER")
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
    public void testValidGetUserByName() throws Exception {
        String name = "admin";
        String expectedResponse = asJsonString(userMapper.getUserResponseFromEntity(userService.getUserByName(name)));
        mockMvc.perform(get(BASIC_PATH + "/username/{name}", name))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(expectedResponse,
                        result.getResponse().getContentAsString()));
    }

    @Test
    public void testValidGetUserById() throws Exception {
        Long id = 1L;
        String expectedResponse = asJsonString(userMapper.getUserResponseFromEntity(userService.getUserById(id)));
        mockMvc.perform(get(BASIC_PATH + "/id/{id}", id))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(expectedResponse,
                        result.getResponse().getContentAsString()));
    }

    @Test
    public void testInvalidGetUserById() throws Exception {
        Long id = -1L;
        String expectedResponse = "\"status\":\"NOT_FOUND\",\"message\":\"User not found!\"";
        mockMvc.perform(get(BASIC_PATH + "/id/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(
                        result.getResponse().getContentAsString().contains(expectedResponse)));
    }

    @Test
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
