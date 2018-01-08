package org.levi9.service.impl;

import java.util.List;

import org.levi9.exception.MyAlreadyExistsException;
import org.levi9.exception.MyNotFoundException;
import org.levi9.exception.MySavingInRepoException;
import org.levi9.model.ComponentType;
import org.levi9.repository.TypeRepository;
import org.levi9.service.TypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeServiceImpl implements TypeService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TypeRepository typeRepository;

	@Override
	public List<ComponentType> getAllTypes() {
		return this.typeRepository.findAll();
	}

	@Override
	public ComponentType addType(ComponentType newType) {
		try {
			if (this.typeRepository.findByName(newType.getName()) != null) {
				throw new MyAlreadyExistsException("Type " + newType.getName() + " already exists");
			}
			ComponentType type = this.typeRepository.save(newType);
			return type;
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			throw new IllegalArgumentException();
		}
	}

	@Override
	public ComponentType updateType(ComponentType chosenType) {
		try {
			ComponentType forChange = this.typeRepository.findByName(chosenType.getName());
			if (forChange == null)
				throw new MyNotFoundException("Type " + chosenType.getName() + " doesn't exists");
			forChange.setCardinality(chosenType.getCardinality());
			ComponentType type = this.typeRepository.save(forChange);
			if (type == null)
				throw new MySavingInRepoException("Saving type in repository failed");
			return type;
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void deleteType(String typeName) {
		try {
			ComponentType forDelete = this.typeRepository.findByName(typeName);
			if (forDelete == null)
				throw new MyNotFoundException("Type " + typeName + " doesn't exists");
			this.typeRepository.delete(forDelete);
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			throw new IllegalArgumentException();
		}
	}

	@Override
	public List<ComponentType> getRequiredTypes() {
		return typeRepository.findByRequired(true);

	}

}
