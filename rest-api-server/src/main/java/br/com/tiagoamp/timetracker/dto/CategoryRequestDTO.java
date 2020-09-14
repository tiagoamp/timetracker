package br.com.tiagoamp.timetracker.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.*;

@JsonPropertyOrder({ "name", "description" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryRequestDTO {

    @NotBlank(message = "{required.field}")  @Size(min=2, max=40)
    private String name;

    @Size(min=1, max=255)
    private String description;


    public CategoryRequestDTO() { }

    public CategoryRequestDTO(String name, String description) {
        this.name = name;
        this.description = description;
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

}
