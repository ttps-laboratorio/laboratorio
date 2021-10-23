package com.ttps.laboratorio.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents an appointment in which blood is extracted from a patient for a study
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "appointments")
public class Appointment implements Serializable {

	private static final long serialVersionUID = -6805723690495232273L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "date", nullable = false)
	private LocalDate date;

	@NotNull
	@Column(name = "time", nullable = false)
	private LocalTime time;

}
