package duikt.practice.otb.controller;

import duikt.practice.otb.dto.ErrorResponse;
import duikt.practice.otb.dto.UserRegisterRequest;
import duikt.practice.otb.dto.UserResponse;
import duikt.practice.otb.entity.User;
import duikt.practice.otb.mapper.UserMapper;
import duikt.practice.otb.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

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

    @GetMapping("/id/{id}")
    @Operation(summary = "Get user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
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
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse userResponse = userMapper.getUserResponseFromEntity(userService.getUserById(id));
        log.info("GET_USER-BY-ID; ID === {}", id);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/username/{name}")
    @Operation(summary = "Get user by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
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
    public ResponseEntity<UserResponse> getUserByName(@PathVariable String name) {
        UserResponse userResponse = userMapper.getUserResponseFromEntity(userService.getUserByName(name));
        log.info("GET_USER-BY-NAME; NAME === {}", name);
        return ResponseEntity.ok(userResponse);
    }
}
