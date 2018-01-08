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
import org.levi9.exception.MyAlreadyExistsException;
import org.levi9.exception.MyNotFoundException;
import org.levi9.exception.MySavingInRepoException;
import org.levi9.model.ComponentType;
import org.levi9.repository.TypeRepository;
import org.levi9.service.impl.TypeServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TypeServiceTests {

	@Mock
	private TypeRepository typeRepository;

	@InjectMocks
	private TypeServiceImpl typeService;

	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreatePass() {

		final ComponentType type = new ComponentType(null, true, "1-n");
		when(typeRepository.findByName(any(String.class))).thenReturn(null);
		when(typeRepository.save(any(ComponentType.class))).thenReturn(null);
		typeService.addType(type);

		verify(typeRepository).findByName(any(String.class));
		verify(typeRepository).save(any(ComponentType.class));
	}

	@Test
	public void testCreateTypeExist() {
		final ComponentType type = new ComponentType("type", true, "1-n");
		when(typeRepository.findByName(any(String.class))).thenReturn(type);
		exception.expect(MyAlreadyExistsException.class);
		typeService.addType(type);

	}

	@Test
	public void testGetAllPass() {
		final ComponentType type = new ComponentType("type", true, "1-n");
		final List<ComponentType> types = new ArrayList<ComponentType>();
		types.add(type);
		when(typeRepository.findAll()).thenReturn(types);

		final List<ComponentType> typesR = typeService.getAllTypes();

		verify(typeRepository).findAll();
		assertTrue(typesR.size() > 0);
	}

	@Test
	public void testRequiredtypesPass() {
		final ComponentType type = new ComponentType("type", true, "1-n");
		final List<ComponentType> types = new ArrayList<ComponentType>();
		types.add(type);
		when(typeRepository.findByRequired(true)).thenReturn(types);

		final List<ComponentType> typesR = typeService.getRequiredTypes();

		verify(typeRepository).findByRequired(true);
		assertTrue(typesR.size() > 0);
	}

	@Test
	public void testUpdatePass() {
		final ComponentType type = new ComponentType("type", true, "1-n");
		when(typeRepository.findByName(any(String.class))).thenReturn(type);
		when(typeRepository.save(any(ComponentType.class))).thenReturn(type);
		final ComponentType savedType = typeService.updateType(type);

		verify(typeRepository).findByName(any(String.class));
		verify(typeRepository).save(any(ComponentType.class));
		assertTrue(savedType.getName().equals("type"));
	}

	@Test
	public void testUpdateTypeNull() {
		final ComponentType type = new ComponentType("type", true, "1-n");
		when(typeRepository.findByName(any(String.class))).thenReturn(null);
		exception.expect(MyNotFoundException.class);
	    typeService.updateType(type);

	}
	
	@Test
	public void testUpdateTypeSaveNull() {
		final ComponentType type = new ComponentType("type", true, "1-n");
		when(typeRepository.findByName(any(String.class))).thenReturn(type);
		when(typeRepository.save(any(ComponentType.class))).thenReturn(null);
		exception.expect(MySavingInRepoException.class);
	    typeService.updateType(type);

	}

	@Test
	public void testDeletePass() {
		final ComponentType type = new ComponentType("type", true, "1-n");
		when(typeRepository.findByName(any(String.class))).thenReturn(type);
		when(typeRepository.save(any(ComponentType.class))).thenReturn(type);
		typeService.deleteType(type.getName());

	}

	@Test
	public void testDeleteTypeNull() {
		final ComponentType type = new ComponentType("type", true, "1-n");
		when(typeRepository.findByName(any(String.class))).thenReturn(null);
		exception.expect(MyNotFoundException.class);
		typeService.deleteType(type.getName());
	}
}
