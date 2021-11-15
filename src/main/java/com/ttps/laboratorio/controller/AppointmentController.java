package com.ttps.laboratorio.controller;

import com.ttps.laboratorio.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
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
  public ResponseEntity<?> listFreeAppointmentDaysByMonth(@RequestParam(name = "year") @NonNull Integer year, @RequestParam(name = "month") @NonNull Integer month) {
    return ResponseEntity.ok(appointmentService.getFreeAppointmentDaysByMonth(year, month));
  }

  /**
   * Returns a list of the appointments of a day.
   * @return  Returns a list of all appointments with "200 OK".
   */
  @PreAuthorize("hasRole('CONFIGURATOR') OR hasRole('EMPLOYEE')")
  @GetMapping(path = "/appointments")
  public ResponseEntity<?> listAppointmentsByDate(@RequestParam(name = "year") @NonNull Integer year, @RequestParam(name = "month") @NonNull Integer month, @RequestParam(name = "day") @NonNull Integer day) {
    return ResponseEntity.ok(appointmentService.getAppointmentsByDate(year, month, day));
  }

  /**
   * Returns a list of the available appointments of a day.
   * @return  Returns a list of all appointments with "200 OK".
   */
  @PreAuthorize("hasRole('CONFIGURATOR') OR hasRole('EMPLOYEE')")
  @GetMapping(path = "/available-appointments")
  public ResponseEntity<?> listAvailableAppointmentsByDate(@RequestParam(name = "year") @NonNull Integer year, @RequestParam(name = "month") @NonNull Integer month, @RequestParam(name = "day") @NonNull Integer day) {
    return ResponseEntity.ok(appointmentService.getAvailableAppointmentsByDate(year, month, day));
  }

}
