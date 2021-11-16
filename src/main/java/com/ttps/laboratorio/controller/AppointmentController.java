package com.ttps.laboratorio.controller;

import com.ttps.laboratorio.dto.AppointmentDTO;
import com.ttps.laboratorio.dto.DateDTO;
import com.ttps.laboratorio.service.AppointmentService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "appointment")
public class AppointmentController {

  private final AppointmentService appointmentService;

  public AppointmentController (AppointmentService appointmentService) {
    this.appointmentService = appointmentService;
  }

  /**
   * View a list of all appointments.
   * @return  Returns a list of all appointments with "200 OK".
   */
  @PreAuthorize("hasRole('CONFIGURATOR') OR hasRole('EMPLOYEE')")
  @GetMapping(path = "/")
  public ResponseEntity<?> listAppointments() {
    return ResponseEntity.ok(appointmentService.getAllAppointments());
  }

  /**
   * Returns a boolean list, where each index represents a day of the month.
   * If index 5 is false then an appointment can not be scheduled on that day.
   * @return  Returns a list of all appointments with "200 OK".
   */
  @PreAuthorize("hasRole('CONFIGURATOR') OR hasRole('EMPLOYEE')")
  @GetMapping(path = "/free-appointments")
  public ResponseEntity<?> listFreeAppointmentDaysByMonth(@Valid @RequestBody DateDTO dateDTO) {
    return ResponseEntity.ok(appointmentService.getFreeAppointmentDaysByMonth(dateDTO.getDate().getYear(), dateDTO.getDate().getMonthValue()));
  }

  /**
   * Returns a list of the appointments of a day.
   * @return  Returns a list of all appointments with "200 OK".
   */
  @PreAuthorize("hasRole('CONFIGURATOR') OR hasRole('EMPLOYEE')")
  @GetMapping(path = "/appointments")
  public ResponseEntity<?> listAppointmentsByDate(@Valid @RequestBody DateDTO dateDTO) {
    return ResponseEntity.ok(appointmentService.getAppointmentsByDate(dateDTO.getDate().getYear(), dateDTO.getDate().getMonthValue(), dateDTO.getDate().getDayOfMonth()));
  }

  /**
   * Returns a list of the available appointments of a day.
   * @return  Returns a list of all appointments with "200 OK".
   */
  @PreAuthorize("hasRole('CONFIGURATOR') OR hasRole('EMPLOYEE')")
  @GetMapping(path = "/available-appointments")
  public ResponseEntity<?> listAvailableAppointmentsByDate(@Valid @RequestBody DateDTO dateDTO) {
    return ResponseEntity.ok(appointmentService.getAvailableAppointmentsByDate(dateDTO.getDate().getYear(), dateDTO.getDate().getMonthValue(), dateDTO.getDate().getDayOfMonth()));
  }

  /**
   * Registers a new Appointment on the database.
   * @param appointmentDTO appointment information
   * @return status
   */
  @PreAuthorize("hasRole('CONFIGURATOR') OR hasRole('EMPLOYEE')")
  @PostMapping(path = "/")
  public ResponseEntity<?> createAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO) {
    appointmentService.createAppointment(appointmentDTO);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('CONFIGURATOR') OR hasRole('EMPLOYEE')")
  @DeleteMapping(path = "/")
  public ResponseEntity<?> deleteAppointment(@RequestParam(name = "appointmentId") @NonNull Long appointmentID) {
    appointmentService.deleteAppointment(appointmentID);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
