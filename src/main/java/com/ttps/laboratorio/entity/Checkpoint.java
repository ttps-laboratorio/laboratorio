package com.ttps.laboratorio.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "checkpoints")
public class Checkpoint implements Serializable {

	public static class CheckpointComparatorAsc implements Comparator<Checkpoint> {

		@Override
		public int compare(Checkpoint o1, Checkpoint o2) {
			return o1.createdAt.compareTo(o2.createdAt);
		}

	}

	public static class CheckpointComparatorDesc implements Comparator<Checkpoint> {

		@Override
		public int compare(Checkpoint o1, Checkpoint o2) {
			return o2.createdAt.compareTo(o1.createdAt);
		}

	}

	public static Comparator<Checkpoint> CREATED_AT_COMPARATOR_ASC = new CheckpointComparatorAsc();
	public static Comparator<Checkpoint> CREATED_AT_COMPARATOR_DESC = new CheckpointComparatorDesc();

	private static final long serialVersionUID = 4363372527985384610L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Builder.Default
	@NotNull
	@Column(name = "created_at", unique = true, nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@ManyToOne
	private StudyStatus status;

	@ManyToOne
	private Employee createdBy;

	@ManyToOne
	private Study study;
}
