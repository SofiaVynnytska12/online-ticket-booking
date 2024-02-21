package duikt.practice.otb.resources.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import duikt.practice.otb.dto.UserRegisterRequest;
import duikt.practice.otb.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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


    @Autowired
    public UserControllerTests(MockMvc mockMvc, UserService userService) {
        this.mockMvc = mockMvc;
        this.userService = userService;
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetUserRole_Admin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASIC_PATH + "/whoamI")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Role: ROLE_ADMIN"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetUserRole_User() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASIC_PATH + "/whoamI")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Role: ROLE_USER"));
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
    public void testValidGetAll() throws Exception {
        String sortDirection = "-";
        String[] properties = new String[]{"email", "id"};
        String expectedResponse = asJsonString(userService.getAll(sortDirection, properties));

        mockMvc.perform(get(BASIC_PATH + "/getAll")
                .param("direction", sortDirection)
                .param("properties", properties)
        )
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(expectedResponse,
                        result.getResponse().getContentAsString()));
    }


    private static <T> String asJsonString(final T obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
