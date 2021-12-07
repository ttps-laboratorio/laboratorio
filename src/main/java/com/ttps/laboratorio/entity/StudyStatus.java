package com.ttps.laboratorio.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the status of a study in the workflow
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "study_statuses")
public class StudyStatus implements Serializable {

	private static final long serialVersionUID = 5117918220377026223L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "name", unique = true, nullable = false)
	private String name;

	@Column(name = "num_order", nullable = false)
	private Integer order;

	// TODO: maybe this is not necessary
	@ManyToOne(optional = true)
	@JoinColumn(name = "next_id", referencedColumnName = "id")
	private StudyStatus next;

	@ManyToOne(optional = true)
	@JoinColumn(name = "previous_id", referencedColumnName = "id")
	private StudyStatus previous;

}
