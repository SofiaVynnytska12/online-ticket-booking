package duikt.practice.otb.service;

import duikt.practice.otb.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User registerUser(User user);
}