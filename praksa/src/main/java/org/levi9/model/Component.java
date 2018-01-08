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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "components")
public class Component {

	@Column(name = "name")
	@NotNull
	private String name;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@OneToOne
	@JoinColumn(name = "typeid")
	@NotNull
	private ComponentType type;

	@OneToOne
	@JoinColumn(name = "manufacturer_id")
	@NotNull
	private Manufacturer manufacturer;

	@Column(name = "quantity")
	@NotNull
	@Min(0)
	@Max((long) Integer.MAX_VALUE)
	private Integer quantity;

	@Column(name = "activeStatus")
	private boolean activeStatus;

	@Column(name = "price")
	@NotNull
	@Min(0)
	@Max((long) Double.MAX_VALUE)
	private double price;

	@OneToMany(fetch = FetchType.EAGER)
	private List<Comment> comments = new ArrayList<Comment>();

	public Component(String name, Long id, ComponentType type, Manufacturer manufacturer, Integer quantity,
			double price, boolean activeStatus) {
		super();
		this.name = name;
		this.id = id;
		this.type = type;
		this.manufacturer = manufacturer;
		this.quantity = quantity;
		this.price = price;
		this.activeStatus = activeStatus;
	}

	public Component(String name, Long componentid, ComponentType type, Manufacturer manufacturer, Integer quantity,
			double price) {
		super();
		this.name = name;
		this.type = type;
		this.manufacturer = manufacturer;
		this.quantity = quantity;
		this.price = price;
	}

	public ComponentType getType() {
		return type;
	}

	public void setType(ComponentType type) {
		this.type = type;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Component() {

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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public boolean isActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	@Override
	public String toString() {
		return "Component [name=" + name + ", id=" + id + ", type=" + type + ", manufacturer=" + manufacturer
				+ ", quantity=" + quantity + ", activeStatus=" + activeStatus + ", price=" + price + "]";
	}

}
