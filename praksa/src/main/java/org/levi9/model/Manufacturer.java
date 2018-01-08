package org.levi9.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Manufacturer {

	@Column
	private String name;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;

	@Override
	public String toString() {
		return "Manufacturer [name=" + name + ", id=" + id + "]";
	}

	public Manufacturer() {
		super();
	}

	public Manufacturer(String name) {
		this.name = name;
	}

	public Manufacturer(String name, Long id) {
		super();
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
