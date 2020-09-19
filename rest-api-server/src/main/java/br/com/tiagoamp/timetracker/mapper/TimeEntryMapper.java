package br.com.tiagoamp.timetracker.mapper;

import br.com.tiagoamp.timetracker.dto.TimeEntryRequestDTO;
import br.com.tiagoamp.timetracker.dto.TimeEntryResponseDTO;
import br.com.tiagoamp.timetracker.model.TimeEntry;
import br.com.tiagoamp.timetracker.repository.TimeEntryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface TimeEntryMapper {

    @Mapping(source="categoryId", target="category.id")
    TimeEntry toModel(TimeEntryRequestDTO reqDTO);

    @Mapping(source="categoryEntity.id", target="category.id")
    TimeEntry toModel(TimeEntryEntity entity);

    @Mapping(source="category.id", target="categoryEntity.id")
    TimeEntryEntity toEntity(TimeEntry timeEntry);

    @Mapping(source="category.id", target="categoryId")
    @Mapping(source="category.name", target="categoryName")
    TimeEntryResponseDTO toResponseDTO(TimeEntry timeEntry);

}
