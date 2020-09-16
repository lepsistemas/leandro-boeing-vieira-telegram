package br.com.lepsistemas.telegram.domain.model;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EntryMessage {
	
	private Long id;
	private String text;
	private String speaker;
	
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
	
	public void speaker(String speaker) {
		this.speaker = speaker;
	}
	
	public String speaker() {
		return this.speaker;
	}

}
