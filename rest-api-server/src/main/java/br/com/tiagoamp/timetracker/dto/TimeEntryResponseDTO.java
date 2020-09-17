package br.com.tiagoamp.timetracker.dto;

import br.com.tiagoamp.timetracker.controller.TimeEntryController;
import br.com.tiagoamp.timetracker.controller.UserController;
import br.com.tiagoamp.timetracker.model.Category;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.RepresentationModel;

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


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        if (id == null) return;
        this.id = id;
        this.add(linkTo(methodOn(TimeEntryController.class).getTimeEntryById(id)).withSelfRel());
        this.add(linkTo(methodOn(TimeEntryController.class).getTimeEntriesByUsers(userId)).withRel("user-time-entries"));
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
        this.userId = userId;
    }

}
