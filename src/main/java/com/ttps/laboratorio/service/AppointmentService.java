package com.ttps.laboratorio.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tomcat.jni.Local;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ttps.laboratorio.dto.request.AppointmentDTO;
import com.ttps.laboratorio.entity.Appointment;
import com.ttps.laboratorio.entity.BlockedDay;
import com.ttps.laboratorio.entity.ScheduleConfigurator;
import com.ttps.laboratorio.entity.Study;
import com.ttps.laboratorio.entity.StudyStatus;
import com.ttps.laboratorio.exception.BadRequestException;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IAppointmentRepository;

@Service
public class AppointmentService {

	private final IAppointmentRepository appointmentRepository;

	private final BlockedDayService blockedDayService;

	private final StudyService studyService;

	private final StudyStatusService studyStatusService;

	public AppointmentService(IAppointmentRepository appointmentRepository, BlockedDayService blockedDayService, StudyService studyService,
														StudyStatusService studyStatusService) {
		this.appointmentRepository = appointmentRepository;
		this.blockedDayService = blockedDayService;
		this.studyService = studyService;
		this.studyStatusService = studyStatusService;
	}

	/**
	 * Gets all appointments registered.
	 *
	 * @return List of all appointments
	 */
	public List<Appointment> getAllAppointments() {
		return new ArrayList<>(appointmentRepository.findAll());
	}

	private Integer addFreeAppointmentMonth(int nextMonth, Integer year, List<LocalDate> freeAppointmentDaysByTwoMonths) {
		if (nextMonth == 1) {
			year += 1;
		}
		freeAppointmentDaysByTwoMonths.addAll(getFreeAppointmentDaysByMonth(year, nextMonth));
		return year;
	}

	public List<LocalDate> getFreeAppointmentDaysByThreeMonths(Integer year, Integer month) {
		List<LocalDate> freeAppointmentDaysByTwoMonths = new ArrayList<>(getFreeAppointmentDaysByMonth(year, month));
		LocalDate currentMonth = LocalDate.of(year, month, 1);
		year = addFreeAppointmentMonth(currentMonth.plusMonths(1).getMonthValue(), year, freeAppointmentDaysByTwoMonths);
		addFreeAppointmentMonth(currentMonth.plusMonths(2).getMonthValue(), year, freeAppointmentDaysByTwoMonths);
		return freeAppointmentDaysByTwoMonths;
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
	public Appointment createAppointment(Long studyId, AppointmentDTO request) {
		Study study = studyService.getStudyById(studyId);
		if (study.getActualStatus() != null
				&& !study.getActualStatus().getId().equals(StudyStatus.ESPERANDO_SELECCION_DE_TURNO)) {
			throw new BadRequestException(
					"El estudio #" + studyId + " no se encuentra en el estado correspondiente para sacar turno.");
		}
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
		study.setAppointment(appointment);
		studyService.addCheckpointWithLoggedUser(StudyStatus.ESPERANDO_TOMA_DE_MUESTRA, study);
		studyService.saveStudy(study);
		return appointment;
	}

	/**
	 * Deletes a appointment.
	 *
	 * @param appointmentID id from the appointment to delete
	 */
	@Transactional
	public void deleteAppointment(Long appointmentID) {
		Appointment appointment = appointmentRepository.findById(appointmentID)
				.orElseThrow(() -> new NotFoundException("No existe un turno con el id " + appointmentID + "."));
		Study study = studyService.getStudyByAppointment(appointment);
		if (study.getActualStatus() != null && !study.getActualStatus().getId().equals(StudyStatus.ESPERANDO_TOMA_DE_MUESTRA)) {
			throw new BadRequestException(
					"El estudio no se encuentra en el estado correspondiente para eliminar el turno.");
		}
		study.setAppointment(null);
		studyService.addCheckpointWithLoggedUser(StudyStatus.ESPERANDO_SELECCION_DE_TURNO, study);
		studyService.saveStudy(study);
		appointmentRepository.delete(appointment);
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

	@Scheduled(cron = "0 0 0 * * ?")
	@Transactional
	public void cancelAppointment() {
		studyService.getStudiesByActualStatus(StudyStatus.ESPERANDO_TOMA_DE_MUESTRA).stream()
				.filter(s -> s.getAppointment().getDate().plusDays(30).compareTo(LocalDate.now()) < 0).forEach(study -> {
					study.setAppointment(null);
					studyService.addCheckpointWithLoggedUser(StudyStatus.ESPERANDO_SELECCION_DE_TURNO, study);
					studyService.saveStudy(study);
				});
	}

}
