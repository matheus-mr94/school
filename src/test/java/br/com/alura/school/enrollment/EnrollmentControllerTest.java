package br.com.alura.school.enrollment;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class EnrollmentControllerTest {

	private final ObjectMapper jsonMapper = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private EnrollmentRepository enrollmentRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Test
	void should_add_new_enrollment() throws Exception{
		userRepository.save(new User("ana", "ana@email.com"));
		courseRepository.save(new Course("java-1", "Java OO", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));
		NewEnrollmentRequest enrollmentRequest = new NewEnrollmentRequest("ana");
		
		mockMvc.perform(post("/courses/java-1/enroll")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(enrollmentRequest)))
				.andExpect(status().isCreated());
	}
	
	@Test
	void should_not_allow_duplicated_user_in_course() throws Exception{
		User user = userRepository.save(new User("ana", "ana@email.com"));
		Course course = courseRepository.save(new Course("java-1", "Java OO", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));
		NewEnrollmentRequest enrollmentRequest = new NewEnrollmentRequest("ana");
		
		enrollmentRepository.save(new Enrollment(user,course,LocalDate.now()));
		
		mockMvc.perform(post("/courses/java-1/enroll")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(enrollmentRequest)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void should_retrieve_enroll_report_for_one_user() throws Exception {
		User user = userRepository.save(new User("ana", "ana@email.com"));
		Course course = courseRepository.save(new Course("java-1", "Java OO", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));
		
		enrollmentRepository.save(new Enrollment(user,course,LocalDate.now()));
		
		 mockMvc.perform(get("/courses/enroll/report")
	                .accept(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	                .andExpect(jsonPath("$[0].email", is("ana@email.com")))
	                .andExpect(jsonPath("$[0].quantidade_matriculas", is(1)));		
		
		
	}
	
	 @Test
	 void should_retrieve_no_content_if_doesnt_exist_enrollment() throws Exception {
	        mockMvc.perform(get("/courses/enroll/report")
	                .accept(MediaType.APPLICATION_JSON))
	                .andExpect(status().isNoContent());
	    }
	 
	 @Test
		void should_retrieve_enroll_report_ordered_by_reversed_quantity_of_enrollments() throws Exception {
			User ana = userRepository.save(new User("ana", "ana@email.com"));
			Course javaOO = courseRepository.save(new Course("java-1", "Java OO", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));
			
			User alex = userRepository.save(new User("alex", "alex@email.com"));
			Course spring = courseRepository.save(new Course("spring-1", "Spring Boot", "Spring Boot"));
			
			enrollmentRepository.save(new Enrollment(ana,spring,LocalDate.now()));
			enrollmentRepository.save(new Enrollment(alex,spring,LocalDate.now()));
			enrollmentRepository.save(new Enrollment(alex,javaOO,LocalDate.now()));
			
			 mockMvc.perform(get("/courses/enroll/report")
		                .accept(MediaType.APPLICATION_JSON))
		                .andExpect(status().isOk())
		                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		                .andExpect(jsonPath("$[0].email", is("alex@email.com")))
		                .andExpect(jsonPath("$[0].quantidade_matriculas", is(2)))		
		                .andExpect(jsonPath("$[1].email", is("ana@email.com")))
		                .andExpect(jsonPath("$[1].quantidade_matriculas", is(1)));		
			
			
		}
	
	

}
