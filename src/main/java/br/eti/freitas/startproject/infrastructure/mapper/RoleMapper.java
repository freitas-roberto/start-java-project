package br.eti.freitas.startproject.infrastructure.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.eti.freitas.startproject.infrastructure.dto.RoleDto;
import br.eti.freitas.startproject.infrastructure.model.Role;
import org.mapstruct.Mapping;

@Mapper
public interface RoleMapper {

    RoleMapper MAPPER = Mappers.getMapper( RoleMapper.class );
 
    RoleDto mapToDto(Role model);

    @Mapping(target = "deleted", ignore = true)
	@Mapping(target = "users", ignore = true)
	@Mapping(target = "privileges", ignore = true)
	Role mapToModel(RoleDto dto);

    List<RoleDto> mapToDto(List<Role> model);
    
}
