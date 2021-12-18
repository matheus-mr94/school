package br.com.alura.school.enrollment;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;

@RestController
public class EnrollmentController {
	
	private final EnrollmentRepository enrollmentRepository;
	private final CourseRepository courseRepository;
	private final UserRepository userRepository;
	
	public EnrollmentController(EnrollmentRepository enrollmentRepository, CourseRepository courseRepository, UserRepository userRepository) {
		this.enrollmentRepository = enrollmentRepository;
		this.courseRepository = courseRepository;
		this.userRepository = userRepository;
	}
	
	@PostMapping("/courses/{courseCode}/enroll")
	public ResponseEntity<Void> newEnrollment ( @PathVariable ("courseCode") String courseCode, @Valid @RequestBody NewEnrollmentRequest newRequest){
		User user = userRepository.findByUsername(newRequest.getUsername())
				.orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("User %s not found", newRequest.getUsername())));
		
		Course course = courseRepository.findByCode(courseCode)
				.orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("Course with code %s not found", courseCode)));
		
		
		Optional<Enrollment> enrollment = enrollmentRepository.findByCourseIdAndUserId(course.getId(), user.getId());
		if(enrollment.isPresent()) {
			return ResponseEntity.badRequest().build();
		}
		
		enrollmentRepository.save(new Enrollment(user,course, LocalDate.now()));
		return ResponseEntity.status(HttpStatus.CREATED).build();
		
	}

}
