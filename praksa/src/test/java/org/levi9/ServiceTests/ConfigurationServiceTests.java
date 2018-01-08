package org.levi9.ServiceTests;


import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.levi9.DTO.ConfigurationDTO;
import org.levi9.exception.MyNotFoundException;
import org.levi9.exception.MySavingInRepoException;
import org.levi9.model.Address;
import org.levi9.model.Component;
import org.levi9.model.ComponentType;
import org.levi9.model.Configuration;
import org.levi9.model.User;
import org.levi9.repository.AddressRepository;
import org.levi9.repository.ComponentRepository;
import org.levi9.repository.ConfigurationRepository;
import org.levi9.repository.UserRepository;
import org.levi9.model.Manufacturer;
import org.levi9.service.ConfigurationService;
import org.levi9.service.impl.ConfigurationServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

/**
 * Test class for {@link ConfigurationService}
 * 
 * @author m.becejac
 *
 */
public class ConfigurationServiceTests {

	@Mock
	private ConfigurationRepository configurationRepository;

	@Mock
	private ComponentRepository componentRepository;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private ConfigurationServiceImpl configurationService;
	
	@Mock
	private AddressRepository addressRepository;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetAll() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		componentSet.add(component);
		
		final Configuration configuration = new Configuration(componentSet, "someConfiguration");
		List<Configuration> confs = new ArrayList<Configuration>();
		confs.add(configuration);
		PageRequest pageRequest = new PageRequest(0,10);
		Page<Configuration> returnPage = new PageImpl<Configuration>(confs, pageRequest, 2);

		when(configurationRepository.findAll(pageRequest)).thenReturn(returnPage);
		configurationService.getConfigurations(pageRequest);
		verify(configurationRepository).findAll(pageRequest);
	}
	
	@Test
	public void testGetMyConfiguration() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		componentSet.add(component);
		
		final Configuration configuration = new Configuration(componentSet, "someConfiguration");
		List<Configuration> confs = new ArrayList<Configuration>();
		confs.add(configuration);
		PageRequest pageRequest = new PageRequest(0,10);
		Page<Configuration> returnPage = new PageImpl<Configuration>(confs, pageRequest, 2);

		when(configurationRepository.findAll(pageRequest)).thenReturn(returnPage);
		configurationService.getMyConfigurations(1l,pageRequest);
		verify(configurationRepository).findByUserId(1l,pageRequest);
	}
 
	@Test
	public void testCreatePass() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		componentSet.add(component);
		final Configuration configuration = new Configuration(componentSet, "someConfiguration");
		final User user = new User(1L, "Pera Peric", "pera@live.com", "pw", "USER");
		when(componentRepository.save(any(Component.class))).thenReturn(component);
		when(configurationRepository.save(any(Configuration.class))).thenReturn(configuration);
		when(userRepository.save(any(User.class))).thenReturn(new User());
		final Configuration savedConfiguration = configurationService.create(configuration, user);

		verify(configurationRepository).save(any(Configuration.class));
		verify(userRepository).save(any(User.class));
		assertTrue(savedConfiguration.getComponents().size() > 0);
		assertTrue(savedConfiguration.getName().equals("someConfiguration"));
	}

	@Test
	public void testCreateConfigurationNull() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		componentSet.add(component);
		final User user = new User(1L, "Pera Peric", "pera@live.com", "pw", "USER");
		exception.expect(MyNotFoundException.class);
		configurationService.create(null, user);
	}

	@Test
	public void testCreateDoesNotExistComponents() {
		List<Component> componentSet = new ArrayList<>();
		final Configuration configuration = new Configuration(componentSet, "someConfiguration");
		final User user = new User(1L, "Pera Peric", "pera@live.com", "pw", "USER");
		exception.expect(MyNotFoundException.class);
		configurationService.create(configuration, user);
	}

	@Test
	public void testCreateConfigurationDoesNotHaveName() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		componentSet.add(component);
		final Configuration configuration = new Configuration(componentSet, "");

		final User user = new User(1L, "Pera Peric", "pera@live.com", "pw", "USER");
		exception.expect(MyNotFoundException.class);
	    configurationService.create(configuration, user);
	}

	@Test
	public void testCreateSaveConfigurationNull() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		componentSet.add(component);
		final Configuration configuration = new Configuration(componentSet, "someConfiguration");
		final User user = new User(1L, "Pera Peric", "pera@live.com", "pw", "USER");
		when(componentRepository.save(any(Component.class))).thenReturn(component);
		when(configurationRepository.save(any(Configuration.class))).thenReturn(null);
		when(userRepository.save(any(User.class))).thenReturn(new User());
		exception.expect(MySavingInRepoException.class);
		configurationService.create(configuration, user);
	}

	@Test
	public void testCreateSaveUserNull() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		componentSet.add(component);
		final Configuration configuration = new Configuration(componentSet, "Configuration");
		final User user = new User(1L, "Pera Peric", "pera@live.com", "pw", "USER");
		when(componentRepository.save(any(Component.class))).thenReturn(component);
		when(configurationRepository.save(any(Configuration.class))).thenReturn(configuration);
		when(userRepository.save(any(User.class))).thenReturn(null);
		exception.expect(MySavingInRepoException.class);
		configurationService.create(configuration, user);
	}

	@Test
	public void testUpdatePass() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		componentSet.add(component);
		final Configuration configuration = new Configuration(componentSet, "someConfiguration");

		when(configurationRepository.findOne(any(Long.class))).thenReturn(configuration);
		when(componentRepository.save(any(Component.class))).thenReturn(component);
		when(configurationRepository.save(any(Configuration.class))).thenReturn(configuration);
		final Configuration updatedConfiguration = configurationService.update(configuration);

		verify(configurationRepository).findOne(any(Long.class));
		verify(configurationRepository).save(any(Configuration.class));
		assertTrue(updatedConfiguration.getComponents().size() > 0);
		assertTrue(updatedConfiguration.getName().equals("someConfiguration"));
	}
	@Test
	public void testUpdatePassWithNewComponents() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		componentSet.add(component);
		
		List<Component> newComponentSet = new ArrayList<>();
		final Component newComponent = new Component();
		newComponent.setId(2L);

		newComponentSet.add(newComponent);
		
		final Configuration configuration = new Configuration(componentSet, "someConfiguration");

		when(configurationRepository.findOne(any(Long.class))).thenReturn(configuration);
		when(componentRepository.save(any(Component.class))).thenReturn(newComponent);
		when(componentRepository.save(any(Component.class))).thenReturn(component);
		Configuration conf = new Configuration(newComponentSet, "someConfiguration");
		when(configurationRepository.save(any(Configuration.class))).thenReturn(configuration);
		final Configuration updatedConfiguration = configurationService.update(conf);

		verify(configurationRepository).findOne(any(Long.class));
		verify(configurationRepository).save(any(Configuration.class));
		assertTrue(updatedConfiguration.getName().equals("someConfiguration"));
	}

	@Test
	public void testUpdateConfigurationNull() {
		exception.expect(MyNotFoundException.class);
		configurationService.update(null);
	}

	@Test
	public void testUpdateComponentsDoNotExist() {
		List<Component> componentSet = new ArrayList<>();
		final Configuration configuration = new Configuration(componentSet, "someConfiguration");
		exception.expect(MyNotFoundException.class);
		configurationService.update(configuration);
	}

	@Test
	public void testUpdateNameDoesNotExist() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		componentSet.add(component);
		final Configuration configuration = new Configuration(componentSet, "");
		exception.expect(MyNotFoundException.class);
		configurationService.update(configuration);
	}

	@Test
	public void testUpdateFindConfigurationIsNull() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		componentSet.add(component);
		final Configuration configuration = new Configuration(componentSet, "someConfiguration");
		when(configurationRepository.findOne(any(Long.class))).thenReturn(null);
		exception.expect(MyNotFoundException.class);
		configurationService.update(configuration);

	}

	@Test
	public void testUpdateSaveConfigurationIsNull() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		componentSet.add(component);
		final Configuration configuration = new Configuration(componentSet, "someConfiguration");
		when(configurationRepository.findOne(any(Long.class))).thenReturn(configuration);
		when(configurationRepository.save(any(Configuration.class))).thenReturn(null);
		exception.expect(MySavingInRepoException.class);
		configurationService.update(configuration);
	}

	@Test
	public void testDeletePass() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		componentSet.add(component);
		final Configuration configuration = new Configuration(componentSet, "someConfiguration");
		final User user = new User(1L, "Pera Peric", "pera@live.com", "pw", "ADMIN");

		when(userRepository.findOne(any(Long.class))).thenReturn(user);
		when(configurationRepository.findOne(any(Long.class))).thenReturn(configuration);
		when(userRepository.save(any(User.class))).thenReturn(user);

		final Configuration deletedConfiguration = configurationService.delete(1L, 1L);
		verify(userRepository).findOne(any(Long.class));
		verify(configurationRepository).findOne(any(Long.class));
		verify(userRepository).save(any(User.class));
		assertTrue(deletedConfiguration.getComponents().size() > 0);
		assertTrue(deletedConfiguration.getName().equals("someConfiguration"));
	}

	@Test
	public void testDeleteUserNotFound() {
		when(userRepository.findOne(any(Long.class))).thenReturn(null);
		exception.expect(MyNotFoundException.class);
	    configurationService.delete(1L, 1L);
	}

	@Test
	public void testDeleteConfigurationNotFound() {
		final User user = new User(1L, "Pera Peric", "pera@live.com", "pw", "ADMIN");
		when(userRepository.findOne(any(Long.class))).thenReturn(user);
		when(configurationRepository.findOne(any(Long.class))).thenReturn(null);
		exception.expect(MyNotFoundException.class);
		configurationService.delete(1L, 1L);
	}

	@Test
	public void testFindByUserIdPass() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		componentSet.add(component);
		final Configuration configuration = new Configuration(componentSet, "someConfiguration");
		final User User = new User(1L, "Pera Peric", "pera@live.com", "pw", "USER");
		User.getConfigurations().add(configuration);
		when(userRepository.findOne(any(Long.class))).thenReturn(User);
		final Collection<Configuration> configurations = configurationService.findByUserId(1L);

		verify(userRepository).findOne(any(Long.class));
		assertTrue(configurations.size() > 0);
	}

	@Test
	public void testFindByUserIdNull() {
		exception.expect(MyNotFoundException.class);
		configurationService.findByUserId(null);
	}

	@Test
	public void testFindByUserIdUserNotFound() {
		when(userRepository.findOne(any(Long.class))).thenReturn(null);
		exception.expect(MyNotFoundException.class);
	    configurationService.findByUserId(1L);
	}

	@Test
	public void testFindByIdPass() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		componentSet.add(component);
		final Configuration configuration = new Configuration(componentSet, "someConfiguration");

		when(configurationRepository.findOne(any(Long.class))).thenReturn(configuration);
		final Configuration conf = configurationService.findById(1L);

		verify(configurationRepository).findOne(any(Long.class));
		assertTrue(conf.getName().equals("someConfiguration"));
	}

	@Test
	public void testFindByIdConfigurationNull() {
		when(configurationRepository.findOne(any(Long.class))).thenReturn(null);
		exception.expect(MyNotFoundException.class);
		configurationService.findById(1L);
	}

	@Test
	public void testCheckOutPass() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		componentSet.add(component);
		final Configuration configuration = new Configuration(componentSet,"New configuraton");
		final ConfigurationDTO confDTO = new ConfigurationDTO("New configuraton", componentSet, 1l);
		confDTO.setDeliverAddress(new Address());
		confDTO.setPayAddress(new Address());
		when(configurationRepository.findOne(any(Long.class))).thenReturn(configuration);
		when(addressRepository.findByStreetAndCityAndZipCodeAndCountry("","",1,"")).thenReturn(new Address());
		when(addressRepository.findByStreetAndCityAndZipCodeAndCountry("","",1,"")).thenReturn(new Address());
		when(configurationRepository.save(any(Configuration.class))).thenReturn(configuration);
		Configuration returnedConf = configurationService.checkOut(confDTO);
		assertTrue(returnedConf.getName().equals("New configuraton"));
	}
	
	@Test
	public void testCheckOutConfigurationNotFound() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		componentSet.add(component);
		final ConfigurationDTO confDTO = new ConfigurationDTO("New configuraton", componentSet, 1l);

		when(configurationRepository.findOne(any(Long.class))).thenReturn(null);
		exception.expect(MyNotFoundException.class);
		configurationService.checkOut(confDTO);
	}
	
	@Test
	public void testCheckOutAddressNull() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		componentSet.add(component);
		final Configuration configuration = new Configuration(componentSet,"New configuraton");
		final ConfigurationDTO confDTO = new ConfigurationDTO("New configuraton", componentSet, 1l);
		confDTO.setDeliverAddress(new Address());
		confDTO.setPayAddress(new Address());
		when(configurationRepository.findOne(any(Long.class))).thenReturn(configuration);
		when(addressRepository.findByStreetAndCityAndZipCodeAndCountry("","",1,"")).thenReturn(null);
		when(addressRepository.findByStreetAndCityAndZipCodeAndCountry("","",1,"")).thenReturn(new Address());
		when(addressRepository.save(any(Address.class))).thenReturn(new Address());
		when(configurationRepository.save(any(Configuration.class))).thenReturn(configuration);

		Configuration returnedConf = configurationService.checkOut(confDTO);
		assertTrue(returnedConf.getName().equals("New configuraton"));
	}
	
	@Test
	public void testCheckOutSaveConfNull() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		componentSet.add(component);
		final Configuration configuration = new Configuration(componentSet,"New configuraton");
		final ConfigurationDTO confDTO = new ConfigurationDTO("New configuraton", componentSet, 1l);
		confDTO.setDeliverAddress(new Address());
		confDTO.setPayAddress(new Address());
		when(configurationRepository.findOne(any(Long.class))).thenReturn(configuration);
		when(addressRepository.findByStreetAndCityAndZipCodeAndCountry("","",1,"")).thenReturn(null);
		when(addressRepository.findByStreetAndCityAndZipCodeAndCountry("","",1,"")).thenReturn(new Address());
		when(configurationRepository.save(any(Configuration.class))).thenReturn(null);
		exception.expect(MySavingInRepoException.class);
		configurationService.checkOut(confDTO);
	}
	
	@Test
	public void testPayPass() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		componentSet.add(component);
		final Configuration configuration = new Configuration(componentSet,"New configuraton");
		final ConfigurationDTO confDTO = new ConfigurationDTO("New configuraton", componentSet, 1l);

		when(configurationRepository.findOne(any(Long.class))).thenReturn(configuration);
		when(configurationRepository.save(any(Configuration.class))).thenReturn(configuration);
		
		Configuration returnedConf = configurationService.pay(confDTO);
		assertTrue(returnedConf.getName().equals("New configuraton"));
	}
	
	@Test
	public void testPayConfigurationNotFound() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		componentSet.add(component);
		final ConfigurationDTO confDTO = new ConfigurationDTO("New configuraton", componentSet, 1l);
		when(configurationRepository.findOne(any(Long.class))).thenReturn(null);
		exception.expect(MyNotFoundException.class);
		configurationService.pay(confDTO);
	}
	
	@Test
	public void testPayConfigurationSaveNull() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		componentSet.add(component);
		final ConfigurationDTO confDTO = new ConfigurationDTO("New configuraton", componentSet, 1l);
		when(configurationRepository.findOne(any(Long.class))).thenReturn(new Configuration());
		when(configurationRepository.save(any(Configuration.class))).thenReturn(null);
		exception.expect(MySavingInRepoException.class);
		configurationService.pay(confDTO);
	}
	
	@Test
	public void testDeliverPass() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		componentSet.add(component);
		final Configuration configuration = new Configuration(componentSet,"New configuraton");
		final ConfigurationDTO confDTO = new ConfigurationDTO("New configuraton", componentSet, 1l);

		when(configurationRepository.findOne(any(Long.class))).thenReturn(configuration);
		when(configurationRepository.save(any(Configuration.class))).thenReturn(configuration);
		
		Configuration returnedConf = configurationService.deliver(confDTO);
		assertTrue(returnedConf.getName().equals("New configuraton"));
	}
	
	@Test
	public void testDeliverConfigurationNotFound() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		componentSet.add(component);
		final ConfigurationDTO confDTO = new ConfigurationDTO("New configuraton", componentSet, 1l);

		when(configurationRepository.findOne(any(Long.class))).thenReturn(null);
		exception.expect(MyNotFoundException.class);
		configurationService.deliver(confDTO);
	}
	
	@Test
	public void testDeliverConfigurationSaveNull() {
		List<Component> componentSet = new ArrayList<>();
		final Component component = new Component("someComponent", 1L, new ComponentType("TypeName", true, "1:n"),
				new Manufacturer(), new Integer(32), new Double(33), true);
		componentSet.add(component);
		final ConfigurationDTO confDTO = new ConfigurationDTO("New configuraton", componentSet, 1l);

		when(configurationRepository.findOne(any(Long.class))).thenReturn(new Configuration());
		when(configurationRepository.save(any(Configuration.class))).thenReturn(null);
		exception.expect(MySavingInRepoException.class);
		configurationService.deliver(confDTO);
	}
	
	

}
