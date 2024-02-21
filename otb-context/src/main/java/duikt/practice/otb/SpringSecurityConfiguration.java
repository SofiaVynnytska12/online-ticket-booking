package duikt.practice.otb;
import duikt.practice.otb.mapper.UserMapper;
import duikt.practice.otb.mapper.impl.UserMapperImpl;
import duikt.practice.otb.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;


import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "duikt.practice.otb")
public class SpringSecurityConfiguration{
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationManagerBuilder builder,
                                                           UserDetailsService userDetailsService,
                                                           PasswordEncoder passwordEncoder) throws Exception {
            builder
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(passwordEncoder);
            return builder.build();
        }


        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public UserMapper userMapper(){
            return new UserMapperImpl();
        }


        @Bean
        public UserDetailsService userDetailsService(UserRepository userRepository, UserMapper userMapper) {
        return new UserSecurityService(userRepository, userMapper);
        }

        @Bean
        public WebSecurityCustomizer ignoreEndpointCustomizer () {
            return (web) -> web.ignoring().antMatchers("/**");
        }

        @Bean
        public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
            http
                    .authorizeRequests(authorizeRequests ->
                            authorizeRequests
                                    .antMatchers("/admin/**").hasRole("ADMIN") // Example: restrict /admin/** to users with role ADMIN
                                    .anyRequest().authenticated()
                        )
                    .httpBasic(withDefaults());

            return http.build();
            }
    }

