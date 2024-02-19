package duikt.practice.otb.controller;

import duikt.practice.otb.dto.ErrorResponse;
import duikt.practice.otb.dto.UserRegisterRequest;
import duikt.practice.otb.entity.User;
import duikt.practice.otb.mapper.UserMapper;
import duikt.practice.otb.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperties;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/registration")
    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful registration",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserRegisterRequest.class))}),
            @ApiResponse(responseCode = "400", description = "BadRequestError",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "ForbiddenError",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "InternalServerError",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public ResponseEntity<String> registerUser(@Valid @RequestBody
                                               UserRegisterRequest userRegisterRequest) {
        User wantToRegister = userMapper.getEntityFromUserRegisterRequest(userRegisterRequest);
        userService.registerUser(wantToRegister);
        log.info("REGISTRATION-USER; email === {}, time == {}",
                wantToRegister.getEmail(), LocalDateTime.now());

        return ResponseEntity.ok("Thank you❤️!\n"
                + "You successfully register on Online Ticket Booking platform!");
    }
}
