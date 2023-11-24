package academy.campuscrafter.service;

import academy.campuscrafter.model.Course;
import academy.campuscrafter.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public Optional<Course> findByID(UUID uuid){
        return courseRepository.findById(uuid);
    }

}
