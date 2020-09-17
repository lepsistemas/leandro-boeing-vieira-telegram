package br.com.lepsistemas.telegram.domain.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class ResponseMessage {
	
	private Long id;
	private String text;
	
	public ResponseMessage(long id, String text) {
		this.id = id;
		this.text = text;
	}
	
	public Long id() {
		return this.id;
	}
	
	public String text() {
		return this.text;
	}

}
