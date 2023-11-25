package academy.campuscrafter.controler;

import academy.campuscrafter.dto.CreateCourseDto;
import academy.campuscrafter.mapper.CourseMapper;
import academy.campuscrafter.mapper.UserMapper;
import academy.campuscrafter.model.Course;
import academy.campuscrafter.model.Role;
import academy.campuscrafter.filter.CurrentUser;
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
    private final CourseMapper courseMapper;
    private final UserMapper userMapper;
    private final CourseService courseService;

    // Retrieve all courses
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAll() {
        List<Course> courses = courseService.findAll();
        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(courses);
    }


    // Create a new course (Only TEACHER and ADMIN can create)
    @PostMapping("/courses")
    public ResponseEntity<Course> create(@RequestBody CreateCourseDto createCourseDto,
                                         @AuthenticationPrincipal CurrentUser currentUser) {
        // Check if the current user is a TEACHER or ADMIN
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


    // Delete a course by ID (Only TEACHER and ADMIN can delete)
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Course> delete(@PathVariable("id") UUID id, @AuthenticationPrincipal CurrentUser currentUser) {
        // Check if the current user is a TEACHER or ADMIN
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


    // Update a course by ID (Only TEACHER and ADMIN can update)
    @PutMapping("/courses/{id}")
    public ResponseEntity<Course> update(@PathVariable("id") UUID id,
                                         @RequestBody CreateCourseDto updateCourseDto,
                                         @AuthenticationPrincipal CurrentUser currentUser) {
        // Retrieve the existing course
        Optional<Course> courseOptional = courseService.findByID(id);
        if (courseOptional.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Course existingCourse = courseOptional.get();
        // Check if the current user is a TEACHER or ADMIN
        if (!userMapper.map(currentUser).getRole().equals(Role.TEACHER) &&
                !userMapper.map(currentUser).getRole().equals(Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Update the course details
        existingCourse.setTitle(updateCourseDto.getTitle());
        existingCourse.setDescription(updateCourseDto.getDescription());
        existingCourse.setCredit(updateCourseDto.getCredit());
        existingCourse.setEnrolmentLimit(updateCourseDto.getEnrolmentLimit());

        // Save the updated course
        courseService.save(existingCourse);

        return ResponseEntity.ok(existingCourse);
    }

}
