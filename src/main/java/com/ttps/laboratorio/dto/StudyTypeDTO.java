package com.ttps.laboratorio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudyTypeDTO {

    @NotNull(message = "Study type id is required")
    private long id;

    @NotNull(message = "Study type name is required")
    private String name;

    private String consent;
}
