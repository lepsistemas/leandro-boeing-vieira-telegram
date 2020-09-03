package br.com.lepsistemas.telegram.domain.model;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EntryMessage {
	
	private Long id;
	private String text;
	
	public EntryMessage(long id, String text) {
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
