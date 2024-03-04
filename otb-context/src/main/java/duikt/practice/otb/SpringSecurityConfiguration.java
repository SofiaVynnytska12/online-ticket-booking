package duikt.practice.otb;

import duikt.practice.otb.exception.NoRightsException;
import duikt.practice.otb.service.UserSecurityService;
import duikt.practice.otb.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Slf4j
@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return new UserSecurityService(userService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        authorizeRequest(httpSecurity);
        return httpSecurity.build();
    }

    private void authorizeRequest(HttpSecurity httpSecurity) {
        try {
            httpSecurity.authorizeRequests(authorizeRequests ->
                            authorizeRequests
                                    .antMatchers("/user/getAll", "/user/id/",
                                            "/user/username/").hasRole("ADMIN")
                                    .antMatchers("/auth/**").permitAll()
                                    .anyRequest().authenticated()
                    )
                    .httpBasic(withDefaults())
                    .csrf().disable();
        } catch (Exception exception) {
            log.error("Authorization exception - {}", exception.getCause().getMessage());
            throw new NoRightsException("Sorry, but you have no rights, to go here!");
        }
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

}

