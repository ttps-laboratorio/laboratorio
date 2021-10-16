package com.ttps.laboratorio.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
 * Represents a user of the application.
 * 
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "password")
@Builder
@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = 6203296922555108573L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "username", length = 50, nullable = false, unique = true)
	private String username;

	@NotNull
	@Column(name = "password", length = 255, nullable = false)
	private String password;

	@NotNull
	@Column(name = "email", length = 255, nullable = false, unique = true)
	private String email;

	@Enumerated(EnumType.STRING)
	private RoleEnum role;

}
