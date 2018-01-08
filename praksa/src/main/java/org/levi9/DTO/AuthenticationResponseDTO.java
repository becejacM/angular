package org.levi9.DTO;

import org.levi9.model.Address;

public class AuthenticationResponseDTO {

	private static final long serialVersionUID = 1L;
	private String token;
	private Long id;
	private String email;
	private String role;
	private Boolean enabled;
	private String username;
	private Address address;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public AuthenticationResponseDTO(String token, Long id, String email, String role, Boolean enabled, String username,
			Address address) {
		super();
		this.username = username;
		this.token = token;
		this.id = id;
		this.email = email;
		this.role = role;
		this.enabled = enabled;
		this.address = address;
	}

	public AuthenticationResponseDTO() {
		super();
	}

}
