package org.levi9.repository;

import org.levi9.model.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ConfigurationRepository extends PagingAndSortingRepository<Configuration, Long> {

	Page<Configuration> findAll(Pageable pageable);

	Page<Configuration> findByUserId(Long id, Pageable page);

}
