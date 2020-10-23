package br.com.tiagoamp.timetracker.error;

public class AuthorizationException extends RuntimeException {

    public AuthorizationException() { }

    public AuthorizationException(String message) {
        super(message);
    }

}
