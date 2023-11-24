package academy.campuscrafter.mapper;

import academy.campuscrafter.dto.CreateUserRequestDto;
import academy.campuscrafter.dto.UserDto;
import academy.campuscrafter.filter.CurrentUser;
import academy.campuscrafter.model.Course;
import academy.campuscrafter.model.Role;
import academy.campuscrafter.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-25T01:26:50+0400",
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
    public User map(CurrentUser currentUser) {
        if ( currentUser == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( currentUser.getId() );
        user.name( currentUser.getName() );
        user.email( currentUser.getEmail() );
        user.role( currentUser.getRole() );

        return user.build();
    }

    @Override
    public UserDto mapToDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( user.getId() );
        userDto.setName( user.getName() );
        userDto.setEmail( user.getEmail() );
        userDto.setRole( user.getRole() );
        userDto.setJoinedDate( user.getJoinedDate() );
        userDto.setLastLogin( user.getLastLogin() );
        userDto.setPicturePath( user.getPicturePath() );
        userDto.setBio( user.getBio() );
        List<Course> list = user.getCourses();
        if ( list != null ) {
            userDto.setCourses( new ArrayList<Course>( list ) );
        }

        return userDto;
    }
}
