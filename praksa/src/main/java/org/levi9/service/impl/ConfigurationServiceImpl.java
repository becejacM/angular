package org.levi9.service.impl;


import java.util.Collection;
import java.util.HashMap;

import javax.transaction.Transactional;

import org.levi9.DTO.ConfigurationDTO;
import org.levi9.model.Address;
import org.levi9.model.Configuration;
import org.levi9.model.StatusOfConfiguration;
import org.levi9.model.User;
import org.levi9.repository.AddressRepository;
import org.levi9.exception.MyNotFoundException;
import org.levi9.exception.MySavingInRepoException;
import org.levi9.model.Component;
import org.levi9.repository.ComponentRepository;
import org.levi9.repository.ConfigurationRepository;
import org.levi9.repository.UserRepository;
import org.levi9.service.ConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author m.becejac
 *
 */
@Service
public class ConfigurationServiceImpl implements ConfigurationService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ConfigurationRepository configurationRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private ComponentRepository componentRepository;

	/**
	 * Function returns list of configurations that are on requested page
	 * 
	 * @return list of configurations on requested page
	 */
	@Override
	public Page<Configuration> getConfigurations(Pageable pageable) {
		return configurationRepository.findAll(pageable);
	}

	/**
	 * Function returns list of configurations for user that are on requested page
	 * 
	 * @return list of configurations on requested page
	 */
	@Override
	public Page<Configuration> getMyConfigurations(Long id, Pageable pageable) {
		return configurationRepository.findByUserId(id, pageable);
	}

	/**
	 * Function creates new configuration and save it to database
	 * 
	 * @param configuration
	 *            - Configuration that is going to be saved in database
	 * @param idC
	 *            - id of user who wants to create configuration
	 * @return saved configuration
	 */
	@Override
	public Configuration create(final Configuration configuration, final User u) {
		try {
			if (configuration == null || configuration.getComponents().size() == 0
					|| configuration.getName().equals("")) {
				throw new MyNotFoundException("Configuration not found");
			}
			for (Component component : configuration.getComponents()) {
				// component.setQuantity(component.getQuantity() - 1);
				componentRepository.save(component);
			}
			configuration.setConfigurationStatus(StatusOfConfiguration.CREATED);
			Configuration conf = configurationRepository.save(configuration);
			if (conf == null) {
				throw new MySavingInRepoException("Saving configuration in repository failed");
			}
			u.getConfigurations().add(configuration);

			User user = userRepository.save(u);
			if (user == null) {
				throw new MySavingInRepoException("Saving user in repository failed");
			}
			return conf;
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Function that update requested configuration and save it to database
	 * 
	 * @param configuration
	 *            - Configuration that is going to be updated
	 * @return saved configuration
	 * @throws ConfigurationNotFoundException
	 *             - If Configuration with requested configuration id doesn't exists
	 *             in database
	 */
	@Override
	public Configuration update(Configuration configuration) {
		try {
			if (configuration == null || configuration.getComponents().size() == 0
					|| configuration.getName().equals("")) {
				throw new MyNotFoundException("Configuration not found");
			}
			Configuration c = configurationRepository.findOne(configuration.getId());
			if (c == null) {
				throw new MyNotFoundException("Configuration with id : " + configuration.getId() + " is not found");
			}
			HashMap<Long, Integer> exists = new HashMap<>();
			for (Component comp : configuration.getComponents()) {
				componentRepository.save(comp);
			}
			for (Component comp : c.getComponents()) {
				if(exists.containsKey(comp.getId()))
					continue;
				exists.put(comp.getId(), 1);
				int inBaseAppearances = 0;
				int newAppearances = 0;
				for (Component currCompBase : c.getComponents()) {
					if (comp.getId() == currCompBase.getId())
						inBaseAppearances++;
				}
				for (Component currCompNew : configuration.getComponents()) {

					if (currCompNew.getId() == comp.getId()) {
						newAppearances++;
					}
				}
				if (inBaseAppearances > newAppearances) {
					int i = comp.getQuantity()+(inBaseAppearances-newAppearances);
					comp.setQuantity(i);
					componentRepository.save(comp);
				}
			}
			
			c.setName(configuration.getName());
			c.setComponents(configuration.getComponents());
			Configuration conf = configurationRepository.save(c);
			if (conf == null) {
				throw new MySavingInRepoException("Saving configuration in repository failed");
			}
			return conf;
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Function that delete Component from database
	 * 
	 * @param confId
	 *            - id of configuration that should be deleted
	 * @param id
	 *            - id of user who wants to delete configuration
	 * @return deleted configuration
	 */
	@Override
	@Transactional
	public Configuration delete(Long idconf, Long id) {
		try {
			User u = userRepository.findOne(id);
			if (u == null) {
				throw new MyNotFoundException("User with id : " + id + " not found");
			}
			for (Configuration c : u.getConfigurations()) {
				if (c.getId() == idconf) {
					u.getConfigurations().remove(c);
					break;
				}
			}
			Configuration conf = configurationRepository.findOne(idconf);
			if (conf == null) {
				throw new MyNotFoundException("Configuration is not found");
			}
			configurationRepository.delete(conf);
			userRepository.save(u);
			return conf;
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			throw new IllegalArgumentException();
		}

	}

	/**
	 * 
	 * * Function that check if configuration with requested id of user exists in
	 * database and if it does, returns it
	 * 
	 * @param id
	 *            - requested id of user
	 * @return Configuration with id:id
	 */
	@Override
	public Collection<Configuration> findByUserId(Long id) {
		if (id == null)
			throw new MyNotFoundException("Id can't be null");
		User c = userRepository.findOne(id);
		if (c == null) {
			throw new MyNotFoundException("Configuration not found");
		}
		return c.getConfigurations();
	}

	/**
	 * 
	 * * Function that check if configuration with requested id exists in database
	 * and if it does, returns it
	 * 
	 * @param id
	 *            - requested id of configuration
	 * @return Configuration with id:id
	 */
	@Override
	public Configuration findById(Long id) {
		Configuration c = configurationRepository.findOne(id);
		if (c == null) {
			throw new MyNotFoundException("Configuration not found");
		}
		return c;
	}

	@Override
	public Configuration checkOut(ConfigurationDTO configuration) {
		try {
			Configuration c = configurationRepository.findOne(configuration.getId());
			if (c == null) {
				throw new MyNotFoundException("Configuration not found");
			}
			Address deliveryAddress = addressRepository.findByStreetAndCityAndZipCodeAndCountry(
					configuration.getDeliverAddress().getStreet(), configuration.getDeliverAddress().getCity(),
					configuration.getDeliverAddress().getZipCode(), configuration.getDeliverAddress().getCountry());
			Address payAddress = addressRepository.findByStreetAndCityAndZipCodeAndCountry(
					configuration.getPayAddress().getStreet(), configuration.getPayAddress().getCity(),
					configuration.getPayAddress().getZipCode(), configuration.getPayAddress().getCountry());
			if (deliveryAddress == null)
				deliveryAddress = addressRepository.save(configuration.getDeliverAddress());
			if (payAddress == null)
				payAddress = addressRepository.save(configuration.getPayAddress());
			c.setDeliverAddress(deliveryAddress);
			c.setPayAddress(payAddress);
			c.setMethodOfPayment(configuration.getMethodOfPayment());
			c.setConfigurationStatus(StatusOfConfiguration.CHECKEDOUT);
			Configuration conf = configurationRepository.save(c);
			if (conf == null)
				throw new MySavingInRepoException("Saving configuration in repository failed");

			return conf;
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Configuration pay(ConfigurationDTO configuration) {
		try {
			Configuration c = configurationRepository.findOne(configuration.getId());
			if (c == null) {
				throw new MyNotFoundException("Configuration not found");
			}
			c.setConfigurationStatus(StatusOfConfiguration.PAID);
			Configuration conf = configurationRepository.save(c);
			if (conf == null)
				throw new MySavingInRepoException("Saving configuration in repository failed");
			return conf;
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Configuration deliver(ConfigurationDTO configuration) {
		try {
			Configuration c = configurationRepository.findOne(configuration.getId());
			if (c == null) {
				throw new MyNotFoundException("Configuration not found");
			}
			c.setConfigurationStatus(StatusOfConfiguration.DELIVERED);
			Configuration conf = configurationRepository.save(c);
			if (conf == null)
				throw new MySavingInRepoException("Saving configuration in repository failed");
			return conf;
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			throw new IllegalArgumentException();
		}
	}

}
