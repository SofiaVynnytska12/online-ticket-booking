    package duikt.practice.otb;

    import duikt.practice.otb.entity.User;
    import duikt.practice.otb.mapper.UserMapper;
    import duikt.practice.otb.repository.UserRepository;
    import lombok.AllArgsConstructor;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.stereotype.Service;

    @Service
    @AllArgsConstructor
    public class UserSecurityService implements UserDetailsService {

         private UserRepository userRepository;
         private UserMapper userMapper;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
            return userMapper.toUserDetails(user);
        }
    }
