package org.levi9.DTO;

import lombok.Data;

import org.levi9.model.Address;
import org.levi9.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class SecurityUser implements UserDetails {
	private static final long serialVersionUID = -949811899438278427L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Long id;
	private String password;
	private String email;
	private Collection<? extends GrantedAuthority> authorities;
	private Boolean enabled;
	private String username;
	private Address address;

	public SecurityUser() {
		super();
	}

	public SecurityUser(User user) {
		this.username = user.getName();
		this.id = user.getId();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthorities());
		this.enabled = user.getVerified();
		this.address = user.getAddress();
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public String getEmail() {
		return this.email;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "SecurityUser [id=" + id + ", password=" + password + ", email=" + email + ", authorities=" + authorities
				+ ", enabled=" + enabled + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authoritiess = new ArrayList<>();
		if (this.authorities.toString().equals("[ADMIN]"))
			authoritiess.add(new SimpleGrantedAuthority("ADMIN"));
		else
			authoritiess.add(new SimpleGrantedAuthority("USER"));

		return authoritiess;
	}

}
