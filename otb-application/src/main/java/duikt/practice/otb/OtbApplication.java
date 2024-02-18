package duikt.practice.otb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class OtbApplication {

    public static void main(String[] args) {
        SpringApplication.run(OtbApplication.class, args);
    }

}
