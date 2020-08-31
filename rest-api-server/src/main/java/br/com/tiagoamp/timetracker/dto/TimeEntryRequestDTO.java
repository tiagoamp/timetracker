package br.com.tiagoamp.timetracker.dto;

import br.com.tiagoamp.timetracker.model.Category;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@JsonPropertyOrder({ "userId", "category", "startTime", "endTime", "durationInMinutes", "annotations" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeEntryRequestDTO {

    @NotBlank(message = "{required.field}")
    private String userId;

    @NotNull(message = "{required.field}")
    private Category category;

    @NotNull(message = "{required.field}")  @PastOrPresent
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Max(255)
    private String annotations;


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
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public String getAnnotations() {
        return annotations;
    }
    public void setAnnotations(String annotations) {
        this.annotations = annotations;
    }

}
