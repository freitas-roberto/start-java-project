package br.eti.freitas.startproject.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.eti.freitas.startproject.dto.OrganizationDto;
import br.eti.freitas.startproject.model.Organization;
import org.mapstruct.Mapping;

@Mapper
public interface OrganizationMapper {

    OrganizationMapper MAPPER = Mappers.getMapper( OrganizationMapper.class );
 
    OrganizationDto mapToDto(Organization model);

    @Mapping(target = "deleted", ignore = true)
	Organization mapToModel(OrganizationDto dto);

    List<OrganizationDto> mapToDto(List<Organization> model);

}
