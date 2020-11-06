package br.com.lepsistemas.telegram.domain.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class EntryMessage {
	
	private Long id;
	private String sender;
	private String text;
	
	public EntryMessage(long id, String sender, String text) {
		this.id = id;
		this.sender = sender;
		this.text = text;
	}
	
	public Long id() {
		return this.id;
	}
	
	public String sender() {
		return this.sender;
	}
	
	public String text() {
		return this.text;
	}
	
}
