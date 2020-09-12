package br.com.tiagoamp.timetracker.dto;

import br.com.tiagoamp.timetracker.controller.UserController;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@JsonPropertyOrder({ "id", "email", "name" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDTO extends RepresentationModel<UserResponseDTO> {

    private Long id;

    private String email;

    private String name;


    public UserResponseDTO() { }

    public UserResponseDTO(Long id, String email, String name) {
        this.setId(id);  // using setter tod set hateoas info
        this.email = email;
        this.name = name;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        if (id == null) return;
        this.id = id;
        this.add(linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel());
        this.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
        this.add(linkTo(methodOn(UserController.class).getCategoriesByUser(id)).withRel("user-categories"));
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
