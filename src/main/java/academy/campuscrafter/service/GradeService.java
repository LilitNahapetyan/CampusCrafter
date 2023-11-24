package academy.campuscrafter.service;

import academy.campuscrafter.model.Course;
import academy.campuscrafter.model.Grade;
import academy.campuscrafter.repository.CourseRepository;
import academy.campuscrafter.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GradeService {
    private final GradeRepository gradeRepository;

    public Optional<Grade> findByID(UUID uuid){
        return gradeRepository.findById(uuid);
    }

}
