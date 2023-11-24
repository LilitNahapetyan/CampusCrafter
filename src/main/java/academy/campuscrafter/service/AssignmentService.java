package academy.campuscrafter.service;

import academy.campuscrafter.model.Assignment;
import academy.campuscrafter.model.Course;
import academy.campuscrafter.repository.AssignmentRepository;
import academy.campuscrafter.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;

    public Optional<Assignment> findByID(UUID uuid){
        return assignmentRepository.findById(uuid);
    }

}
