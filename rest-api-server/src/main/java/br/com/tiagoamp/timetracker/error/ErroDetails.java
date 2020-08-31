package br.com.tiagoamp.timetracker.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErroDetails {

    private String title;

    private String message;

    private Object details;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;


    public ErroDetails(String title, String message, Object details) {
        this.title = title;
        this.message = message;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }

    public ErroDetails(String title, String message) {
        this(title, message, null);
    }


    public String getTitle() {
        return title;
    }
    public String getMessage() {
        return message;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public Object getDetails() {
        return details;
    }
    public void setDetails(Object details) {
        this.details = details;
    }
}
