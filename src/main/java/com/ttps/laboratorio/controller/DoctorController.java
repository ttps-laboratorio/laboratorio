package com.ttps.laboratorio.controller;

import com.ttps.laboratorio.dto.DoctorDTO;
import com.ttps.laboratorio.service.DoctorService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "doctor")
public class DoctorController {

  private final DoctorService doctorService;

  public DoctorController(DoctorService doctorService) {
    this.doctorService = doctorService;
  }

  /**
   * View a list of all doctors.
   * @return  Returns a list of all doctors with "200 OK".
   */
  @PreAuthorize("hasRole('CONFIGURATOR')")
  @GetMapping(path = "/")
  public ResponseEntity<?> listDoctors() {
    return ResponseEntity.ok(doctorService.getAllDoctors());
  }

  /**
   * Registers a new Doctor on the database.
   * @param doctorDTO doctor information
   * @return status
   */
  @PreAuthorize("hasRole('CONFIGURATOR')")
  @PostMapping(path = "/create")
  public ResponseEntity<?> createDoctor(@Valid @RequestBody DoctorDTO doctorDTO) {
    doctorService.createDoctor(doctorDTO);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  /**
   * Modifies a new Doctor on the database.
   * @param doctorDTO doctor information
   * @return status
   */
  @PreAuthorize("hasRole('CONFIGURATOR')")
  @PutMapping(path = "/update")
  public ResponseEntity<?> updateDoctor(@RequestParam(name = "doctorId") @NonNull Long doctorID, @Valid @RequestBody @NonNull DoctorDTO doctorDTO) {
    doctorService.updateDoctor(doctorID, doctorDTO);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
