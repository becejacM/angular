package org.levi9.service;

import java.util.List;

import org.levi9.DTO.ChangePasswordDto;
import org.levi9.DTO.EditUserDTO;
import org.levi9.model.User;

/**
 * @author m.becejac
 * 
 *         Interface for user service
 *
 */
public interface UserService {

	public User findById(Long id);

	public List<User> getAllUsers();

	public User create(User user);

	public User update(EditUserDTO user);

	void changePassword(ChangePasswordDto cpdto);
}
