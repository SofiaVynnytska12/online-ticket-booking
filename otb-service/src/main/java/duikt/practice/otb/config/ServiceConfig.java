package duikt.practice.otb.config;

import duikt.practice.otb.mapper.BusTicketsMapper;
import duikt.practice.otb.mapper.TrainTicketMapper;
import duikt.practice.otb.mapper.impl.BusTicketsMapperImpl;
import duikt.practice.otb.mapper.impl.TrainTicketMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ServiceConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TrainTicketMapper trainTicketMapper() {
        return new TrainTicketMapperImpl();
    }

    @Bean
    public BusTicketsMapper busTicketsMapper(){return  new BusTicketsMapperImpl();
    }

}
