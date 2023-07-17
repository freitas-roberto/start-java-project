package br.eti.freitas.startproject.infrastructure.mapper;

import br.eti.freitas.startproject.infrastructure.dto.SignUpDto;
import br.eti.freitas.startproject.infrastructure.model.User;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-15T15:50:10-0300",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220802-0458, environment: Java 17.0.4.1 (Eclipse Adoptium)"
)
public class AuthenticateMapperImpl implements AuthenticateMapper {

    @Override
    public SignUpDto mapToDto(User model) {
        if ( model == null ) {
            return null;
        }

        SignUpDto signUpDto = new SignUpDto();

        signUpDto.setEmail( model.getEmail() );
        signUpDto.setName( model.getName() );
        signUpDto.setPassword( model.getPassword() );
        signUpDto.setUsername( model.getUsername() );

        return signUpDto;
    }

    @Override
    public User mapToModel(SignUpDto dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( dto.getEmail() );
        user.setName( dto.getName() );
        user.setPassword( dto.getPassword() );
        user.setUsername( dto.getUsername() );

        return user;
    }
}
