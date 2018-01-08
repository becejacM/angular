package org.levi9.ServiceTests;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.levi9.DTO.ChangePasswordDto;
import org.levi9.DTO.EditUserDTO;
import org.levi9.exception.MyAlreadyExistsException;
import org.levi9.exception.MyNotFoundException;
import org.levi9.model.User;
import org.levi9.model.VerificationToken;
import org.levi9.repository.UserRepository;
import org.levi9.repository.VerificationTokenRepository;
import org.levi9.service.impl.UserServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author m.becejac
 *
 */
public class UserServiceTests {

	@Mock
	private UserRepository userRepository;
	@Mock
	private VerificationTokenRepository verificationTokenRepository;

	@InjectMocks
	private UserServiceImpl userService;

	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreatePass() {
		final User user = new User(1L, "Pera Peric", "pera@live.com", "pw", "USER");
		when(userRepository.findByName(any(String.class))).thenReturn(null);
		when(userRepository.save(any(User.class))).thenReturn(user);
		when(userRepository.findByEmail(any(String.class))).thenReturn(null);
		assertTrue(user.getName().equals("Pera Peric"));
		userService.create(user);
	}

	@Test
	public void testCreateUserNull() {
		final User user = new User(1L, "Pera Peric", "pera@live.com", "pw", "USER");
		when(userRepository.findByName(any(String.class))).thenReturn(user);
		exception.expect(MyAlreadyExistsException.class);
		when(userRepository.findByEmail(any(String.class))).thenReturn(null);
		userService.create(user);
	}

	@Test
	public void testFindByIdPass() {
		final User user = new User(1L, "Pera Peric", "pera@live.com", "pw", "USER");
		when(userRepository.findOne(any(Long.class))).thenReturn(user);
		userService.findById(user.getId());

		verify(userRepository).findOne(any(Long.class));
		assertTrue(user.getName().equals("Pera Peric"));
	}

	@Test
	public void testGetAllPass() {
		final User user = new User(1L, "Pera Peric", "pera@live.com", "pw", "USER");
		List<User> users = new ArrayList<>();
		users.add(user);
		when(userRepository.findAll()).thenReturn(users);
		users = userService.getAllUsers();

		verify(userRepository).findAll();
		assertTrue(users.size() > 0);
	}

	@Test
	public void testUpdatePass() {
		final EditUserDTO userDTO = new EditUserDTO(1L, "Pera Peric", "pera@live.com");
		final User user = new User(1L, "Pera Peric", "pera@live.com", "pw", "USER");
		when(userRepository.findOne(any(long.class))).thenReturn(user);
		when(userRepository.save(any(User.class))).thenReturn(user);
		userService.update(userDTO);
		verify(userRepository).findOne(any(Long.class));
		verify(userRepository).save(any(User.class));
		assertTrue(user.getName().equals("Pera Peric"));
	}

	@Test
	public void testUpdateUserNull() {
		final EditUserDTO userDTO = new EditUserDTO(1L, "Pera Peric", "pera@live.com");
		final User user = new User(1L, "Pera Peric", "pera@live.com", "pw", "USER");
		when(userRepository.findOne(any(Long.class))).thenReturn(null);
		when(userRepository.save(any(User.class))).thenReturn(user);
		exception.expect(MyNotFoundException.class);
		userService.update(userDTO);

	}

	@Test
	public void testChangePasswordPass() {
		final ChangePasswordDto DTO = new ChangePasswordDto("pass", "new", "new", 1L);
		final User user = new User(1L, "Pera Peric", "pera@live.com", "pass", "USER");
		when(userRepository.findOne(any(long.class))).thenReturn(user);
		when(userRepository.save(any(User.class))).thenReturn(user);
		when(verificationTokenRepository.save(any(VerificationToken.class))).thenReturn(new VerificationToken());
		userService.changePassword(DTO);

		verify(userRepository).findOne(any(Long.class));
		verify(userRepository).save(any(User.class));
		assertTrue(user.getName().equals("Pera Peric"));
	}

	@Test
	public void testChangePasswordUserNull() {
		final ChangePasswordDto DTO = new ChangePasswordDto("pass", "new", "new", 1L);
		final User user = new User(1L, "Pera Peric", "pera@live.com", "pass", "USER");
		when(userRepository.findOne(any(Long.class))).thenReturn(null);
		when(userRepository.save(any(User.class))).thenReturn(user);
		when(verificationTokenRepository.save(any(VerificationToken.class))).thenReturn(new VerificationToken());
		exception.expect(MyNotFoundException.class);
		userService.changePassword(DTO);

	}
}
