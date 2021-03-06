package com.ttps.laboratorio.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a study of a patient in the laboratory
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "studies")
public class Study implements Serializable {

	private static final long serialVersionUID = -7376811737402724614L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Builder.Default
	@Column(name = "created_at")
	private LocalDateTime createdAt = LocalDateTime.now();

	@NotNull
	@Column(name = "budget", nullable = false)
	private BigDecimal budget;

	@NotNull
	@Column(name = "extraction_amount", nullable = false)
	private BigDecimal extractionAmount;

	@Builder.Default
	@Column(name = "paid_extraction_amount")
	private Boolean paidExtractionAmount = false;

	@OneToOne(mappedBy = "study", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private FinalReport finalReport;

	/**
	 * Maybe this has to be deleted and calculated.
	 */
	@Builder.Default
	@Column(name = "delay")
	private Boolean delay = false;

	/**
	 * A study has one patient and a patient may have many studies
	 */
	@ManyToOne(optional = false)
	private Patient patient;

	/**
	 * A study may have one appointment
	 */
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Appointment appointment;

	/**
	 * Doctor who refers the patient
	 */
	@ManyToOne
	private Doctor referringDoctor;

	/**
	 * Type of the study
	 */
	@ManyToOne
	private StudyType type;

	/**
	 * Person who extract the sample from the extraction room
	 */
	@ManyToOne(optional = true)
	private Extractionist extractionist;

	/**
	 * Presumptive diagnosis elaborated by referring doctor
	 */
	@NotNull
	@ManyToOne(optional = false)
	private PresumptiveDiagnosis presumptiveDiagnosis;

	/**
	 * Sample represents the blood extraction of the study
	 */
	@ManyToOne(optional = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "sample_id", referencedColumnName = "id")
	private Sample sample;

	@Builder.Default
	@OneToMany(mappedBy = "study", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Checkpoint> checkpoints = new ArrayList<>();

	/**
	 * Get actual checkpoint or null
	 *
	 * @return
	 */
	@JsonIgnore
	public Checkpoint getRecentCheckpoint() {
		return checkpoints.stream().sorted(Checkpoint.CREATED_AT_COMPARATOR_DESC).findFirst().orElse(null);
	}

	/**
	 * Get actual status or null
	 *
	 * @return
	 */
	public StudyStatus getActualStatus() {
		return Optional.ofNullable(getRecentCheckpoint()).map(Checkpoint::getStatus).orElse(null);
	}

	public void setFinalReport(FinalReport finalReport) {
		this.finalReport = finalReport;
		finalReport.setStudy(this);
	}

	public void setSample(Sample sample) {
		this.sample = sample;
		if (sample != null)
			sample.setStudy(this);
	}

	public boolean addCheckpoint(Checkpoint checkpoint) {
		checkpoint.setStudy(this);
		return this.checkpoints.add(checkpoint);
	}

}
