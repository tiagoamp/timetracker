package br.com.tiagoamp.timetracker.model;

import java.time.LocalDateTime;

public class TimeEntry {

    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String annotations;

    private Category category;


    public TimeEntry() { }

    public TimeEntry(Long id, LocalDateTime startTime, LocalDateTime endTime, String annotations, Category category) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.annotations = annotations;
        this.category = category;
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
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    public Category getCategory() { return category; }
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
