package org.levi9.service;

import java.util.List;

import org.levi9.model.ComponentType;

public interface TypeService {

	public List<ComponentType> getAllTypes();

	public ComponentType addType(ComponentType newType);

	public ComponentType updateType(ComponentType chosenType);

	public void deleteType(String typeName);

	public List<ComponentType> getRequiredTypes();

}
