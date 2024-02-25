package duikt.practice.otb.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class CustomLoginException extends BadCredentialsException {

    public CustomLoginException(String msg) {
        super(msg);
    }

}
