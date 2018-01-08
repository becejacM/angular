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
import org.levi9.exception.MyNotFoundException;
import org.levi9.exception.MyQuantityNotZeroException;
import org.levi9.exception.MySavingInRepoException;
import org.levi9.model.Component;
import org.levi9.model.Manufacturer;
import org.levi9.model.ComponentType;
import org.levi9.repository.ComponentRepository;
import org.levi9.repository.ManufacturerRepository;
import org.levi9.repository.TypeRepository;
import org.levi9.service.impl.ComponentServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

public class ComponentServiceTest {

	@Mock
	private ComponentRepository componentRepository;

	@Mock
	private TypeRepository typeRepository;

	@Mock
	private ManufacturerRepository manufacturerRepository;

	@InjectMocks
	private ComponentServiceImpl componentService;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetComponentByID() {
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		when(componentRepository.findOne(any(Long.class))).thenReturn(component);
		componentService.getComponentByID(1l);
	}
	
	@Test
	public void testGetComponentsByPage() {
		List<Component> componentSet = new ArrayList<Component>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		componentSet.add(component);
		
		PageRequest pageRequest = new PageRequest(0,10);
		Page<Component> returnPage = new PageImpl<Component>(componentSet, pageRequest, 2);

		when(componentRepository.findAll(pageRequest)).thenReturn(returnPage);
		componentService.getComponents(pageRequest);
		verify(componentRepository).findAll(pageRequest);
	}
	
	@Test
	public void testGetComponents() {
		List<Component> componentSet = new ArrayList<Component>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		componentSet.add(component);
		when(componentRepository.findAll()).thenReturn(componentSet);
		componentService.getComponents();
		verify(componentRepository).findAll();
	}
	
	@Test
	public void findById() {
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		when(componentRepository.findOne(1L)).thenReturn(component);
		componentService.findById(1l);
		verify(componentRepository).findOne(1l);
	}
	
	@Test
	public void testAdd() {
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		final Manufacturer manufacturer = new Manufacturer("Manufacturer");
		final ComponentType type = new ComponentType("procesor", true, "0-1");
		component.setManufacturer(manufacturer);
		when(manufacturerRepository.findByName("Manufacturer")).thenReturn(manufacturer);
		when(typeRepository.findByName(any(String.class))).thenReturn(type);
		when(componentRepository.save(any(Component.class))).thenReturn(new Component());

		componentService.addComponent(component);
		verify(componentRepository).save(any(Component.class));
		assertTrue(component.getQuantity().equals(32));
		assertTrue(component.getName().equals("someComponent"));

	}
	
	@Test
	public void testAddManufacturerNull() {
		final ComponentType type = new ComponentType("procesor", true, "0-2");
		final Component component = new Component("someComponent", 1L, type, new Manufacturer(), new Integer(32),
				new Double(33), true);

		when(manufacturerRepository.findByName("Manufacturer")).thenReturn(null);
		exception.expect(MyNotFoundException.class);
		componentService.addComponent(component);
	}

	@Test
	public void testAddTypeNull() {
		final ComponentType type = new ComponentType("procesor", true, "0-2");
		final Manufacturer manufacturer = new Manufacturer("Manufacturer", 0L);
		final Component component = new Component("someComponent", 1L, type, manufacturer, new Integer(32),
				new Double(33), true);

		when(manufacturerRepository.findByName("Manufacturer")).thenReturn(manufacturer);
		when(typeRepository.findByName("procesor")).thenReturn(null);
		exception.expect(MyNotFoundException.class);
		componentService.addComponent(component);

	}

	@Test
	public void testAddSaveComponentNull() {
		final ComponentType type = new ComponentType("procesor", true, "0-2");
		final Manufacturer manufacturer = new Manufacturer("Manufacturer", 0L);
		final Component component = new Component("someComponent", 1L, type, manufacturer, new Integer(32),
				new Double(33), true);
		when(manufacturerRepository.findByName("Manufacturer")).thenReturn(manufacturer);
		when(typeRepository.findByName("procesor")).thenReturn(type);
		when(componentRepository.save(any(Component.class))).thenReturn(null);
		exception.expect(MySavingInRepoException.class);
		componentService.addComponent(component);

	}
	
	@Test
	public void testUpdatePass(){
		ComponentType type = new ComponentType("TypeName", true, "1:n");
		final Manufacturer manufacturer = new Manufacturer("Manufacturer", 0L);
		final Component component = new Component("someComponent", 1L, type, manufacturer, new Integer(32),
				new Double(33), true);

		when(componentRepository.findOne(any(Long.class))).thenReturn(component);
		when(manufacturerRepository.findByName("Manufacturer")).thenReturn(manufacturer);
		when(typeRepository.findByName(any(String.class))).thenReturn(type);
		when(componentRepository.save(any(Component.class))).thenReturn(component);
		final Component updatedComponent = componentService.updateComponent(component);
		assertTrue(updatedComponent.getName().equals("someComponent"));
	}
	
	@Test
	public void testUpdateComponentNotFound() {
		final ComponentType type = new ComponentType("procesor", true, "0-2");
		final Manufacturer manufacturer = new Manufacturer("Manufacturer", 0L);
		final Component component = new Component("someComponent", 1L, type, manufacturer, new Integer(32),
				new Double(33), true);
		when(componentRepository.findOne(any(Long.class))).thenReturn(null);
		exception.expect(MyNotFoundException.class);
		componentService.updateComponent(component);
	}

	@Test
	public void testUpdateManufacturerNull() throws MyNotFoundException {
		ComponentType type = new ComponentType("TypeName", true, "1:n");
		final Manufacturer manufacturer = new Manufacturer("Manufacturer", 0L);
		final Component component = new Component("someComponent", 1L, type, manufacturer, new Integer(32),
				new Double(33), true);

		when(componentRepository.findOne(any(Long.class))).thenReturn(component);
		when(manufacturerRepository.findByName("Manufacturer")).thenReturn(null);
		exception.expect(MyNotFoundException.class);
		componentService.updateComponent(component);
	}
	
	@Test
	public void testUpdateTypeNull() throws MyNotFoundException {
		ComponentType type = new ComponentType("TypeName", true, "1:n");
		final Manufacturer manufacturer = new Manufacturer("Manufacturer", 0L);
		final Component component = new Component("someComponent", 1L, type, manufacturer, new Integer(32),
				new Double(33), true);

		when(componentRepository.findOne(any(Long.class))).thenReturn(component);
		when(manufacturerRepository.findByName("Manufacturer")).thenReturn(manufacturer);
		when(typeRepository.findByName("procesor")).thenReturn(null);
		exception.expect(MyNotFoundException.class);
		componentService.updateComponent(component);
	}

	@Test
	public void testDeleteComponentPassActiveStatus() {
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(0), new Double(33), true);
		when(componentRepository.findOne(any(Long.class))).thenReturn(component);
		when(componentRepository.save(any(Component.class))).thenReturn(component);
		componentService.deleteComponent("1");

	}
	
	@Test
	public void testDeleteComponentPassStatusNotActive() {
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(0), new Double(33), false);
		when(componentRepository.findOne(any(Long.class))).thenReturn(component);
		when(componentRepository.save(any(Component.class))).thenReturn(component);
		componentService.deleteComponent("1");

	}

	@Test
	public void testDeleteComponentNotFound() {
		when(componentRepository.findOne(any(Long.class))).thenReturn(null);
		exception.expect(MyNotFoundException.class);
		componentService.deleteComponent("1");

	}

	@Test
	public void testDeleteComponentWithQuantityNotZero() {
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		when(componentRepository.findOne(any(Long.class))).thenReturn(component);
		exception.expect(MyQuantityNotZeroException.class);
		componentService.deleteComponent("1");

	}

}
