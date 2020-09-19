package br.com.tiagoamp.timetracker.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@JsonPropertyOrder({ "categoryId", "startTime", "endTime", "durationInMinutes", "annotations" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeEntryRequestDTO {

    @NotNull(message = "{required.field}")
    private Long categoryId;

    @NotNull(message = "{required.field}")  @PastOrPresent
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    @Size(min=1, max=255)
    private String annotations;


    public TimeEntryRequestDTO() { }

    public TimeEntryRequestDTO(Long categoryId, LocalDateTime startTime, LocalDateTime endTime, String annotations) {
        this.categoryId = categoryId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.annotations = annotations;
    }


    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    public String getAnnotations() {
        return annotations;
    }
    public void setAnnotations(String annotations) {
        this.annotations = annotations;
    }
    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

}
