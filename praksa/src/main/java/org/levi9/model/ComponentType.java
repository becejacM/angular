package org.levi9.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class ComponentType {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;

	@Column
	private String name;

	@Column
	private boolean required;

	@Column
	private String cardinality;

	public ComponentType() {

	}

	public ComponentType(String name) {
		this.name = name;
	}

	public ComponentType(String name, boolean required, String cardinality) {
		super();
		this.name = name;
		this.cardinality = cardinality;
		this.required = required;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardinality() {
		return cardinality;
	}

	public void setCardinality(String cardinality) {
		this.cardinality = cardinality;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	@Override
	public String toString() {
		return "Type [name=" + name + ", cardinality=" + cardinality + "]";
	}

}
