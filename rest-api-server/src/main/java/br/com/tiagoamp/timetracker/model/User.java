package br.com.tiagoamp.timetracker.model;

import java.util.List;
import java.util.Objects;

public class User {

    private Long id;

    private String email;

    private String name;

    private String password;

    private List<Category> categories;

    private List<TimeEntry> timeEntries;


    public User() { }

    public User(Long id, String email, String name, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public User(String email, String name, String password) {
        this(null, email, name, password);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<Category> getCategories() {
        return categories;
    }
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    public List<TimeEntry> getTimeEntries() {
        return timeEntries;
    }
    public void setTimeEntries(List<TimeEntry> timeEntries) {
        this.timeEntries = timeEntries;
    }
}
