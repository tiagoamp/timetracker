package br.com.tiagoamp.timetracker.repository;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TIME_ENTRY")
public class TimeEntryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String annotations;

    @OneToOne
    private CategoryEntity category;


    public TimeEntryEntity() { }

    public TimeEntryEntity(Long id, LocalDateTime startTime, LocalDateTime endTime, String annotations, CategoryEntity categoryEntity) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.annotations = annotations;
        this.category = categoryEntity;
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
    public CategoryEntity getCategory() {
        return category;
    }
    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.category = categoryEntity;
    }
    public String getAnnotations() {
        return annotations;
    }
    public void setAnnotations(String annotations) {
        this.annotations = annotations;
    }

}
