package com.ttps.laboratorio.dto.request;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnpaidStudiesDTO {

	private List<Long> unpaidStudies = new ArrayList<>();

}
