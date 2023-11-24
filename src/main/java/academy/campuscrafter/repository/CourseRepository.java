package academy.campuscrafter.repository;

import academy.campuscrafter.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {


}
