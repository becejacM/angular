package org.levi9.service.impl;

import java.util.List;

import org.levi9.DTO.ChangePasswordDto;
import org.levi9.DTO.EditUserDTO;
import org.levi9.exception.MyAlreadyExistsException;
import org.levi9.exception.MyNotFoundException;
import org.levi9.model.User;
import org.levi9.model.VerificationToken;
import org.levi9.repository.UserRepository;
import org.levi9.repository.VerificationTokenRepository;
import org.levi9.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author m.becejac
 * 
 *         Users service management
 *
 */
@Service
public class UserServiceImpl implements UserService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private VerificationTokenRepository verificationTokenRepository;

	private final Integer verificationTokenExpiryTime = 1440;

	/**
	 * Function returns user with requested id
	 * 
	 * @param id
	 *            - id of user, who is going to be returned
	 * @return user with requested id
	 */

	@Override
	public User findById(Long id) {
		return userRepository.findOne(id);
	}

	/**
	 * Function returns all users that exists in database
	 * 
	 * @return list of all users
	 */
	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	/**
	 * Function creates new user and save it to database
	 * 
	 * @param user
	 *            - user who is going to be saved in database
	 * @return saved user
	 */
	@Override
	public User create(User user) {
		User u = userRepository.findByName(user.getName());
		if (u != null)
			throw new MyAlreadyExistsException("User " + user.getName() + " not found");
		user.setVerified(false);
		User saved = userRepository.save(user);
		try {
			VerificationToken verificationToken = new VerificationToken(saved, this.verificationTokenExpiryTime,
					"REGISTRATION");
			verificationTokenRepository.save(verificationToken);
		} catch (Exception e) {

		}

		return this.userRepository.findByEmail(user.getEmail());
	}

	/**
	 * Function updates user
	 * 
	 * @param user
	 *            - user who is going to be updated
	 * @return
	 */
	@Override
	public User update(EditUserDTO user) {
		try {
			User u = this.userRepository.findOne(user.getId());
			if (u == null) {
				throw new MyNotFoundException("User with id " + user.getId() + " not found!");
			}
			u.setEmail(user.getEmail());
			u.setName(user.getUsername());

			return userRepository.save(u);
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Function updates password
	 * 
	 * @param cpdto
	 *            - DTO for changing password
	 */
	@Override
	public void changePassword(ChangePasswordDto cpdto) {
		try {
			User u = this.userRepository.findOne(cpdto.getUserId());
			if (u == null) {
				throw new MyNotFoundException("User with id " + cpdto.getUserId() + " not found!");
			}

			if (cpdto.getOldPassword().equals(u.getPassword())) {
				if (cpdto.getNewPassword().equals(cpdto.getRepeatedNewPassword())) {
					u.setPassword(cpdto.getNewPassword());
					userRepository.save(u);
				}
			}
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			throw new IllegalArgumentException();
		}
	}

}
