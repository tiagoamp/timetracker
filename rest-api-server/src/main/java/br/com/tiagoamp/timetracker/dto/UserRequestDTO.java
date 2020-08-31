package br.com.tiagoamp.timetracker.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@JsonPropertyOrder({ "email", "name", "password" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRequestDTO {

    @NotBlank(message = "{required.field}")
    @Email(message = "{invalid.field}")
    private String email;

    @NotBlank(message = "{required.field}")  @Size(min=2, max=40)
    private String name;

    @NotBlank(message = "{required.field}")  @Size(min=4, max=40)
    private String password;


    public UserRequestDTO() { }

    public UserRequestDTO(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
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

}
