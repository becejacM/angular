package org.levi9.service.impl;

import java.util.List;

import org.levi9.exception.MyNotFoundException;
import org.levi9.exception.MyQuantityNotZeroException;
import org.levi9.exception.MySavingInRepoException;
import org.levi9.model.Component;
import org.levi9.model.Manufacturer;
import org.levi9.model.ComponentType;
import org.levi9.repository.ComponentRepository;
import org.levi9.repository.ManufacturerRepository;
import org.levi9.repository.TypeRepository;
import org.levi9.service.ComponentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ComponentServiceImpl implements ComponentService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ComponentRepository componentRepository;

	@Autowired
	private TypeRepository typeRepository;

	@Autowired
	private ManufacturerRepository manufacturerRepository;

	/**
	 * Function that check if component with requested id exists in database and if
	 * it does, returns it
	 * 
	 * @param id
	 *            - requested id of component
	 * @return Component with id:id
	 */
	@Override
	public Component getComponentByID(Long id) {
		return componentRepository.findOne(id);
	}

	/**
	 * Function returns all components that exists in database
	 * 
	 * @return list of components
	 */
	@Override
	public Page<Component> getComponents(Pageable pageable) {
		return componentRepository.findAll(pageable);
	}

	/**
	 * Function that returns all components 
	 * 
	
	 * @return List of components 
	 */
	
	/**
	 * Function returns all components that exists in database
	 * 
	 * @return list of components
	 */
	@Override
	public List<Component> getComponents() {
		return componentRepository.findAll();
	}

	/**
	 * Function that returns all components with matching parameters and have
	 * quantity larger than 0
	 * 
	 * @param price
	 *            - String format of price in format (a-b)
	 * @param type
	 *            - Name of component type
	 * @return List of components that matches parameters
	 */
	@Override
	public Component findById(Long id) {
		return componentRepository.findOne(id);
	}

	/**
	 * Function that returns all components with matching parameters
	 * 
	 * @param price
	 *            - String format of price in format (a-b)
	 * @param type
	 *            - Name of component type
	 * @return List of components that matches parameters
	 */
	@Override
	public Page<Component> searchComponentsByPriceTypeExists(String price, String type, Pageable pageable) {
		double[] limits = parse(price);
		return componentRepository.findByTypeNameAndPriceBetweenAndQuantityGreaterThan(type, limits[0], limits[1], 0,
				pageable);
	}

	/**
	 * Function that returns all components with matching parameters and have
	 * quantity larger than 0
	 * 
	 * @param price
	 *            - String format of price in format (a-b)
	 * @return List of components that matches parameters
	 */
	@Override
	public Page<Component> searchComponentsByPriceType(String price, String type, Pageable pageable) {
		double[] limits = parse(price);
		return componentRepository.findByTypeNameAndPriceBetween(type, limits[0], limits[1], pageable);
	}

	/**
	 * Function that returns all components with quantity larger than 0
	 * 
	 * @param type
	 *            - Name of component type
	 * @return List of components that matches parameters
	 */
	@Override
	public Page<Component> searchComponentsByPriceExist(String price, Pageable pageable) {
		double[] limits = parse(price);
		return componentRepository.findByQuantityGreaterThanAndPriceBetween(0, limits[0], limits[1], pageable);
	}

	/**
	 * Function that returns all components with matching parameters and have
	 * quantity larger than 0
	 * 
	 * @param type
	 *            - Name of component type
	 * @return List of components that matches parameters
	 */
	@Override
	public Page<Component> searchComponentsByExistType(String type, Pageable pageable) {
		return componentRepository.findByQuantityGreaterThanAndTypeName(0, type, pageable);
	}

	/**
	 * Function that returns all components with matching parameters
	 * 
	 * @param price
	 *            - String format of price in format (a-b)
	 * @return List of components that matches parameters
	 */
	@Override
	public Page<Component> searchComponentByPrice(String price, Pageable pageable) {
		double[] limits = parse(price);
		return componentRepository.findByPriceBetween(limits[0], limits[1], pageable);
	}

	/**
	 * Function that returns all components with matching parameters
	 * 
	 * @param type
	 *            - Name of component type
	 * @return List of components that matches parameters
	 */
	@Override
	public Page<Component> searchComponentByType(String type, Pageable pageable) {
		return componentRepository.findByTypeName(type, pageable);
	}

	/**
	 * Function that returns all components that have quantity larger than 0
	 * 
	 * @return List of components that matches parameters
	 */
	@Override
	public Page<Component> searchComponentByExist(Pageable pageable) {
		return componentRepository.findByQuantityGreaterThan(0, pageable);
	}

	/**
	 * Function that parse string price range (a-b)
	 * 
	 * @param price
	 *            - price range (a-b)
	 * @return list[a,b]
	 */
	private double[] parse(String price) {
		String[] limits = price.split("-");
		double[] parsed = { 0, 1000000 };
		parsed[0] = Double.parseDouble(limits[0]);
		parsed[1] = Double.parseDouble(limits[1]);
		return parsed;
	}

	/**
	 * Function that create new component and save it to database
	 * 
	 * @param component
	 *            - Component that is going to be saved in database
	 * @return saved component
	 */
	@Override
	public Component addComponent(Component component) {
		try {
			Manufacturer manufacturer = this.manufacturerRepository.findByName(component.getManufacturer().getName());
			if (manufacturer == null)
				throw new MyNotFoundException();
			component.setActiveStatus(true);
			component.setManufacturer(manufacturer);
			ComponentType type = this.typeRepository.findByName(component.getType().getName());
			if (type == null)
				throw new MyNotFoundException("Type with name " + component.getType().getName() + " is not found");

			component.setType(type);

			Component comp = this.componentRepository.save(component);
			if (comp == null)
				throw new MySavingInRepoException("Saving component failed due to component repository error");
			return comp;
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Function that update requested component and save it to database
	 * 
	 * @param component
	 *            - Component that is going to be updated
	 * @return saved component
	 * @throws MyNotFoundException
	 *             - If Component with requested id component.id doesn't exists in
	 *             database
	 */
	@Override
	public Component updateComponent(Component component) {
		try {
			Component comp = this.componentRepository.findOne(component.getId());
			if (comp == null)
				throw new MyNotFoundException("Component with id " + component.getId() + " not found");
			Manufacturer manu = this.manufacturerRepository.findByName(component.getManufacturer().getName());
			if (manu == null)
				throw new MyNotFoundException();
			ComponentType type = this.typeRepository.findByName(component.getType().getName());
			if (type == null)
				throw new MyNotFoundException("Type with name " + component.getType().getName() + " is not found");
			comp.setName(component.getName());
			comp.setPrice(component.getPrice());
			comp.setManufacturer(manu);
			comp.setQuantity(component.getQuantity());
			comp.setType(type);
			this.componentRepository.save(comp);
			return comp;
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Function that delete Component from database
	 * 
	 * @param id
	 *            - id of component that should be deleted
	 * @throws MyNotFoundException
	 *             - if Component with requested id:id doesn't exists in database
	 * @throws MyQuantityNotZeroException
	 *             - if quantity of Component with id:id is larger than 0
	 */
	@Override
	public Component deleteComponent(String id) {
		try {
			Long longId = Long.parseLong(id);
			Component component = this.componentRepository.findOne(longId);
			if (component == null)
				throw new MyNotFoundException("Component not found");
			if (component.getQuantity() == 0) {
				if (component.isActiveStatus()) {
					component.setActiveStatus(false);
					this.componentRepository.save(component);
				} else {
					component.setActiveStatus(true);
					this.componentRepository.save(component);
				}

				return component;
			}else
				throw new MyQuantityNotZeroException(
					"Component " + component.getName() + " has more then 0 items in storage !");

		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			throw new IllegalArgumentException();
		}
	}

}