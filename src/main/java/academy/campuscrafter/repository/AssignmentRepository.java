package academy.campuscrafter.repository;

import academy.campuscrafter.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssignmentRepository extends JpaRepository<Assignment, UUID> {
}
