package br.com.tiagoamp.timetracker.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "email", "name" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDTO {

    private Long id;

    private String email;

    private String name;


    public UserResponseDTO() { }

    public UserResponseDTO(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
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

}
