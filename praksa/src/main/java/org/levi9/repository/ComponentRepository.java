package org.levi9.repository;


import java.util.List;

import org.levi9.model.Component;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ComponentRepository extends PagingAndSortingRepository<Component, Long> {

	Component findByName(String name);

	Page<Component> findByQuantityGreaterThan(Integer qunatity, Pageable pageable);

	Page<Component> findByTypeName(String name, Pageable pageable);

	Page<Component> findByPriceBetween(Double first, Double second, Pageable pageable);

	Page<Component> findByQuantityGreaterThanAndTypeName(Integer quantity, String name, Pageable pageable);

	Page<Component> findByQuantityGreaterThanAndPriceBetween(Integer quantity, Double first, Double second,
			Pageable pageable);

	Page<Component> findByTypeNameAndPriceBetween(String name, Double first, Double second, Pageable pageable);

	Page<Component> findByTypeNameAndPriceBetweenAndQuantityGreaterThan(String name, Double first, Double second,
			Integer quantity, Pageable pageable);

	Page<Component> findAll(Pageable pageable);
	
	List<Component> findAll();
	
}
