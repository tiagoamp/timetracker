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

    @OneToOne(fetch = FetchType.EAGER)
    private CategoryEntity category;

    @OneToOne(fetch = FetchType.EAGER)
    private UserEntity userEntity;


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
    public CategoryEntity getCategory() { return category; }
    public void setCategory(CategoryEntity category) {
        this.category = category;
    }
    public String getAnnotations() {
        return annotations;
    }
    public void setAnnotations(String annotations) {
        this.annotations = annotations;
    }

}
