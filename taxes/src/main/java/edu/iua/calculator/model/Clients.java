package edu.iua.calculator.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="clients")
public class Clients {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="client_id", nullable = false)
	private int clientId;
	
	@Column(name="name", length = 60)
	private String name;

	@Column(name="last_name", length = 60)
	private String lastName;
	
	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id")
	private Addresses address;

	public Clients() {}

	public Clients(int clientId, String name, String lastName, Addresses address) {
		super();
		this.clientId = clientId;
		this.name = name;
		this.lastName = lastName;
		this.address = address;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Addresses getAddress() {
		return address;
	}

	public void setAddress(Addresses address) {
		this.address = address;
	}

}