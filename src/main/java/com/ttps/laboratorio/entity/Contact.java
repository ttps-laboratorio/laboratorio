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
 * Represents a contact information of a patient
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "contacts")
public class Contact implements Serializable {

	private static final long serialVersionUID = 7344265627229872984L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "name", length = 60, nullable = false)
	private String name;

	@NotNull
	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;

	@NotNull
	@Column(name = "email", length = 30, nullable = false)
	private String email;

}
