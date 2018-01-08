package org.levi9.DTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegistrationDTO {

	@NotNull
	@Size(min = 4, max = 10)
	private String username;

	@NotNull
	private String email;

	@NotNull
	@Size(min = 5, max = 12)
	private String password;

	@NotNull
	private String repeatedPassword;

	public RegistrationDTO() {

	}

	public RegistrationDTO(String username, String email, String password, String repeatedPassword) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.repeatedPassword = repeatedPassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

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

	public String getRepeatedPassword() {
		return repeatedPassword;
	}

	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}

	@Override
	public String toString() {
		return "RegistrationDTO [username=" + username + ", email=" + email + ", password=" + password
				+ ", repeatedPassword=" + repeatedPassword + "]";
	}

}
