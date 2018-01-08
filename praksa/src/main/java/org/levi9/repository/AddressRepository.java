package org.levi9.repository;

import org.levi9.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository  extends JpaRepository<Address, Long>{

	
	Address findByStreetAndCityAndZipCodeAndCountry(String street, String city, Integer zipCode, String country);
}
