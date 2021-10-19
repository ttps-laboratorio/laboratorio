package com.ttps.laboratorio.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the blood extraction of a patient for a study
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "samples")
public class Sample implements Serializable {

	private static final long serialVersionUID = -8917121333355673866L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "mililiters", nullable = false)
	private Double mililiters;

	@NotNull
	@Column(name = "freezer", nullable = false)
	private Integer freezer;

	@OneToOne(optional = false)
	@JoinColumn(name = "study_id")
	private Study study;

	@ManyToOne(optional = true)
	@JoinColumn(name = "sample_batch_id", nullable = true)
	private SampleBatch sampleBatch;
}
