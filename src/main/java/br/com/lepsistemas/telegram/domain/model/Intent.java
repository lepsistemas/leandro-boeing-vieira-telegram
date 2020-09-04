package br.com.lepsistemas.telegram.domain.model;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Intent {
	
	private String key;
	private Double confidence;
	
	public Intent(String key, Double confidence) {
		this.key = key;
		this.confidence = confidence;
	}
	
	public String key() {
		return this.key;
	}
	
	public Double confidence() {
		return this.confidence;
	}

}
