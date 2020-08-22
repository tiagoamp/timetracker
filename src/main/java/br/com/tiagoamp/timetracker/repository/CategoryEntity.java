package br.com.tiagoamp.timetracker.repository;

import br.com.tiagoamp.timetracker.model.Category;
import br.com.tiagoamp.timetracker.model.User;

import javax.persistence.*;

@Entity
@Table(name = "CATEGORIES")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String description;

    @OneToOne
    private UserEntity user;

    public CategoryEntity() { }

    public CategoryEntity(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }


    public static CategoryEntity from(Category category) {
        return new CategoryEntity(category.getId(), category.getName(), category.getDescription());
    }

    public Category toModel() {
        return new Category(id, name, description);
    }

    public void updateInfoFrom(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
    }


    public Integer getId() { return id; }
    public void setId(Integer id) {
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
