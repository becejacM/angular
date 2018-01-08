package org.levi9.service;

import java.util.List;

import org.levi9.model.Manufacturer;

public interface ManufacturerService {

	public List<Manufacturer> getAll();

	public Manufacturer create(Manufacturer m);
}
