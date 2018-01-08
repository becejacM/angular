package org.levi9.controller;

import java.util.List;

import javax.validation.Valid;

import org.levi9.exception.MyNotValidParamsException;
import org.levi9.exception.MyValidationFormException;
import org.levi9.model.ComponentType;
import org.levi9.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author m.becejac REST point for componentType manipulation.
 *
 */
@RestController
@RequestMapping(value = "/api/types")
@CrossOrigin
public class TypeController {

	@Autowired
	TypeService typeService;

	/**
	 * Function returns all component types from database
	 * 
	 * @return list of component types
	 */
	@GetMapping(value = "/all")
	private List<ComponentType> getTypes() {
		return this.typeService.getAllTypes();
	}

	/**
	 * Function adds component to database
	 * 
	 * @param type
	 *            - new type which is going to be added
	 * @param errors
	 *            - if there are errors in type parameters
	 * @return - new ResponseEntity<>(newType, HttpStatus.OK)
	 */
	@PostMapping
	private ResponseEntity<ComponentType> addType(@RequestBody @Valid ComponentType type, BindingResult errors) {
		if (errors.hasErrors())
			throw new MyNotValidParamsException("Adding type has failed! Not all parameters are valid");
		ComponentType newType = this.typeService.addType(type);
		return new ResponseEntity<>(newType, HttpStatus.OK);
	}

	/**
	 * Function updates type
	 * 
	 * @param type
	 * @param errors
	 * @return - new ResponseEntity<>(this.typeService.updateType(type),
	 *         HttpStatus.OK)
	 */
	@PutMapping
	private ResponseEntity<ComponentType> updateType(@RequestBody @Valid ComponentType type, BindingResult errors) {
		if (errors.hasErrors())
			throw new MyValidationFormException("Updating type has failed! Not all parameters are valid");
		return new ResponseEntity<>(this.typeService.updateType(type), HttpStatus.OK);
	}

	/**
	 * Function deletes type from database
	 * 
	 * @param typeName
	 * @return - new ResponseEntity<>(HttpStatus.OK)
	 */
	@DeleteMapping
	private ResponseEntity<ComponentType> deleteType(
			@RequestParam(value = "typeName", required = true) String typeName) {
		this.typeService.deleteType(typeName);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = "/get-required-types")
	private ResponseEntity<List<ComponentType>> getRequiredTypes() {
		return new ResponseEntity<>(typeService.getRequiredTypes(), HttpStatus.OK);
	}

}
