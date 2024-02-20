package duikt.practice.otb.service.impl;

import duikt.practice.otb.entity.User;
import duikt.practice.otb.entity.addition.Role;
import duikt.practice.otb.exception.InvalidDataException;
import duikt.practice.otb.repository.UserRepository;
import duikt.practice.otb.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(User user) {
        userCheckingAndAdditionsForFields(user);
        User created = userRepository.save(user);
        log.info("User '{}' successfully saved to DB.", user.getUsername());
        return created;
    }

    private void userCheckingAndAdditionsForFields(User user) {
        checkUserForNull(user);
        encodeUserPassword(user);
        user.setUserRole(Role.USER);
    }

    private void checkUserForNull(User user) throws InvalidDataException {
        if (user == null) {
            log.error("'registerUser' - null user passed!");
            throw new InvalidDataException("Sorry, something went wrong!");
        }
    }

    private void encodeUserPassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
