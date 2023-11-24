package academy.campuscrafter.controler;

import academy.campuscrafter.dto.CreateCourseDto;
import academy.campuscrafter.mapper.CourseMapper;
import academy.campuscrafter.mapper.UserMapper;
import academy.campuscrafter.filter.CurrentUser;
import academy.campuscrafter.model.Course;
import academy.campuscrafter.model.Role;
import academy.campuscrafter.model.User;
import academy.campuscrafter.service.CourseService;
import academy.campuscrafter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CourseController {
    private final UserService userService;
    //    private final PasswordEncoder passwordEncoder;//    private final JwtService jwtService;
    private final CourseMapper courseMapper;
    private final UserMapper userMapper;
    private final CourseService courseService;

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAll() {
        List<Course> courses = courseService.findAll();
        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/courses")
    public ResponseEntity<Course> create(@RequestBody CreateCourseDto createCourseDto,
                                         @AuthenticationPrincipal CurrentUser currentUser) {
        if (!userMapper.map(currentUser).getRole().equals(Role.TEACHER) && !userMapper.map(currentUser).getRole().equals(Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Course course = courseMapper.map(createCourseDto);
        Optional<User> user = userService.findByEmail(currentUser.getEmail());
        course.setTeacher(user.get());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(createCourseDto.getStartD(), formatter);
        course.setStartDate(dateTime);
        courseService.save(course);
        return ResponseEntity.ok(course);
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Course> create(@PathVariable("id") UUID id, @AuthenticationPrincipal CurrentUser currentUser) {
        if (!userMapper.map(currentUser).getRole().equals(Role.TEACHER) &&
                !userMapper.map(currentUser).getRole().equals(Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<Course> courseOptional = courseService.findByID(id);
        if (courseOptional.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        courseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}