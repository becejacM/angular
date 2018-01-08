package org.levi9.service;

import java.util.List;

import org.levi9.model.Component;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ComponentService {

	Component getComponentByID(Long id);

	Page<Component> getComponents(Pageable pageable);

	List<Component> getComponents();

	Page<Component> searchComponentsByPriceTypeExists(String price, String type, Pageable pageable);

	Page<Component> searchComponentsByPriceType(String price, String type, Pageable pageable);

	Page<Component> searchComponentsByPriceExist(String price, Pageable pageable);

	Page<Component> searchComponentsByExistType(String type, Pageable pageable);

	Page<Component> searchComponentByPrice(String price, Pageable pageable);

	Page<Component> searchComponentByType(String type, Pageable pageable);

	Page<Component> searchComponentByExist(Pageable pageable);

	Component addComponent(Component component);

	Component updateComponent(Component component);

	Component deleteComponent(String id);

	Component findById(Long id);

}
