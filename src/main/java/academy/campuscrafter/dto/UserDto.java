package academy.campuscrafter.dto;

import academy.campuscrafter.model.Course;
import academy.campuscrafter.model.Role;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
