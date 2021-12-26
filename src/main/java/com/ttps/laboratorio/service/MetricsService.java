package com.ttps.laboratorio.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ttps.laboratorio.constants.Months;
import com.ttps.laboratorio.dto.response.StudiesByMonthDTO;
import com.ttps.laboratorio.dto.response.StudiesByMonthOfYearDTO;
import com.ttps.laboratorio.dto.response.StudiesByStudyTypeDTO;
import com.ttps.laboratorio.entity.StudyType;

@Service
public class MetricsService {

	private final StudyTypeService studyTypeService;

	private final StudyService studyService;

	public MetricsService(StudyTypeService studyTypeService, StudyService studyService) {
		this.studyTypeService = studyTypeService;
		this.studyService = studyService;
	}

	public List<StudiesByStudyTypeDTO> listStudiesByStudyType() {
		List<StudyType> studyTypes = studyTypeService.getAllStudyTypes();
		List<StudiesByStudyTypeDTO> response = new ArrayList<>();
		studyTypes.forEach(studyType -> {
			StudiesByStudyTypeDTO studiesByStudyTypeDTO = new StudiesByStudyTypeDTO();
			studiesByStudyTypeDTO.setStudyTypeName(studyType.getName());
			studiesByStudyTypeDTO.setStudies(studyService.studiesByStudyType(studyType));
			response.add(studiesByStudyTypeDTO);
		});
		return response;
	}

	public StudiesByMonthOfYearDTO listStudiesByMonthOfYear(Integer year) {
		StudiesByMonthOfYearDTO studiesByMonthOfYearDTO = new StudiesByMonthOfYearDTO();
		studiesByMonthOfYearDTO.setYear(year.toString());
		List<Integer> months = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12));
		months.forEach(month -> {
			StudiesByMonthDTO studiesByMonthDTO = new StudiesByMonthDTO();
			studiesByMonthDTO.setMonth(Months.MONTHS_IN_SPANISH.get(month));
			studiesByMonthDTO.setStudies(studyService.studiesByMonthOfYear(month,year));
			studiesByMonthOfYearDTO.getStudiesByMonth().add(studiesByMonthDTO);
		});
		return studiesByMonthOfYearDTO;
	}

  public List<Integer> listYearsWithStudies() {
		return studyService.yearsWithStudies();
  }
}
