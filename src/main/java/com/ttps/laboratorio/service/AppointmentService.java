package com.ttps.laboratorio.service;

import com.ttps.laboratorio.dto.AppointmentDTO;
import com.ttps.laboratorio.entity.Appointment;
import com.ttps.laboratorio.entity.BlockedDay;
import com.ttps.laboratorio.entity.ScheduleConfigurator;
import com.ttps.laboratorio.exception.BadRequestException;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IAppointmentRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

	private final IAppointmentRepository appointmentRepository;

	private final BlockedDayService blockedDayService;

	public AppointmentService(IAppointmentRepository appointmentRepository, BlockedDayService blockedDayService) {
		this.appointmentRepository = appointmentRepository;
		this.blockedDayService = blockedDayService;
	}

	/**
	 * Gets all appointments registered.
	 *
	 * @return List of all appointments
	 */
	public List<Appointment> getAllAppointments() {
		return new ArrayList<>(appointmentRepository.findAll());
	}

	public List<LocalDate> getFreeAppointmentDaysByMonth(Integer year, Integer month) {
		List<Boolean> booleanMonthList = getBooleanFreeAppointmentDaysByMonth(year, month);
		List<LocalDate> freeAppointmentDaysByMonth = new ArrayList<>();
		for (int i = 0; i < booleanMonthList.size(); i++) {
			if (booleanMonthList.get(i)) {
				freeAppointmentDaysByMonth.add(LocalDate.of(year, month, i + 1));
			}
		}
		return freeAppointmentDaysByMonth;
	}

	public List<Appointment> getAppointmentsByDate(Integer year, Integer month, Integer day) {
		LocalDate date = LocalDate.of(year, month, day);
		List<Appointment> allAppointments = getAllAppointments();
		return allAppointments.stream().filter(appointment -> appointment.getDate().equals(date))
				.collect(Collectors.toList());
	}

	public List<LocalTime> getAvailableAppointmentsByDate(Integer year, Integer month, Integer day) {
		List<Appointment> appointmentsFromDate = getAppointmentsByDate(year, month, day);
		List<LocalTime> appointmentList = getRawAppointmentList();
		List<Boolean> availableDays = getBooleanFreeAppointmentDaysByMonthWithoutFullAppointmentDays(year, month);
		if (!availableDays.get(day - 1)) {
			return new ArrayList<>();
		}
		if (appointmentsFromDate != null && !appointmentsFromDate.isEmpty()) {
			appointmentsFromDate.forEach(appointment -> appointmentList.removeIf(a -> a.equals(appointment.getTime())));
		}
		return appointmentList;
	}

	/**
	 * Creates new appointment.
	 * 
	 * @param request appointment information
	 */
	public void createAppointment(AppointmentDTO request) {
		ScheduleConfigurator scheduleConfigurator = new ScheduleConfigurator();
		List<Boolean> booleanMonthList = getBooleanFreeAppointmentDaysByMonth(request.getDate().getYear(),
				request.getDate().getMonthValue());
		if (!booleanMonthList.get(request.getDate().getDayOfMonth() - 1)) {
			throw new BadRequestException(
					"El laboratorio está cerrado o ya no tiene turnos disponibles el día seleccionado.");
		}
		if (request.getTime().isBefore(scheduleConfigurator.getOpeningTime())
				|| request.getTime().isAfter(scheduleConfigurator.getClosingTime())
				|| request.getTime().equals(scheduleConfigurator.getClosingTime())) {
			throw new BadRequestException("El turno debe solicitarse dentro del horario de atencion.");
		}
		List<LocalTime> availableAppointments = getAvailableAppointmentsByDate(request.getDate().getYear(),
				request.getDate().getMonthValue(), request.getDate().getDayOfMonth());
		if (!availableAppointments.contains(request.getTime())) {
			throw new BadRequestException("Turno no disponible.");
		}
		Appointment appointment = new Appointment();
		appointment.setDate(request.getDate());
		appointment.setTime(request.getTime());
		appointmentRepository.save(appointment);
	}

	/**
	 * Deletes a appointment.
	 * 
	 * @param appointmentID id from the appointment to delete
	 */
	public void deleteAppointment(Long appointmentID) {
		appointmentRepository.delete(appointmentRepository.findById(appointmentID)
				.orElseThrow(() -> new NotFoundException("No existe un turno con el id " + appointmentID + ".")));
	}

	private List<Boolean> getBooleanFreeAppointmentDaysByMonth(Integer year, Integer month) {
		int daysInMonth = YearMonth.of(year, month).lengthOfMonth();
		Calendar calendar = Calendar.getInstance();
		List<Boolean> freeDaysInMonth = getBooleanFreeAppointmentDaysByMonthWithoutFullAppointmentDays(year, month);
		// filters appointment full days
		blockAppointmentFullDays(calendar, year, month, daysInMonth, freeDaysInMonth);
		return freeDaysInMonth;
	}

	private List<Boolean> getBooleanFreeAppointmentDaysByMonthWithoutFullAppointmentDays(Integer year, Integer month) {
		int daysInMonth = YearMonth.of(year, month).lengthOfMonth();
		List<Boolean> freeDaysInMonth = new ArrayList<>(Arrays.asList(new Boolean[daysInMonth]));
		Collections.fill(freeDaysInMonth, Boolean.TRUE);
		Calendar calendar = Calendar.getInstance();
		// filters weekends
		blockSaturdaysAndSundays(calendar, year, month, daysInMonth, freeDaysInMonth);
		List<BlockedDay> blockedDaysFromMonth = blockedDayService.getBlockedDaysByMonth(month);
		// filters blocked days
		blockedDaysFromMonth.forEach(blockedDay -> blockBlockedDays(blockedDay, freeDaysInMonth));
		return freeDaysInMonth;
	}

	private void blockSaturdaysAndSundays(Calendar calendar, Integer year, Integer month, int daysInMonth,
			List<Boolean> freeDaysInMonth) {
		for (int day = 1; day <= daysInMonth; day++) {
			calendar.set(year, month - 1, day);
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
				freeDaysInMonth.set(day - 1, Boolean.FALSE);
			}
		}
	}

	private void blockBlockedDays(BlockedDay blockedDay, List<Boolean> freeDaysInMonth) {
		LocalDate localDate = blockedDay.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int day = localDate.getDayOfMonth();
		freeDaysInMonth.set(day, Boolean.FALSE);
	}

	private void blockAppointmentFullDays(Calendar calendar, Integer year, Integer month, int daysInMonth,
			List<Boolean> freeDaysInMonth) {
		for (int day = 1; day <= daysInMonth; day++) {
			calendar.set(year, month - 1, day);
			List<LocalTime> availableAppointmentsFromDate = getAvailableAppointmentsByDate(year, month, day);
			if (availableAppointmentsFromDate != null && availableAppointmentsFromDate.isEmpty()) {
				freeDaysInMonth.set(day - 1, Boolean.FALSE);
			}
		}
	}

	// TODO its not checking if the scheduleConfigurator may have a change
	private List<LocalTime> getRawAppointmentList() {
		ScheduleConfigurator scheduleConfigurator = new ScheduleConfigurator();
		LocalTime aux = scheduleConfigurator.getOpeningTime();
		List<LocalTime> rawAppointmentList = new ArrayList<>();
		while (aux.compareTo(scheduleConfigurator.getClosingTime()) < 0) {
			rawAppointmentList.add(aux);
			aux = aux.plusMinutes(scheduleConfigurator.getFrequency().getMinute());
		}
		return rawAppointmentList;
	}

}
