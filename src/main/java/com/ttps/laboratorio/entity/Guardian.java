package com.ttps.laboratorio.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "guardians")
public class Guardian {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(name = "first_name", length = 60, nullable = false)
  private String firstName;

  @NotNull
  @Column(name = "last_name", length = 60, nullable = false)
  private String lastName;

  @NotNull
  @Column(name = "phone_number", nullable = false)
  private String phoneNumber;

  @NotNull
  @Column(name = "email", length = 30, nullable = false)
  private String email;

  @NotNull
  @Column(name = "address", length = 60, nullable = false)
  private String address;

}
