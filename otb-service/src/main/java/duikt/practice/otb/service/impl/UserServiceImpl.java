package duikt.practice.otb.service.impl;

import duikt.practice.otb.entity.User;
import duikt.practice.otb.entity.addition.Role;
import duikt.practice.otb.exception.IncorrectPasswordException;
import duikt.practice.otb.exception.InvalidDataException;
import duikt.practice.otb.repository.UserRepository;
import duikt.practice.otb.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.springframework.data.domain.Sort.*;

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

    @Override
    public List<User> getAll(String sortDirection, String[] properties) {
        return userRepository.findAll(
                by(getDirectionForSort(sortDirection), properties));
    }

    private Direction getDirectionForSort(String sortDirection) {
        if (sortDirection.equals("+")){
            return Direction.ASC;
        }

        return Direction.DESC;
    }

    @Override
    public User getUserByName(String name) {
        if (name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("incorrect name");
        }
        return userRepository.findByUsername(name)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }



    @Override
    public void ifPasswordsNotMatchesThrowException(String rawPass, String encodedPass) {
        if (!passwordEncoder.matches(rawPass, encodedPass)) {
            throw new IncorrectPasswordException("Check if you write correct password!");
        }
    }
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }

}
