package com.ttps.laboratorio.service;

import com.ttps.laboratorio.entity.BlockedDay;
import com.ttps.laboratorio.repository.IBlockedDayRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class BlockedDayService {

	private final IBlockedDayRepository blockedDayRepository;

	public BlockedDayService(IBlockedDayRepository blockedDayRepository) {
		this.blockedDayRepository = blockedDayRepository;
	}

	/**
	 * Gets all blocked days registered.
	 * 
	 * @return List of all blocked days
	 */
	public List<BlockedDay> getAllBlockedDays() {
		return new ArrayList<>(blockedDayRepository.findAll());
	}

	public List<BlockedDay> getBlockedDaysByMonth(Integer month) {
		List<BlockedDay> allBlockedDays = getAllBlockedDays();
		return allBlockedDays.stream().filter(blockedDay -> isSameMonth(blockedDay, month))
				.collect(Collectors.toList());
	}

	private Boolean isSameMonth(BlockedDay blockedDay, Integer month) {
		LocalDate localDate = blockedDay.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int blockedDayMonth = localDate.getMonthValue();
		return month == blockedDayMonth;
	}

}
