package duikt.practice.otb.controller;

import duikt.practice.otb.dto.ErrorResponse;
import duikt.practice.otb.dto.LoginRequest;
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
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    @Operation(summary = "Login form")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login was successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "BadRequest",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "InternalServerError",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
    })
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        processingUserAuthentication(username, loginRequest.getPassword());
        log.info("User authorize with username - {} == {}", username, LocalDateTime.now());

        return ResponseEntity.ok(userMapper.getUserResponseFromEntity(userService.getUserByName(username)));
    }

    private void processingUserAuthentication(String username, String password) {
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(username, password);
        Authentication authenticationResponse =
                this.authenticationManager.authenticate(authenticationRequest);

        SecurityContextHolder.getContext().setAuthentication(authenticationResponse);
    }

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

}
