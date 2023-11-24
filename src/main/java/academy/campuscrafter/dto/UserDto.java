package academy.campuscrafter.dto;

import academy.campuscrafter.model.Course;
import academy.campuscrafter.model.Role;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Data
public class UserDto {

    private UUID id;
    private String name;
    private String email;
    private Role role;
    private LocalDateTime joinedDate;
    private LocalDateTime lastLogin;
    private String picturePath;
    private String bio;
    private List<Course> courses;
}
