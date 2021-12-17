package com.ttps.laboratorio.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ttps.laboratorio.entity.StudyType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyItemResponseDTO {

	private Long id;

	private LocalDateTime createdAt;

	private BigDecimal budget;

	private BigDecimal extractionAmount;

	private Boolean paidExtractionAmount;

	private Boolean delay = false;

	private String firstName;

	private String lastName;

	private StudyType type;

	private StudyStatusResponseDTO actualStatus;
}
