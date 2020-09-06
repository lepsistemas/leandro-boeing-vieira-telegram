package br.com.lepsistemas.telegram.domain.model;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(exclude = "confidence")
public class Entity {
	
	private String key;
	private String value;
	private Double confidence;
	
	public Entity(String key, String value, Double confidence) {
		this.key = key;
		this.value = value;
		this.confidence = confidence;
	}
	
	public String key() {
		return this.key;
	}
	
	public String value() {
		return this.value;
	}
	
	public Double confidence() {
		return this.confidence;
	}

}
