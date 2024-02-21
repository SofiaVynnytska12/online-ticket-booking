package duikt.practice.otb.controller;

import duikt.practice.otb.dto.ErrorResponse;
import duikt.practice.otb.dto.UserRegisterRequest;
import duikt.practice.otb.dto.UserResponse;
import duikt.practice.otb.entity.User;
import duikt.practice.otb.mapper.UserMapper;
import duikt.practice.otb.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.*;

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
                            schema = @Schema(implementation = UserResponse.class))}),
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
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody
                                               UserRegisterRequest userRegisterRequest) {
        User wantToRegister = userMapper.getEntityFromUserRegisterRequest(userRegisterRequest);
        User registeredUser = userService.registerUser(wantToRegister);
        log.info("REGISTRATION-USER; email === {}, time == {}",
                wantToRegister.getEmail(), LocalDateTime.now());

        return ResponseEntity.ok(userMapper.getUserResponseFromEntity(registeredUser));
    }

    @GetMapping("/getAll")
    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetches all users from the system",
                    content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))),
            @ApiResponse(responseCode = "403", description = "ForbiddenError",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "NotFoundError",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "InternalServerError",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<List<UserResponse>> getAllUsers(
            @RequestParam(name = "direction", defaultValue = "+") String direction,
            @RequestParam(name = "properties", defaultValue = "id") String[] properties) {
        List<UserResponse> userResponses = userService.getAll(direction, properties)
                .stream()
                .map(userMapper::getUserResponseFromEntity)
                .collect(toList());
        log.info("GET_ALL-USERS");

        return ResponseEntity.ok(userResponses);
    }
    @GetMapping("/whoamI")
    @Operation(summary = "Get whoamI")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetches all users from the system",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))),
            @ApiResponse(responseCode = "403", description = "ForbiddenError",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "NotFoundError",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "InternalServerError",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
    })
    public String whoAmI() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return "Role: " + authentication.getAuthorities().iterator().next().getAuthority();
        }
    }
