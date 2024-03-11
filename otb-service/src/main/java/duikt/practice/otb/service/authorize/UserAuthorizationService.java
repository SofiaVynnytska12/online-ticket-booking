package duikt.practice.otb.service.authorize;

import duikt.practice.otb.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserAuthorizationService {

    private final UserService userService;

    public boolean isUserSame(Long id, String name) {
        return getUserIdByName(name).equals(id);
    }

    private Long getUserIdByName(String name) {
        return userService.getUserByName(name).getId();
    }
}
