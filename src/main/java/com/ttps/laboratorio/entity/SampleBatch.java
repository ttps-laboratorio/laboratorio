package com.ttps.laboratorio.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a batch of samples to be processed.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sample_batches")
public class SampleBatch implements Serializable {

	private static final long serialVersionUID = 4509621474632912732L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private SampleBatchStatus status = SampleBatchStatus.IN_PROCESS;

	@JsonIgnore
	@OneToMany(mappedBy = "sampleBatch", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@Column(name = "samples", nullable = false)
	@Builder.Default
	private List<Sample> samples = new ArrayList<>();

	@Column(name = "final_report_url")
	private String finalReportsUrl;

	public void addSample(Sample sample) {
		samples.add(sample);
		sample.setSampleBatch(this);
	}

}
