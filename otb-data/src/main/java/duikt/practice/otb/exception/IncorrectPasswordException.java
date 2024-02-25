package duikt.practice.otb.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class IncorrectPasswordException extends BadCredentialsException {

    public IncorrectPasswordException(String msg) {
        super(msg);
    }

}
