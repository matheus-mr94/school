package br.com.alura.school.user;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.alura.school.enrollment.Enrollment;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Size(max = 20)
	@NotBlank
	@Column(nullable = false, unique = true)
	private String username;

	@NotBlank
	@Email
	@Column(nullable = false, unique = true)
	private String email;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Enrollment> usersEnrollments;

	@Deprecated
	protected User() {
	}

	public User(String username, String email) {
		this.username = username;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public Long getId() {
		return id;
	}

}
