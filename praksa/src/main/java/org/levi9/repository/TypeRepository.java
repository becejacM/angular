package org.levi9.repository;

import java.util.List;

import org.levi9.model.ComponentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<ComponentType, Long> {

	ComponentType findByName(String name);

	List<ComponentType> findByRequired(Boolean number);

}
