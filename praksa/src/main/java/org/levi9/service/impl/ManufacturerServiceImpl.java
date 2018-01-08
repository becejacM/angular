package org.levi9.service.impl;

import java.util.List;

import org.levi9.model.Manufacturer;
import org.levi9.repository.ManufacturerRepository;
import org.levi9.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

	@Autowired
	private ManufacturerRepository manufacturerRepository;

	@Override
	public List<Manufacturer> getAll() {
		return manufacturerRepository.findAll();
	}

	@Override
	public Manufacturer create(Manufacturer m) {
		return manufacturerRepository.save(m);
	}
}
