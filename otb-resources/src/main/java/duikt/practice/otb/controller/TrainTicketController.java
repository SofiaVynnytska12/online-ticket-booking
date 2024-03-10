package duikt.practice.otb.controller;

import duikt.practice.otb.dto.ErrorResponse;
import duikt.practice.otb.dto.TicketSorted;
import duikt.practice.otb.dto.TrainTicketResponse;
import duikt.practice.otb.dto.UserResponse;
import duikt.practice.otb.entity.TrainTicket;
import duikt.practice.otb.entity.addition.City;
import duikt.practice.otb.mapper.TrainTicketMapper;
import duikt.practice.otb.repository.TrainTicketRepository;
import duikt.practice.otb.service.TrainTicketService;
import duikt.practice.otb.service.impl.TrainTicketServiceIml;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/user/{user_id}/train_ticket")
public class TrainTicketController {

    private final TrainTicketMapper trainTicketMapper;
    private final TrainTicketService trainTicketService;
    private final TrainTicketRepository trainTicketRepository;

    @GetMapping("/{id}")
    @PreAuthorize("@userAuthorizationService.isUserSame(#userId, authentication.name)")
    public ResponseEntity<TrainTicketResponse> getOneTrainTicket(
            @PathVariable("user_id") Long userId, @PathVariable Long id,
            Authentication authentication) {
        var trainTicketRequest = trainTicketMapper
                .entityToTrainTicketResponse(trainTicketService.getTicketById(id));
        log.info("GET-TRAIN_TICKET === user == {}, train name == {}",
                authentication.getName(), trainTicketRequest.getName());

        return ResponseEntity.ok(trainTicketRequest);
    }


    @PostMapping("/buy/{id}")
    @PreAuthorize("@trainTicketAuthorizationService" +
            ".isUserSameAndTicketAvailable(#userId, authentication.name, #id)")
    public ResponseEntity<TrainTicketResponse> trainTicketBuy(
            @PathVariable("user_id") Long userId, @PathVariable Long id,
            Authentication authentication) {
        var trainTicketRequest = trainTicketMapper
                .entityToTrainTicketResponse(trainTicketService.buyTicket(userId, id));
        log.info("POST-TRAIN_TICKET-BUY === user == {}, train name == {}",
                authentication.getName(), trainTicketRequest.getName());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(trainTicketRequest);
    }

    @DeleteMapping("/return/{id}")
    @PreAuthorize("@trainTicketAuthorizationService" +
            ".isUserSameAndTicketOwner(#userId, authentication.name, #id)")
    public ResponseEntity<TrainTicketResponse> trainTicketReturn(
            @PathVariable("user_id") Long userId, @PathVariable Long id,
            Authentication authentication) {
        var trainTicketRequest = trainTicketMapper
                .entityToTrainTicketResponse(trainTicketService.returnTicket(id));
        log.info("POST-TRAIN_TICKET-RETURN === user == {}, train name == {}",
                authentication.getName(), trainTicketRequest.getName());

        return ResponseEntity.ok(trainTicketRequest);
    }
    @Operation(summary = "Get sorted tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetches all users from the system",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TrainTicketResponse.class)))),
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
    public ResponseEntity<List<TicketSorted>> getSortedTickets(
            @RequestParam(value = "direction",defaultValue = "+") String direction,
            @PathVariable("fromCity") String fromCity,
            @PathVariable("toCity") String toCity,
            @PathVariable Long user_id) {
        List<TicketSorted> sortedTickets = trainTicketService.sortedByDateAndTime(direction,fromCity,toCity)
                .stream()
                .map(trainTicketMapper::entityToTicketSorted)
                .collect(Collectors.toList());
        return ResponseEntity.ok(sortedTickets);
    }

}
