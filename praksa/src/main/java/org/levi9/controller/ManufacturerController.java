package org.levi9.controller;

import java.util.List;
import javax.validation.Valid;

import org.levi9.DTO.ManufacturerDTO;
import org.levi9.exception.MyValidationFormException;
import org.levi9.model.Manufacturer;
import org.levi9.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author m.becejac
 *
 */
@RestController
@RequestMapping(value = "/api/manufacturers")
@CrossOrigin
public class ManufacturerController {

	@Autowired
	private ManufacturerService manufacturerService;

	/**
	 * Function returns all manufacturers from database
	 * 
	 * @return list of manufacturers types
	 */
	@GetMapping(value = "/all")
	private List<Manufacturer> getManufacturers() {
		return manufacturerService.getAll();
	}

	@PostMapping()
	public ResponseEntity<Manufacturer> createManufacturer(@RequestBody @Valid ManufacturerDTO manufacturer,
			BindingResult result) {
		if (result.hasErrors()) {
			throw new MyValidationFormException(
					"Creating manufacturer isn't possible because not all parameters are valid");
		}
		Manufacturer tryCreate = new Manufacturer(manufacturer.getManufacturerName());
		Manufacturer created = manufacturerService.create(tryCreate);

		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}
}
