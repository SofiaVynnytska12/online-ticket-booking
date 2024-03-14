package duikt.practice.otb.controller;

import duikt.practice.otb.dto.BusTicketResponse;
import duikt.practice.otb.dto.ErrorResponse;
import duikt.practice.otb.mapper.BusTicketMapper;
import duikt.practice.otb.service.BusTicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import duikt.practice.otb.dto.TrainTicketResponse;
import duikt.practice.otb.entity.BusTicket;
import duikt.practice.otb.mapper.BusTicketsMapper;
import duikt.practice.otb.service.BusTicketService;
import io.swagger.v3.oas.annotations.Operation;
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

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/user/{user_id}/bus_ticket")
public class BusTicketController {

    private final BusTicketService busTicketService;
    private final BusTicketMapper busTicketMapper;

    @Operation(summary = "Get sorted bus tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Fetches all bus tickets from the system",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema =
                            @Schema(implementation = BusTicketResponse.class)))),
            @ApiResponse(responseCode = "403", description = "ForbiddenError",
    private final BusTicketsMapper busTicketsMapper;

    @Operation(summary = "buy bus ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " ticket has been bought, have a nice ride",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BusTicketResponse.class))),
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
    @PreAuthorize("@busTicketAuthorizationService" +
            ".isUserSameAndTicketAvailable(#userId, authentication.name, #ticketId)")
    public ResponseEntity<BusTicketResponse> buyBusTicket(
            @PathVariable("user_id") Long userId,
            @PathVariable("id") Long ticketId, Authentication authentication) {
            BusTicketResponse response = busTicketsMapper.entityToBusTicketResponse(busTicketService.buyTicket(userId,ticketId));
        log.info("POST-BUS_TICKET-BUY === user == {}, bus name == {}",
                authentication.getName(), response.getName());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @Operation(summary = "get one bus  ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "here is your ticket, enjoy!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BusTicketResponse.class))),
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
    @GetMapping
    @PreAuthorize("@userAuthorizationService.isUserSame(#userId, authentication.name)")
    public ResponseEntity<List<BusTicketResponse>> getAll(
            @PathVariable("user_id") Long userId,
            @RequestParam(value = "direction", defaultValue = "+") String direction,
            @RequestParam(value = "city_from") String cityFrom,
            @RequestParam(value = "city_to") String cityTo, Authentication authentication) {
        var busTicketsResponse = busTicketMapper.getResponseListFromEntityList(
                busTicketService.getSortedTickets(cityFrom, cityTo, direction));
        log.info("GET-ALL-SORTED_BUS_TICKETS === username - {}", authentication.getName());

        return ResponseEntity.ok(busTicketsResponse);
    }

    @Operation(summary = "Return bus ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return bus ticket by user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BusTicketResponse.class))),
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
    @PreAuthorize("@busTicketAuthorizationService" +
            ".isUserSameAndTicketOwner(#userId, authentication.name, #id)")
    public ResponseEntity<BusTicketResponse> returnBusTicket(
            @PathVariable("user_id") Long userId, @PathVariable Long id,
            Authentication authentication) {
        var ticketResponse = busTicketMapper.getResponseFromEntity(busTicketService
                .returnBusTicket(userId, id));
        log.info("RETURN-BUS_TICKET === username - {}, bus name == {}",
                authentication.getName(), ticketResponse.getName());

        return ResponseEntity.ok(ticketResponse);
    }


    @GetMapping("/{id}")
    @PreAuthorize("@userAuthorizationService.isUserSame(#userId, authentication.name)")
    public ResponseEntity<BusTicketResponse> getOneBusTicket(
            @PathVariable("user_id") Long userId, @PathVariable Long id,
            Authentication authentication) {
        BusTicketResponse response = busTicketsMapper.entityToBusTicketResponse(busTicketService.getTicketById(id));
        log.info("GET-BUS_TICKET === user == {}, bus name == {}",
                authentication.getName(), response.getName());

        return ResponseEntity.ok(response);
    }
}
