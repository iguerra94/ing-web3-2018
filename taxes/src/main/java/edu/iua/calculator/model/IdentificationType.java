package edu.iua.calculator.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="identification_type_details")
public class IdentificationType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "identification_id", nullable = false)
	private int identificationId;
	
	@Column(name = "identification_type", length = 100, nullable = false)
	private String identificationType;
	
	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "identificationType")
	private List<Clients> clients;
	
	public IdentificationType() {}

	public IdentificationType(int identificationId, String identificationType) {
		super();
		this.identificationId = identificationId;
		this.identificationType = identificationType;
	}

	public int getIdentificationId() {
		return identificationId;
	}

	public void setIdentificationId(int identificationId) {
		this.identificationId = identificationId;
	}

	public String getIdentificationType() {
		return identificationType;
	}

	public void setIdentificationType(String identificationType) {
		this.identificationType = identificationType;
	}

}