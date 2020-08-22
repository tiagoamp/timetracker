package br.com.tiagoamp.timetracker.mapper;

import br.com.tiagoamp.timetracker.dto.UserRequestDTO;
import br.com.tiagoamp.timetracker.dto.UserResponseDTO;
import br.com.tiagoamp.timetracker.model.User;
import br.com.tiagoamp.timetracker.repository.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface UserMapper {

    // UserMapper INSTANCE = Mappers.getMapper(UserMapper.class); // if it is not a springboot app, use this factory

    User toModel(UserRequestDTO userReqDTO);
    User toModel(UserEntity userEntity);

    UserEntity toEntity(User user);

    UserResponseDTO toResponseDTO(User user);

}
