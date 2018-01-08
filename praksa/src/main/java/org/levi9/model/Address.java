package org.levi9.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column
	private String street;

	@Column
	private Integer zipCode;

	@Column
	private String city;

	@Column
	private String country;

	public Address(Long id, String street, Integer zipCode, String city, String country) {
		super();
		this.id = id;
		this.street = street;
		this.zipCode = zipCode;
		this.city = city;
		this.country = country;
	}

	public Address() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", street=" + street + ", zipCode=" + zipCode + ", city=" + city + ", country="
				+ country + "]";
	}

}
