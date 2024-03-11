package duikt.practice.otb.service.servicetests.auth;

import duikt.practice.otb.service.authorize.UserAuthorizationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class UserAuthorizationServiceTests {

    private final UserAuthorizationService userAuthorizationService;

    @Autowired
    public UserAuthorizationServiceTests(UserAuthorizationService userAuthorizationService) {
        this.userAuthorizationService = userAuthorizationService;
    }


    @Test
    public void testTrueIsUserSame() {
        Long userId = 2L;
        String username = "user";

        assertTrue(userAuthorizationService.isUserSame(userId, username));
    }

    @Test
    public void testFalseIsUserSame() {
        Long adminId = 1L;
        String username = "user";

        assertFalse(userAuthorizationService.isUserSame(adminId, username));
    }
}
