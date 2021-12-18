package br.com.alura.school.enrollment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.school.user.User;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>{

	Optional<Enrollment> findByCourseIdAndUserId(Long courseId, Long userId );

	List<Enrollment> findByUser(User user);


}
