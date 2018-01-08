package org.levi9.service;

import java.util.Collection;

import org.levi9.DTO.ConfigurationDTO;
import org.levi9.model.Configuration;
import org.levi9.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConfigurationService {

	public Page<Configuration> getConfigurations(Pageable pageable);
	
	public Page<Configuration> getMyConfigurations(Long id,Pageable pageable);

	public Configuration create(Configuration configuration, User user);

	public Configuration update(Configuration configuration);

	public Configuration delete(Long confId, Long id);

	public Collection<Configuration> findByUserId(Long id);

	public Configuration findById(Long id);

	public Configuration checkOut(ConfigurationDTO configuration);

	public Configuration pay(ConfigurationDTO configuration);

	public Configuration deliver(ConfigurationDTO configuration);
}
