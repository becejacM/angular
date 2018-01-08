package org.levi9.DTO;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.levi9.model.Address;
import org.levi9.model.Component;
import org.levi9.model.StatusOfConfiguration;

public class ConfigurationDTO {

	private Long id;

	@NotNull
	private String name;

	@NotNull
	private List<Component> components = new ArrayList<Component>();

	private StatusOfConfiguration status;

	@NotNull
	private Long userId;

	private Address payAddress;

	private Address deliverAddress;

	private String methodOfPayment;

	public ConfigurationDTO() {
	}

	public ConfigurationDTO(String name, List<Component> components, Long userId) {
		super();
		this.name = name;
		this.components = components;
		this.userId = userId;
	}

	public ConfigurationDTO(Long id, String name, List<Component> components, Long userId) {
		super();
		this.id = id;
		this.name = name;
		this.components = components;
		this.userId = userId;
	}

	public Address getPayAddress() {
		return payAddress;
	}

	public void setPayAddress(Address payAddress) {
		this.payAddress = payAddress;
	}

	public Address getDeliverAddress() {
		return deliverAddress;
	}

	public void setDeliverAddress(Address deliverAddress) {
		this.deliverAddress = deliverAddress;
	}

	public String getMethodOfPayment() {
		return methodOfPayment;
	}

	public void setMethodOfPayment(String methodOfPayment) {
		this.methodOfPayment = methodOfPayment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StatusOfConfiguration getStatus() {
		return status;
	}

	public void setStatus(StatusOfConfiguration statusOfConfiguration) {
		this.status = statusOfConfiguration;
	}

	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "ConfigurationDTO [id=" + id + ", name=" + name + ", components=" + components + ", status=" + status
				+ ", userId=" + userId + ", payAddress=" + payAddress + ", deliverAddress=" + deliverAddress
				+ ", methodOfPayment=" + methodOfPayment + "]";
	}

}
