package duikt.practice.otb.controller;

import duikt.practice.otb.dto.TrainTicketResponse;
import duikt.practice.otb.mapper.TrainTicketMapper;
import duikt.practice.otb.service.TrainTicketService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/user/{user_id}/train_ticket")
public class TrainTicketController {

    private final TrainTicketMapper trainTicketMapper;
    private final TrainTicketService trainTicketService;

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

}
