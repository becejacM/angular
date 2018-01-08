package org.levi9.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "configuration")
public class Configuration {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	@NotNull
	private String name;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<Component> components = new ArrayList<Component>();

	@Column
	private StatusOfConfiguration configurationStatus;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties("configurations")
	private User user;

	@OneToOne
	@JoinColumn(name = "pay_address_id")
	private Address payAddress;

	@OneToOne
	@JoinColumn(name = "deliver_address_id")
	private Address deliverAddress;

	@Column
	private String methodOfPayment;

	public Configuration() {
	}

	public Configuration(Long configurationid, String name, List<Component> components,
			StatusOfConfiguration configurationStatus) {
		super();
		this.id = configurationid;
		this.name = name;
		this.components = components;
		this.configurationStatus = configurationStatus;
	}

	public Configuration(String name, List<Component> components, StatusOfConfiguration configurationStatus,
			User user) {
		super();
		this.name = name;
		this.components = components;
		this.configurationStatus = configurationStatus;
		this.user = user;
	}

	public String getMethodOfPayment() {
		return methodOfPayment;
	}

	public void setMethodOfPayment(String methodOfPayment) {
		this.methodOfPayment = methodOfPayment;
	}

	public Configuration(List<Component> components, String name) {
		this.components = components;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StatusOfConfiguration getConfigurationStatus() {
		return configurationStatus;
	}

	public void setConfigurationStatus(StatusOfConfiguration configurationStatus) {
		this.configurationStatus = configurationStatus;
	}

	@Override
	public String toString() {
		return "Configuration [id=" + id + ", name=" + name + ", components=" + components + ", configurationStatus="
				+ configurationStatus + ", user=" + user + ", payAddress=" + payAddress + ", deliverAddress="
				+ deliverAddress + ", methodOfPayment=" + methodOfPayment + "]";
	}

}
