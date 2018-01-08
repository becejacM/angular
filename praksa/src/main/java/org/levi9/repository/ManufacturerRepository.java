package org.levi9.repository;

import org.levi9.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

	Manufacturer findByName(String name);
}