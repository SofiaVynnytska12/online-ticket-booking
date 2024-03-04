package duikt.practice.otb.service.authorize;

import duikt.practice.otb.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrainTicketAuthorizationService {

    private final UserService userService;

    public boolean isUserSame(Long id, String name) {
        boolean is = getUserIdByName(name).equals(id);
        return is;
    }

    private Long getUserIdByName(String name) {
        return userService.getUserByName(name).getId();
    }
}
