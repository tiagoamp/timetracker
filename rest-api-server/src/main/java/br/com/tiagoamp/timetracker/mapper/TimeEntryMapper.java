package br.com.tiagoamp.timetracker.mapper;

import br.com.tiagoamp.timetracker.dto.CategoryRequestDTO;
import br.com.tiagoamp.timetracker.dto.CategoryResponseDTO;
import br.com.tiagoamp.timetracker.dto.TimeEntryRequestDTO;
import br.com.tiagoamp.timetracker.dto.TimeEntryResponseDTO;
import br.com.tiagoamp.timetracker.model.Category;
import br.com.tiagoamp.timetracker.model.TimeEntry;
import br.com.tiagoamp.timetracker.repository.CategoryEntity;
import br.com.tiagoamp.timetracker.repository.TimeEntryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface TimeEntryMapper {

    @Mapping(source="userId", target="user.id")
    @Mapping(source="categoryId", target="category.id")
    TimeEntry toModel(TimeEntryRequestDTO reqDTO);

    @Mapping(source="userEntity.id", target="user.id")
    @Mapping(source="categoryEntity.id", target="category.id")
    TimeEntry toModel(TimeEntryEntity entity);

    @Mapping(source="user.id", target="userEntity.id")
    @Mapping(source="category.id", target="categoryEntity.id")
    TimeEntryEntity toEntity(TimeEntry timeEntry);

    @Mapping(source="user.id", target="userId")
    @Mapping(source="category.id", target="categoryId")
    @Mapping(source="category.name", target="categoryName")
    TimeEntryResponseDTO toResponseDTO(TimeEntry timeEntry);

}
