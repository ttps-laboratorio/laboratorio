package com.ttps.laboratorio.entity;

import com.sun.istack.NotNull;
import java.io.Serializable;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name = "schedule_configurator")
public class ScheduleConfigurator implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @NotNull
  @Column(name = "opening_time", unique = true, nullable = false)
  protected LocalTime openingTime = LocalTime.of(7,0);

  @NotNull
  @Column(name = "closing_time", unique = true, nullable = false)
  protected LocalTime closingTime = LocalTime.of(12,0);

  @NotNull
  @Column(name = "frequency", unique = true, nullable = false)
  protected LocalTime frequency = LocalTime.of(0,15);

}
