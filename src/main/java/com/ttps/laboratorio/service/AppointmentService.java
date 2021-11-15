package com.ttps.laboratorio.service;

import com.ttps.laboratorio.entity.Appointment;
import com.ttps.laboratorio.entity.BlockedDay;
import com.ttps.laboratorio.entity.ScheduleConfigurator;
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

  public AppointmentService(IAppointmentRepository appointmentRepository,
                            BlockedDayService blockedDayService) {
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

  public List<Boolean> getFreeAppointmentDaysByMonth(Integer year, Integer month) {
    int daysInMonth = YearMonth.of(year, month).lengthOfMonth();
    List<Boolean> freeDaysInMonth = new ArrayList<>(Arrays.asList(new Boolean[daysInMonth]));
    Collections.fill(freeDaysInMonth, Boolean.TRUE);
    Calendar calendar = Calendar.getInstance();
    // filters weekends
    blockSaturdaysAndSundays(calendar, year, month, daysInMonth, freeDaysInMonth);
    List<BlockedDay> blockedDaysFromMonth = blockedDayService.getBlockedDaysByMonth(month);
    // filters blocked days
    blockedDaysFromMonth.forEach(blockedDay -> blockBlockedDays(blockedDay, freeDaysInMonth));
    // filters appointment full days
    blockAppointmentFullDays(calendar, year, month, daysInMonth, freeDaysInMonth);
    return freeDaysInMonth;
  }

  public List<Appointment> getAppointmentsByDate(Integer year, Integer month, Integer day) {
    LocalDate date = LocalDate.of(year, month, day);
    List<Appointment> allAppointments = getAllAppointments();
    return allAppointments.stream().filter(appointment -> appointment.getDate().equals(date)).collect(Collectors.toList());
  }

  public List<LocalTime> getAvailableAppointmentsByDate(Integer year, Integer month, Integer day) {
    List<Appointment> appointmentsFromDate = getAppointmentsByDate(year, month, day);
    List<LocalTime> appointmentList = getRawAppointmentList();
    if (appointmentsFromDate != null && !appointmentsFromDate.isEmpty()) {
      appointmentsFromDate.forEach(appointment -> appointmentList.removeIf(a -> a.equals(appointment.getTime())));
    }
    return appointmentList;
  }

  private void blockSaturdaysAndSundays(Calendar calendar, Integer year, Integer month, int daysInMonth, List<Boolean> freeDaysInMonth) {
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

  private void blockAppointmentFullDays(Calendar calendar, Integer year, Integer month, int daysInMonth, List<Boolean> freeDaysInMonth) {
    for (int day = 1; day <= daysInMonth; day++) {
      calendar.set(year, month - 1, day);
      List<LocalTime> availableAppointmentsFromDate = getAvailableAppointmentsByDate(year, month, day);
      if (availableAppointmentsFromDate != null && availableAppointmentsFromDate.isEmpty()) {
        freeDaysInMonth.set(day, Boolean.FALSE);
      }
    }
  }

  //TODO its not checking if the scheduleConfigurator may have a change
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
