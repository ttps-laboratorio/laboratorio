package com.ttps.laboratorio.entity;

import java.io.Serializable;

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
 * 
 * Represents a Doctor
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "doctors")
public class Doctor implements Serializable {

	private static final long serialVersionUID = 7604042034167642293L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "first_name", length = 50, nullable = false)
	private String firstName;

	@NotNull
	@Column(name = "last_name", length = 50, nullable = false)
	private String lastName;

	@NotNull
	@Column(name = "email", length = 255, nullable = false, unique = true)
	private String email;

	@Column(name = "phone_number", nullable = true)
	private Integer phoneNumber;

	@NotNull
	@Column(name = "license_number", nullable = false)
	private Integer licenseNumber;

}
