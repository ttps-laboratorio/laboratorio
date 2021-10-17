package com.ttps.laboratorio.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents a study of a patient in the laboratory
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "studies")
public class Study implements Serializable {

	private static final long serialVersionUID = -7376811737402724614L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "discharge_date")
	private LocalDate dischargeDate;

	@NotNull
	@Column(name = "budget", precision = 2, nullable = false)
	private BigDecimal budget;

	@NotNull
	@Column(name = "extraction_amount", precision = 2, nullable = false)
	private BigDecimal extractionAmount;

	@Column(name = "paid_extraction_amount")
	private Boolean paidExtractionAmount;

	@Column(name = "positive_result")
	private Boolean positiveResult;

	/**
	 * Maybe this has to be deleted and calculated.
	 */
	@Column(name = "delayed")
	private Boolean delayed;

	/**
	 * An study has one patient and a patient may has many studies
	 */
	@ManyToOne(optional = false)
	private Patient patient;

}
