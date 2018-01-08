package org.levi9.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "authorities")
	private String authorities;

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

	@OneToMany(fetch = FetchType.EAGER)
	private Set<Configuration> configurations = new HashSet<>();

	@Column
	private Boolean verified;

	@OneToOne
	@JoinColumn(name = "address_id")
	private Address address;

	public User() {

	}

	public User(Long id, String name, String email, String password, String authorities) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public User(Long id, String name, String email, String password, String authorities,
			Set<Configuration> configurations, Address address) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.configurations = configurations;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<Configuration> getConfigurations() {
		return configurations;
	}

	public void setConfigurations(Set<Configuration> configurations) {
		this.configurations = configurations;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", authorities="
				+ authorities + ", configurations=" + configurations + ", verified=" + verified + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		User other = (User) obj;
		if (email.equals(other.email) && password.equals(other.password)) {
			return true;
		} else {
			return false;
		}
	}

	public String getAuthorities() {
		return authorities;
	}

}
