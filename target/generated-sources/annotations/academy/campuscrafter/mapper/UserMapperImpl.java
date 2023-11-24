package academy.campuscrafter.mapper;

import academy.campuscrafter.dto.CreateUserRequestDto;
import academy.campuscrafter.dto.UserDto;
import academy.campuscrafter.model.Role;
import academy.campuscrafter.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-24T19:35:49+0400",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User map(CreateUserRequestDto createUserRequestDto) {
        if ( createUserRequestDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.name( createUserRequestDto.getName() );
        user.email( createUserRequestDto.getEmail() );
        if ( createUserRequestDto.getRole() != null ) {
            user.role( Enum.valueOf( Role.class, createUserRequestDto.getRole() ) );
        }
        user.password( createUserRequestDto.getPassword() );

        return user.build();
    }

    @Override
    public UserDto mapToDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        return userDto;
    }
}
