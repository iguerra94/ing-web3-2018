package edu.iua.calculator.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="suppliers")
public class Suppliers {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "supplier_id", nullable = false)
	private int supplierId;
	
	@Column(name = "business_name", length = 100, nullable = false)
	private String businessName;
	
	public Suppliers() {}

	public Suppliers(int supplierId, String businessName) {
		super();
		this.supplierId = supplierId;
		this.businessName = businessName;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

}
