package org.levi9.DTO;

import javax.validation.constraints.NotNull;

public class ManufacturerDTO {

	@NotNull
	private String manufacturerName;

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public ManufacturerDTO() {
		super();
	}

}
