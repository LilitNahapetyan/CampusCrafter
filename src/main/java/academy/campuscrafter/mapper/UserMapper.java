package academy.campuscrafter.mapper;

import academy.campuscrafter.dto.CreateUserRequestDto;
import academy.campuscrafter.dto.UserDto;
import academy.campuscrafter.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User map(CreateUserRequestDto createUserRequestDto);
    UserDto mapToDto(User user);

}
