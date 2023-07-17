package br.eti.freitas.startproject.infrastructure.mapper;

import br.eti.freitas.startproject.infrastructure.dto.UserDto;
import br.eti.freitas.startproject.infrastructure.model.User;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-15T15:50:10-0300",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220802-0458, environment: Java 17.0.4.1 (Eclipse Adoptium)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto mapToDto(User model) {
        if ( model == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setEmail( model.getEmail() );
        userDto.setId( model.getId() );
        userDto.setName( model.getName() );
        userDto.setPassword( model.getPassword() );
        userDto.setUsername( model.getUsername() );

        return userDto;
    }

    @Override
    public User mapToModel(UserDto dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( dto.getEmail() );
        user.setId( dto.getId() );
        user.setName( dto.getName() );
        user.setPassword( dto.getPassword() );
        user.setUsername( dto.getUsername() );

        return user;
    }
}
