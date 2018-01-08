package org.levi9.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.levi9.DTO.ChangePasswordDto;
import org.levi9.DTO.EditUserDTO;
import org.levi9.DTO.RegistrationDTO;
import org.levi9.exception.MyNotValidParamsException;
import org.levi9.exception.MyValidationFormException;
import org.levi9.model.Address;
import org.levi9.model.Role;
import org.levi9.model.User;
import org.levi9.model.VerificationToken;
import org.levi9.repository.AddressRepository;
import org.levi9.repository.UserRepository;
import org.levi9.repository.VerificationTokenRepository;
import org.levi9.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author j.ilic REST point for user manipulation.
 */
@RestController
@RequestMapping(value = "/api/users")
@CrossOrigin
public class UserController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private VerificationTokenRepository verificationTokenRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	UserService userService;

	/**
	 * @return all users from database This method gets all existing users in
	 *         database
	 */
	@GetMapping(value = "/all")
	public ResponseEntity<Collection<User>> getUsers() {
		Collection<User> users = userService.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	/**
	 * @param userDTO
	 *            is user which want to register
	 * @param result
	 *            is information if component is in valid form
	 * @return registered user This method register new user in system
	 */
	@PostMapping(value = "/register")
	public ResponseEntity<User> register(@RequestBody @Valid RegistrationDTO userDTO, BindingResult result) {
		if (result.hasErrors()) {
			throw new MyNotValidParamsException("Registration Failed. Not all parameters are valid");
		}
		if (!userDTO.getPassword().equals(userDTO.getRepeatedPassword())) {
			throw new MyValidationFormException("Validation failed, passwords doesn't match");
		}
		User user = new User();
		user.setName(userDTO.getUsername());
		user.setPassword(userDTO.getPassword());
		user.setEmail(userDTO.getEmail());
		Address a = addressRepository.findOne(1L);
		user.setAddress(a);
		user.setAuthorities(Role.USER);
		User created = userService.create(user);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	@PutMapping(value = "/password")
	public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordDto cpdto) {
		this.userService.changePassword(cpdto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping(value = "/update")
	public ResponseEntity<User> updateProfile(@RequestBody EditUserDTO user) {
		User u = this.userService.update(user);
		return new ResponseEntity<>(u, HttpStatus.OK);

	}

	/**
	 * @param token
	 *            which is sent on email
	 * @param datum
	 *            when email is sent for register
	 * @throws ParseException
	 *             This method is for verifying if user accept email request
	 */
	@GetMapping(value = "/confirm/{token:.+}", produces = MediaType.APPLICATION_JSON_VALUE)
	public RedirectView verify(@PathVariable String token) {
		RedirectView view = new RedirectView();
		view.setUrl("http://localhost:4200");
		logger.info("> get user verify email:{}", token);
		VerificationToken vt = this.verificationTokenRepository.findByToken(token);
		User user = vt.getUser();
		user.setVerified(true);
		this.userRepository.save(user);
		logger.info("< get user verify email:{}", token);
		return view;
	}

}
