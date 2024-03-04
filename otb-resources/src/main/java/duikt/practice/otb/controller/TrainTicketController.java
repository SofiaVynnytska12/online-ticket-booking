package duikt.practice.otb.controller;

import duikt.practice.otb.dto.TrainTicketRequest;
import duikt.practice.otb.mapper.TrainTicketMapper;
import duikt.practice.otb.service.TrainTicketService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/user/{user_id}/train_ticket")
public class TrainTicketController {

    private final TrainTicketMapper trainTicketMapper;
    private final TrainTicketService trainTicketService;

    @GetMapping("/{id}")
    @PreAuthorize("@trainTicketAuthorizationService.isUserSame(#userId, authentication.name)")
    public ResponseEntity<TrainTicketRequest> getOneTrainTicket(
            @PathVariable("user_id") Long userId, @PathVariable Long id,
            Authentication authentication) {
        var trainTicketRequest = trainTicketMapper
                .entityToTrainTicketRequest(trainTicketService.getTicketById(id));
        log.info("GET-TRAIN_TICKET === user == {}, train name == {}", authentication.getName(),
                trainTicketRequest.getName());

        return ResponseEntity.ok(trainTicketRequest);
    }

}
