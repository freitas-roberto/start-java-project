package br.eti.freitas.startproject.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.eti.freitas.startproject.infrastructure.dto.SignUpDto;
import br.eti.freitas.startproject.infrastructure.model.User;

@Mapper
public interface AuthenticateMapper {

	AuthenticateMapper MAPPER = Mappers.getMapper( AuthenticateMapper.class );
    
    SignUpDto mapToDto(User model);
    
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "direct_privileges", ignore = true)
	@Mapping(target = "organization", ignore = true)
	@Mapping(target = "roles", ignore = true)
	@Mapping(target = "privileges", ignore = true)
	@Mapping(target = "deleted", ignore = true) 
    User mapToModel(SignUpDto dto);
    
}
