package br.com.alura.school.enroll;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.alura.school.course.Course;
import br.com.alura.school.user.User;

@Entity
public class Enrollment {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "course_id", referencedColumnName = "id")
	private Course course;
	
	@Column(nullable = false, name = "enroll_date")
	private LocalDate enrollDate;

	public Enrollment() {

	}

	public Enrollment(User user, Course course, LocalDate enrollDate) {
		this.user = user;
		this.course = course;
		this.enrollDate = enrollDate;
	}

	public User getUser() {
		return user;
	}

	public Course getCourse() {
		return course;
	}

	public LocalDate getEnrollDate() {
		return enrollDate;
	}

	
}
