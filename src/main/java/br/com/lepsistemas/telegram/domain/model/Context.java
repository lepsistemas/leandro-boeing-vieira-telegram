package br.com.lepsistemas.telegram.domain.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(exclude = "value")
@ToString
public class Context {
	
	private String key;
	private Object value;
	
	public Context(String key, Object value) {
		this.key = key;
		this.value = value;
	}

}
