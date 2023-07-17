package br.eti.freitas.startproject.infrastructure.mapper;

import br.eti.freitas.startproject.infrastructure.dto.RoleDto;
import br.eti.freitas.startproject.infrastructure.model.Role;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-15T15:50:19-0300",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220802-0458, environment: Java 17.0.4.1 (Eclipse Adoptium)"
)
public class RoleMapperImpl implements RoleMapper {

    @Override
    public RoleDto mapToDto(Role model) {
        if ( model == null ) {
            return null;
        }

        RoleDto roleDto = new RoleDto();

        roleDto.setDescription( model.getDescription() );
        roleDto.setEnabled( model.isEnabled() );
        roleDto.setId( model.getId() );
        roleDto.setName( model.getName() );

        return roleDto;
    }

    @Override
    public Role mapToModel(RoleDto dto) {
        if ( dto == null ) {
            return null;
        }

        Role role = new Role();

        role.setDescription( dto.getDescription() );
        role.setEnabled( dto.isEnabled() );
        role.setId( dto.getId() );
        role.setName( dto.getName() );

        return role;
    }

    @Override
    public List<RoleDto> mapToDto(List<Role> model) {
        if ( model == null ) {
            return null;
        }

        List<RoleDto> list = new ArrayList<RoleDto>( model.size() );
        for ( Role role : model ) {
            list.add( mapToDto( role ) );
        }

        return list;
    }
}
