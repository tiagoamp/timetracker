package br.com.tiagoamp.timetracker.dto;

import br.com.tiagoamp.timetracker.controller.TimeEntryController;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.RepresentationModel;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@JsonPropertyOrder({ "id", "userId", "categoryId", "categoryName", "startTime", "endTime", "durationInMinutes", "annotations" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeEntryResponseDTO extends RepresentationModel<TimeEntryResponseDTO> {

    private Long id;

    private Long userId;

    private Long categoryId;

    private String categoryName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer durationInMinutes;

    private String annotations;


    private void calculateDuration() {
        if (startTime == null || endTime == null) return;
        this.durationInMinutes = (int) Duration.between(startTime, endTime).toMinutes();
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        calculateDuration();
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        calculateDuration();
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public String getAnnotations() {
        return annotations;
    }
    public void setAnnotations(String annotations) {
        this.annotations = annotations;
    }
    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }
    public void setDurationInMinutes(Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }
    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        if (userId == null) return;
        this.userId = userId;
        this.add(linkTo(methodOn(TimeEntryController.class).getTimeEntryById(userId, id)).withSelfRel());
    }

}
