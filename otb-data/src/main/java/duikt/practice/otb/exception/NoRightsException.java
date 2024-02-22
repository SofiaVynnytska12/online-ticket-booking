package duikt.practice.otb.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class NoRightsException extends BadCredentialsException {

    public NoRightsException(String msg) {
        super(msg);
    }

}
