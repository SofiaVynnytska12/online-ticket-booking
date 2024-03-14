package duikt.practice.otb.controller;

import duikt.practice.otb.dto.ErrorResponse;
import duikt.practice.otb.dto.TicketSorted;
import duikt.practice.otb.dto.TrainTicketResponse;
import duikt.practice.otb.mapper.TrainTicketMapper;
import duikt.practice.otb.service.TrainTicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/user/{user_id}/train_ticket")
public class TrainTicketController {

    private final TrainTicketMapper trainTicketMapper;
    private final TrainTicketService trainTicketService;

    @Operation(summary = "Get sorted tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetches all users from the system",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TicketSorted.class)))),
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
    @GetMapping("/{fromCity}/{toCity}")
    @PreAuthorize("@userAuthorizationService.isUserSame(#user_id, authentication.name)")
    public ResponseEntity<List<TicketSorted>> getSortedTickets(
            Authentication authentication,
            @RequestParam(value = "direction", defaultValue = "+") String direction,
            @PathVariable("fromCity") String fromCity,
            @PathVariable("toCity") String toCity,
            @PathVariable Long user_id) {
        List<TicketSorted> sortedTickets = trainTicketService
                .sortedByDateAndTime(direction, fromCity, toCity)
                .stream()
                .map(trainTicketMapper::entityToTicketSorted)
                .collect(Collectors.toList());
        log.info("GET-ALL-SORTED-TICKETS");
        authentication.getName();
        return ResponseEntity.ok(sortedTickets);
    }


    @Operation(summary = "Get one train ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetches train ticket from the system",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TrainTicketResponse.class))),
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
    @GetMapping("/{id}")
    @PreAuthorize("@userAuthorizationService.isUserSame(#userId, authentication.name)")
    public ResponseEntity<TrainTicketResponse> getOneTrainTicket(
            @PathVariable("user_id") Long userId, @PathVariable Long id,
            Authentication authentication) {
        var trainTicketResponse = trainTicketMapper
                .entityToTrainTicketResponse(trainTicketService.getTicketById(id));
        log.info("GET-TRAIN_TICKET === user == {}, train name == {}",
                authentication.getName(), trainTicketResponse.getName());

        return ResponseEntity.ok(trainTicketResponse);
    }

    @Operation(summary = "Buy train ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Buy train ticket by user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TrainTicketResponse.class))),
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
    @PostMapping("/buy/{id}")
    @PreAuthorize("@trainTicketAuthorizationService" +
            ".isUserSameAndTicketAvailable(#userId, authentication.name, #id)")
    public ResponseEntity<TrainTicketResponse> trainTicketBuy(
            @PathVariable("user_id") Long userId, @PathVariable Long id,
            Authentication authentication) {
        var trainTicketResponse = trainTicketMapper
                .entityToTrainTicketResponse(trainTicketService.buyTicket(userId, id));
        log.info("POST-TRAIN_TICKET-BUY === user == {}, train name == {}",
                authentication.getName(), trainTicketResponse.getName());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(trainTicketResponse);
    }

    @Operation(summary = "Return train ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return train ticket by user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TrainTicketResponse.class))),
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
    @DeleteMapping("/return/{id}")
    @PreAuthorize("@trainTicketAuthorizationService" +
            ".isUserSameAndTicketOwner(#userId, authentication.name, #id)")
    public ResponseEntity<TrainTicketResponse> trainTicketReturn(
            @PathVariable("user_id") Long userId, @PathVariable Long id,
            Authentication authentication) {
        var trainTicketResponse = trainTicketMapper
                .entityToTrainTicketResponse(trainTicketService.returnTicket(userId, id));
        log.info("DELETE-TRAIN_TICKET-RETURN === user == {}, train name == {}",
                authentication.getName(), trainTicketResponse.getName());

        return ResponseEntity.ok(trainTicketResponse);
    }
}
