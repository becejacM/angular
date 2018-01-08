package org.levi9.controller;

import javax.validation.Valid;

import org.levi9.DTO.ConfigurationDTO;
import org.levi9.exception.MyValidationFormException;
import org.levi9.model.Configuration;
import org.levi9.model.StatusOfConfiguration;
import org.levi9.model.User;
import org.levi9.service.ConfigurationService;
import org.levi9.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author j.ilic REST point for configuration manipulation.
 */
@RestController
@RequestMapping("/api/configurations")
@CrossOrigin
public class ConfigurationController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private UserService userService;

	/**
	 * This method gets configurations that belong to page with certain number of
	 * configurations ( e.g 3 configurations are on one page)
	 * 
	 * @return list of configurations that belong to page
	 */
	@GetMapping(value = "/all")
	private ResponseEntity<Page<Configuration>> getConfigurations(Pageable pageable) {
		return new ResponseEntity<>(this.configurationService.getConfigurations(pageable), HttpStatus.OK);
	}

	/**
	 * This method gets configurations for user that belong to page with certain
	 * number of configurations ( e.g 3 configurations are on one page)
	 * 
	 * @return list of configurations that belong to page
	 */
	@GetMapping(value = "/get_my_configurations/{id:.+}")
	private ResponseEntity<Page<Configuration>> getConfigurationsByUser(@PathVariable Long id, Pageable pageable) {
		return new ResponseEntity<>(this.configurationService.getMyConfigurations(id, pageable), HttpStatus.OK);
	}

	/**
	 * @param configuration
	 *            which should be added
	 * @param result
	 *            where is information if configuration is in valid form
	 * @return configuration which is added This method adds a new configuration
	 */
	@PostMapping()
	public ResponseEntity<Configuration> createConfiguration(@RequestBody @Valid ConfigurationDTO configuration,
			BindingResult result) {
		if (result.hasErrors()) {
			throw new MyValidationFormException(
					"Creating configuration isn't possible because not all parameters are valid");
		}
		Long id = configuration.getUserId();
		User user = userService.findById(id);
		Configuration tryCreate = new Configuration(configuration.getName(), configuration.getComponents(),
				StatusOfConfiguration.CREATED, user);
		Configuration created = configurationService.create(tryCreate, user);

		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	/**
	 * @param configuration
	 *            which should be updated
	 * @param result
	 *            where is information if configuration is in valid form
	 * @return configuration which is updated
	 * @throws ConfigurationNotFoundException
	 *             is custom exception which have message for error This method
	 *             updates an existing configuration
	 */
	@PutMapping
	public ResponseEntity<Configuration> updateConfiguration(@RequestBody @Valid ConfigurationDTO configuration,
			BindingResult result) {
		if (result.hasErrors()) {
			throw new MyValidationFormException("Update configuration failed! Not all parameters are valid");
		}
		Configuration tryUpdate = new Configuration(configuration.getId(), configuration.getName(),
				configuration.getComponents(), configuration.getStatus());
		Configuration updated = configurationService.update(tryUpdate);
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}

	@PutMapping(value = "/check-out")
	public ResponseEntity<Configuration> checkOutConfiguration(@RequestBody @Valid ConfigurationDTO configuration,
			BindingResult result) {
		return new ResponseEntity<>(configurationService.checkOut(configuration), HttpStatus.OK);
	}

	@PutMapping(value = "/pay")
	public ResponseEntity<Configuration> payConfiguration(@RequestBody @Valid ConfigurationDTO configuration,
			BindingResult result) {

		Configuration updated = configurationService.pay(configuration);
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}

	@PutMapping(value = "/deliver")
	public ResponseEntity<Configuration> deliverConfiguration(@RequestBody @Valid ConfigurationDTO configuration,
			BindingResult result) {
		Configuration updated = configurationService.deliver(configuration);
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}

	/**
	 * @param idConf
	 *            is id of configuration which should be deleted
	 * @param id
	 *            is id of user which made configuration which should be deleted
	 * @return configuration which is deleted This method deletes configuration from
	 *         database and from users configurations
	 */
	@DeleteMapping(value = "/{id:.+}/{userId:.+}")
	public ResponseEntity<Configuration> delete(@PathVariable Long id, @PathVariable Long userId) {
		logger.info("> delete conf by id:{}", id);
		Configuration configuration = configurationService.delete(id, userId);

		logger.info("< delete conf by id:{}", id);

		if (configuration == null) {
			Configuration c = null;
			return new ResponseEntity<>(c, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(configuration, HttpStatus.OK);
	}
}
