package br.com.lepsistemas.telegram.domain.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(exclude = "confidence")
@ToString
public class Intent {
	
	private String value;
	private Double confidence;
	
	public Intent(String value, Double confidence) {
		this.value = value;
		this.confidence = confidence;
	}

}
