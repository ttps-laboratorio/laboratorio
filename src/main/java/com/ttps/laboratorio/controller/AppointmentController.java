package com.ttps.laboratorio.controller;

import com.ttps.laboratorio.dto.request.AppointmentDTO;
import com.ttps.laboratorio.entity.Appointment;
import com.ttps.laboratorio.service.AppointmentService;
import java.time.LocalDate;
import javax.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "appointment")
public class AppointmentController {

	private final AppointmentService appointmentService;

	public AppointmentController(AppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}

	/**
	 * View a list of all appointments.
	 *
	 * @return Returns a list of all appointments with "200 OK".
	 */
	@PreAuthorize("hasRole('CONFIGURATOR') OR hasRole('EMPLOYEE')")
	@GetMapping(path = "/")
	public ResponseEntity<?> listAppointments() {
		return ResponseEntity.ok(appointmentService.getAllAppointments());
	}

	/**
	 * Returns a boolean list, where each index represents a day of the month. If
	 * index 5 is false then an appointment can not be scheduled on that day.
	 *
	 * @return Returns a list of all appointments with "200 OK".
	 */
	@PreAuthorize("hasRole('CONFIGURATOR') OR hasRole('EMPLOYEE')")
	@GetMapping(path = "/free-appointments/{date}")
	public ResponseEntity<?> listFreeAppointmentDaysByMonth(
			@PathVariable(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		return ResponseEntity
				.ok(appointmentService.getFreeAppointmentDaysByMonth(date.getYear(), date.getMonthValue()));
	}

	/**
	 * Returns a list of the appointments of a day.
	 *
	 * @return Returns a list of all appointments with "200 OK".
	 */
	@PreAuthorize("hasRole('CONFIGURATOR') OR hasRole('EMPLOYEE')")
	@GetMapping(path = "/appointments/{date}")
	public ResponseEntity<?> listAppointmentsByDate(
			@PathVariable(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		return ResponseEntity.ok(appointmentService.getAppointmentsByDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
	}

	/**
	 * Returns a list of the available appointments of a day.
	 *
	 * @return Returns a list of all appointments with "200 OK".
	 */
	@PreAuthorize("hasRole('CONFIGURATOR') OR hasRole('EMPLOYEE')")
	@GetMapping(path = "/available-appointments/{date}")
	public ResponseEntity<?> listAvailableAppointmentsByDate(
			@PathVariable(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		return ResponseEntity.ok(appointmentService.getAvailableAppointmentsByDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
	}

	/**
	 * Registers a new Appointment on the database.
	 *
	 * @param appointmentDTO appointment information
	 * @return status
	 */
	@PreAuthorize("hasRole('CONFIGURATOR') OR hasRole('EMPLOYEE')")
	@PostMapping(path = "/")
	public ResponseEntity<Appointment> createAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO) {
		return new ResponseEntity<>(appointmentService.createAppointment(appointmentDTO), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('CONFIGURATOR') OR hasRole('EMPLOYEE')")
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteAppointment(@PathVariable(name = "id") @NonNull Long appointmentID) {
		appointmentService.deleteAppointment(appointmentID);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
