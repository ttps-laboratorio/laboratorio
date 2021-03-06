package com.ttps.laboratorio.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents the final report of a study
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "final_reports")
public class FinalReport implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Builder.Default
	@NotNull
	@Column(name = "created_at", unique = true, nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@Column(name = "positive_result")
	private Boolean positiveResult;

	@JoinColumn(name = "medical_informant")
	@ManyToOne
	private Employee medicalInformant;

	@Column(name = "report")
	private String report;

	@JoinColumn(name = "study_id")
	@OneToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Study study;

}
