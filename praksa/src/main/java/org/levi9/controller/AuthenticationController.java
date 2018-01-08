package org.levi9.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.levi9.DTO.AuthenticationRequestDTO;
import org.levi9.DTO.AuthenticationResponseDTO;
import org.levi9.DTO.SecurityUser;
import org.levi9.security.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
@CrossOrigin
public class AuthenticationController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${levi9.token.header}")
	private String tokenHeader;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private UserDetailsService userDetailsService;

	@PostMapping
	public ResponseEntity<?> authenticationRequest(@RequestBody AuthenticationRequestDTO authenticationRequest) {
		// Perform the authentication
		Authentication authentication = null;
		UsernamePasswordAuthenticationToken t = new UsernamePasswordAuthenticationToken(
				authenticationRequest.getEmail(), authenticationRequest.getPassword());
		try {
			authentication = this.authenticationManager.authenticate(t);
		} catch (AuthenticationException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// Reload password post-authentication so we can generate token
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		SecurityUser su = (SecurityUser) userDetails;
		String token = this.tokenUtils.generateToken(userDetails);

		// Return the token
		AuthenticationResponseDTO authResponse = new AuthenticationResponseDTO(token, su.getId(), su.getEmail(),
				su.getAuthorities().toString(), su.isEnabled(), su.getUsername(), su.getAddress());
		return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}

}
