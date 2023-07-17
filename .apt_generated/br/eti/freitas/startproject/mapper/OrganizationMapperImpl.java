package br.eti.freitas.startproject.mapper;

import br.eti.freitas.startproject.dto.OrganizationDto;
import br.eti.freitas.startproject.model.Organization;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-15T13:14:10-0300",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220802-0458, environment: Java 17.0.4.1 (Eclipse Adoptium)"
)
public class OrganizationMapperImpl implements OrganizationMapper {

    @Override
    public OrganizationDto mapToDto(Organization model) {
        if ( model == null ) {
            return null;
        }

        OrganizationDto organizationDto = new OrganizationDto();

        organizationDto.setEmail( model.getEmail() );
        organizationDto.setEnabled( model.isEnabled() );
        organizationDto.setId( model.getId() );
        organizationDto.setName( model.getName() );
        organizationDto.setOrganizationKey( model.getOrganizationKey() );

        return organizationDto;
    }

    @Override
    public Organization mapToModel(OrganizationDto dto) {
        if ( dto == null ) {
            return null;
        }

        Organization organization = new Organization();

        organization.setEmail( dto.getEmail() );
        organization.setEnabled( dto.isEnabled() );
        organization.setId( dto.getId() );
        organization.setName( dto.getName() );
        organization.setOrganizationKey( dto.getOrganizationKey() );

        return organization;
    }

    @Override
    public List<OrganizationDto> mapToDto(List<Organization> model) {
        if ( model == null ) {
            return null;
        }

        List<OrganizationDto> list = new ArrayList<OrganizationDto>( model.size() );
        for ( Organization organization : model ) {
            list.add( mapToDto( organization ) );
        }

        return list;
    }
}
