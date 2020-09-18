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
	
	public String value() {
		return this.value;
	}
	
	public Double confidence() {
		return this.confidence;
	}

}
