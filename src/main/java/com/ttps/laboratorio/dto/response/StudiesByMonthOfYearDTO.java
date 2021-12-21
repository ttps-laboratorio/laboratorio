package com.ttps.laboratorio.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudiesByMonthOfYearDTO {

	private String year;

	private List<StudiesByMonthDTO> studiesByMonth = new ArrayList<>();

}
