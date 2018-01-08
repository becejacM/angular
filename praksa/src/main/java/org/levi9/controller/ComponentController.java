package org.levi9.controller;

import java.util.List;

import javax.validation.Valid;
import org.levi9.exception.MyValidationFormException;
import org.levi9.model.Component;
import org.levi9.service.ComponentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author j.ilic REST point for component manipulation.
 */
@RestController
@RequestMapping(value = "/api/components")
@CrossOrigin
public class ComponentController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ComponentService componentService;

	/**
	 * @param id
	 * @return found component This method searches component by id
	 */

	@GetMapping(value = "/{id}")
	private ResponseEntity<Component> getComponent(@PathVariable Long id) {
		return new ResponseEntity<>(componentService.getComponentByID(id), HttpStatus.OK);
	}

	/**
	 * This method gets components that belong to page with certain number of
	 * components ( e.g 3 components are on one page)
	 * 
	 * @param page
	 * @return list of components that belong to page
	 */
	@GetMapping(value = "/all")
	private ResponseEntity<Page<Component>> getComponents(Pageable pageable) {
		return new ResponseEntity<>(this.componentService.getComponents(pageable), HttpStatus.OK);
	}
	
	/**
	 * This method gets components that belong to page with certain number of
	 * components ( e.g 3 components are on one page)
	 * 
	 * @param page
	 * @return list of components that belong to page
	 */
	@GetMapping(value = "/allComponents")
	private ResponseEntity<List<Component>> getAllComponents() {
		return new ResponseEntity<>(this.componentService.getComponents(), HttpStatus.OK);
	}

	/**
	 * @param component
	 *            which should be added
	 * @param errors
	 *            where is information if component is in valid form
	 * @return component which is added This method adds a new component
	 */
	@PostMapping
	private ResponseEntity<Component> addComponent(@RequestBody @Valid Component component, BindingResult errors) {
		if (errors.hasErrors())
			throw new MyValidationFormException("Adding component isn't possible parameters arn't valid");
		Component com = this.componentService.addComponent(component);
		return new ResponseEntity<>(com, HttpStatus.OK);
	}

	/**
	 * @param component
	 *            which should be updated
	 * @param errors
	 *            where is information if component is in valid form
	 * @return component which is updated
	 * 
	 */
	@PutMapping
	private ResponseEntity<Component> updateComponent(@RequestBody @Valid Component component, BindingResult errors) {
		if (errors.hasErrors())
			throw new MyValidationFormException("Update component isn't possible parameters arn't valid");
		return new ResponseEntity<>(this.componentService.updateComponent(component), HttpStatus.OK);
	}

	/**
	 * @param id
	 *            of component which should be deleted
	 * @return component which is deleted
	 */
	@DeleteMapping
	private ResponseEntity<Component> deleteComponent(@RequestParam(value = "id", required = true) String id) {
		Component component = this.componentService.deleteComponent(id);
		return new ResponseEntity<>(component, HttpStatus.OK);
	}

	/**
	 * @param price
	 *            is in format 1-600 where first number is lower limit and second
	 *            number is upper limit.
	 * @param exist
	 *            is information if component is required or optional
	 * @param type
	 *            is type of component for searching
	 * @return all components that meet all the criteria This method searches
	 *         components on some criteria, based on which criteria is set
	 */
	@GetMapping(value = "/search")
	private Page<Component> searchComponents(Pageable pageable,
			@RequestParam(value = "price", required = false) String price,
			@RequestParam(value = "exist", required = false) String exist,
			@RequestParam(value = "type", required = false) String type) {

		if (price != null && exist != null && type != null) {
			logger.info("all");
			logger.info(price+"   "+type+"   "+exist);
			return this.componentService.searchComponentsByPriceTypeExists(price, type, pageable);
		} else if (price != null && type != null) {
			logger.info("price and type");
			return this.componentService.searchComponentsByPriceType(price, type, pageable);
		} else if (price != null && exist != null) {
			logger.info("price and exist");
			return this.componentService.searchComponentsByPriceExist(price, pageable);
		} else if (exist != null && type != null) {
			logger.info("exist and type");
			return this.componentService.searchComponentsByExistType(type, pageable);
		} else if (price != null) {
			logger.info("price");
			return this.componentService.searchComponentByPrice(price, pageable);
		} else if (type != null) {
			logger.info("type");
			return this.componentService.searchComponentByType(type, pageable);
		} else if (exist != null) {
			logger.info("exist");
			return this.componentService.searchComponentByExist(pageable);
		}
		return getComponents(new PageRequest(0, 10)).getBody();

	}
}
