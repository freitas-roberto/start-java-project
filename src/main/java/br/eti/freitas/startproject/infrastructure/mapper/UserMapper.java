package br.eti.freitas.startproject.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.eti.freitas.startproject.infrastructure.dto.UserDto;
import br.eti.freitas.startproject.infrastructure.model.User;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper( UserMapper.class );
 
	UserDto mapToDto(User model);
    
	@Mapping(target = "roles", ignore = true)
	@Mapping(target = "direct_privileges", ignore = true)
	@Mapping(target = "organization", ignore = true)
	@Mapping(target = "deleted", ignore = true)
	@Mapping(target = "privileges", ignore = true)
	User mapToModel(UserDto dto);

}
