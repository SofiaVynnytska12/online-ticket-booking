package duikt.practice.otb.resources.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import duikt.practice.otb.dto.LoginRequest;
import duikt.practice.otb.dto.UserRegisterRequest;
import duikt.practice.otb.mapper.UserMapper;
import duikt.practice.otb.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = MOCK)
public class AuthControllerTests {

    private final String BASIC_PATH = "/auth";

    private final MockMvc mockMvc;
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public AuthControllerTests(MockMvc mockMvc, UserService userService, UserMapper userMapper) {
        this.mockMvc = mockMvc;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Test
    public void testValidLogin() throws Exception {
        String username = "admin";
        String password = "1111";
        String expectedResponse = asJsonString(userMapper
                .getUserResponseFromEntity(
                        userService.getUserByName(username)));

        mockMvc.perform(MockMvcRequestBuilders.post(BASIC_PATH + "/login")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(new LoginRequest(username, password)))
                )
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(expectedResponse,
                        result.getResponse().getContentAsString()));
    }

    @Test
    public void testInvalidUsernameLogin() throws Exception {
        String username = "not found user";
        String password = "invalid";

        mockMvc.perform(MockMvcRequestBuilders.post(BASIC_PATH + "/login")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(new LoginRequest(username, password)))
                )
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResponse()
                        .getContentAsString()
                        .contains("\"status\":\"NOT_FOUND\",\"message\":\"User not found!\"")));
    }

    @Test
    public void testInvalidPasswordLogin() throws Exception {
        String username = "admin";
        String password = "invalid";

        mockMvc.perform(MockMvcRequestBuilders.post(BASIC_PATH + "/login")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(new LoginRequest(username, password)))
                )
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResponse()
                        .getContentAsString()
                        .contains("\"status\":\"UNAUTHORIZED\",\"message\":\"Bad credentials\"")));
    }

    @Test
    public void testValidRegisterUser() throws Exception {
        UserRegisterRequest registerRequest = createUserRegisterRequest("user name",
                "test@mail.co", "123456789");
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

    private static <T> String asJsonString(final T obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
