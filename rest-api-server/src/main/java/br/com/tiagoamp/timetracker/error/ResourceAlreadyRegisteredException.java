package br.com.tiagoamp.timetracker.error;

public class ResourceAlreadyRegisteredException extends RuntimeException {

    public ResourceAlreadyRegisteredException(String message) {
        super(message);
    }

}
