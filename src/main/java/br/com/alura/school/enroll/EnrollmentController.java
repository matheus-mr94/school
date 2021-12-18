package br.com.alura.school.enroll;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnrollmentController {
	
	private final EnrollmentRepository enrollRepository;
	
	public EnrollmentController(EnrollmentRepository enrollRepository) {
		this.enrollRepository = enrollRepository;
	}
	

}
