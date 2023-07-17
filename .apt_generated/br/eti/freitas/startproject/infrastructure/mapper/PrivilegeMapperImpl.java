package br.eti.freitas.startproject.infrastructure.mapper;

import br.eti.freitas.startproject.infrastructure.dto.PrivilegeDto;
import br.eti.freitas.startproject.infrastructure.model.Privilege;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-15T15:27:56-0300",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220802-0458, environment: Java 17.0.4.1 (Eclipse Adoptium)"
)
public class PrivilegeMapperImpl implements PrivilegeMapper {

    @Override
    public PrivilegeDto mapToDto(Privilege model) {
        if ( model == null ) {
            return null;
        }

        PrivilegeDto privilegeDto = new PrivilegeDto();

        privilegeDto.setEnabled( model.isEnabled() );
        privilegeDto.setId( model.getId() );
        privilegeDto.setResource( model.getResource() );
        privilegeDto.setType( model.getType() );

        return privilegeDto;
    }

    @Override
    public Privilege mapToModel(PrivilegeDto dto) {
        if ( dto == null ) {
            return null;
        }

        Privilege privilege = new Privilege();

        privilege.setEnabled( dto.isEnabled() );
        privilege.setId( dto.getId() );
        privilege.setResource( dto.getResource() );
        privilege.setType( dto.getType() );

        return privilege;
    }

    @Override
    public List<PrivilegeDto> mapToDto(List<Privilege> model) {
        if ( model == null ) {
            return null;
        }

        List<PrivilegeDto> list = new ArrayList<PrivilegeDto>( model.size() );
        for ( Privilege privilege : model ) {
            list.add( mapToDto( privilege ) );
        }

        return list;
    }
}
