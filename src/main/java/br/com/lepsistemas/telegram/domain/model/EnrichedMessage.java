package br.com.lepsistemas.telegram.domain.model;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EnrichedMessage {
	
	private EntryMessage entry;
	private String response;
	
	public EnrichedMessage(EntryMessage entry) {
		this.entry = entry;
	}
	
	public EntryMessage entry() {
		return this.entry;
	}

	public void response(String response) {
		this.response = response;
		
	}

	public String response() {
		return this.response;
	}

}
