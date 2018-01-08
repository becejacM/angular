package org.levi9.DTO;

public class AuthenticationRequestDTO {
	private static final long serialVersionUID = 6624726180748515507L;
	private String email;
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

	public AuthenticationRequestDTO(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public AuthenticationRequestDTO() {
		super();
	}

}
