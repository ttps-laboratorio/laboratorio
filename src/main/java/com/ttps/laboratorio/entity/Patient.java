package com.ttps.laboratorio.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a patient.
 * <p>
 * Patient is a person that has genetic medical studies in the laboratory
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "patients")
public class Patient implements Serializable {

	private static final long serialVersionUID = -6743123046826185125L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "dni", length = 10, unique = true, nullable = false)
	private Long dni;

	@NotNull
	@Column(name = "first_name", length = 50, nullable = false)
	private String firstName;

	@NotNull
	@Column(name = "last_name", length = 50, nullable = false)
	private String lastName;

	@NotNull
	@Column(name = "birth_date", nullable = false)
	private LocalDate birthDate;

	@NotNull
	@Lob
	@Column(name = "clinic_history")
	private String clinicHistory;

	@Column(name = "affiliate_number")
	private String affiliateNumber;

	@OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
	private Contact contact;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "health_insurance_id")
	private HealthInsurance healthInsurance;

	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Study> studies;

	public boolean addStudy(Study study) {
		study.setPatient(this);
		return studies.add(study);
	}
}
