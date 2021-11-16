package com.ttps.laboratorio.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
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
 * 
 * Represents an employee of laboratory
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "employees")
public class Employee implements Serializable {

	private static final long serialVersionUID = -3489278096375189328L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@NotNull
	@Column(name = "first_name", unique = true, nullable = false)
	protected String firstName;

	@NotNull
	@Column(name = "last_name", unique = true, nullable = false)
	protected String lastName;

	@ManyToOne(cascade = CascadeType.ALL)
	protected User user;
}
