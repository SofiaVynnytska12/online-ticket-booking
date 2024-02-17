package duikt.practice.otb.service.config;

import duikt.practice.otb.config.ServiceConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class ServiceConfigTests {

    private final ServiceConfig serviceConfig;
    private final PasswordEncoder expectedPasswordEncoder;

    @Autowired
    public ServiceConfigTests(ServiceConfig serviceConfig, PasswordEncoder passwordEncoder) {
        this.serviceConfig = serviceConfig;
        this.expectedPasswordEncoder = passwordEncoder;
    }

    @Test
    public void testValidPasswordEncoderBean() {
        PasswordEncoder actualPasswordEncoder = serviceConfig.passwordEncoder();

        Assertions.assertEquals(expectedPasswordEncoder, actualPasswordEncoder);
    }
}
