package br.com.alura.school.enrollment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnrollmentResponse {

	@JsonProperty("email")
	private final String email;

	@JsonProperty("quantidade_matriculas")
	private final Integer enrollmentQuantity;

	public EnrollmentResponse(String email, Integer enrollmentQuantity) {
		this.email = email;
		this.enrollmentQuantity = enrollmentQuantity;
	}

	public String getEmail() {
		return email;
	}

	public Integer getEnrollmentQuantity() {
		return enrollmentQuantity;
	}

}
