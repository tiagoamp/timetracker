package br.com.tiagoamp.timetracker.mapper;

import br.com.tiagoamp.timetracker.dto.CategoryRequestDTO;
import br.com.tiagoamp.timetracker.dto.CategoryResponseDTO;
import br.com.tiagoamp.timetracker.dto.UserRequestDTO;
import br.com.tiagoamp.timetracker.dto.UserResponseDTO;
import br.com.tiagoamp.timetracker.model.Category;
import br.com.tiagoamp.timetracker.model.User;
import br.com.tiagoamp.timetracker.repository.CategoryEntity;
import br.com.tiagoamp.timetracker.repository.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface CategoryMapper {

    Category toModel(CategoryRequestDTO reqDTO);
    Category toModel(CategoryEntity entity);
    CategoryEntity toEntity(Category category);
    CategoryResponseDTO toResponseDTO(Category category);

}
