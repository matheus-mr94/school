package br.com.alura.school.enrollment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>{

	Optional<Enrollment> findByCourseIdAndUserId(Long courseId, Long userId );


}
