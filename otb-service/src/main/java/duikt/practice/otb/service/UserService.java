package duikt.practice.otb.service;

import duikt.practice.otb.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User registerUser(User user);

    List<User> getAll(String sortDirection, String[] properties);

    User getUserByName(String name);

    void ifPasswordsNotMatchesThrowException(String rawPass, String encodedPass);
}
