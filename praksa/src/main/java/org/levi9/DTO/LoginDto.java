package org.levi9.DTO;

import javax.validation.constraints.NotNull;

public class LoginDto {

	@Override
	public String toString() {
		return "LoginDto [email=" + email + ", password=" + password + "]";
	}

	@NotNull
	private String email;

	@NotNull
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LoginDto() {
	}

	public LoginDto(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

}
