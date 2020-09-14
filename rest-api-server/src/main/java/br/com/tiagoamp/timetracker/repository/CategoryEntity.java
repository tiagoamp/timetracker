package br.com.tiagoamp.timetracker.repository;

import javax.persistence.*;

@Entity
@Table(name = "CATEGORIES")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    @OneToOne
    private UserEntity user;

    public CategoryEntity() { }

    public CategoryEntity(Long id, String name, String description, UserEntity userEntity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.user = userEntity;
    }

    public CategoryEntity(Long id, String name, String description) {
        this(id, name, description, null);
    }


    public Long getId() { return id; }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public UserEntity getUser() {
        return user;
    }
    public void setUser(UserEntity user) {
        this.user = user;
    }

}
