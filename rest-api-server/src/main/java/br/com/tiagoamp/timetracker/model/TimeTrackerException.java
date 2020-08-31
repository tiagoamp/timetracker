package br.com.tiagoamp.timetracker.model;

/**
 * Business checked exception
 */
public class TimeTrackerException extends Exception {

    private String message;
    public TimeTrackerException(String businessMessage) {
        this.message = businessMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
