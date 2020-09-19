package br.com.tiagoamp.timetracker.dto;

import br.com.tiagoamp.timetracker.controller.CategoryController;
import br.com.tiagoamp.timetracker.controller.UserController;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@JsonPropertyOrder({ "id", "userId", "name", "description" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponseDTO extends RepresentationModel<CategoryResponseDTO> {

    private Long id;

    private Long userId;

    private String name;

    private String description;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        if (userId == null) return;
        this.userId = userId;
        this.add(linkTo(methodOn(CategoryController.class).getCategoriesById(userId, id)).withSelfRel());
    }
}
