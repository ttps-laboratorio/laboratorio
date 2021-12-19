package com.ttps.laboratorio.dto.request;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrlDTO {

	@NotBlank(message = "Url is required.")
	private String url;

	private List<Integer> failedSamples = new ArrayList<>();

}
