package br.eti.freitas.startproject.infrastructure.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.eti.freitas.startproject.infrastructure.dto.PrivilegeDto;
import br.eti.freitas.startproject.infrastructure.model.Privilege;
import org.mapstruct.Mapping;

@Mapper
public interface PrivilegeMapper {

	PrivilegeMapper MAPPER = Mappers.getMapper(PrivilegeMapper.class);
	
	PrivilegeDto mapToDto(Privilege model);
	
	@Mapping(target = "roles", ignore = true)
	@Mapping(target = "users", ignore = true)
	@Mapping(target = "deleted", ignore = true)
	Privilege mapToModel(PrivilegeDto dto);

	List<PrivilegeDto> mapToDto(List<Privilege> model);
}
