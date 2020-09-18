package br.com.lepsistemas.telegram.domain.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class EnrichedMessage {
	
	private EntryMessage entry;
	private Set<Context> contexts;
	private Set<Intent> intents;
	private String response;
	
	public EnrichedMessage(EntryMessage entry) {
		this.entry = entry;
		this.contexts = new HashSet<>();
		this.intents = new HashSet<>();
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
	
	public void addContext(String key, Object value) {
		this.contexts.add(new Context(key, value));
	}
	
	public List<Context> contexts() {
		return new ArrayList<>(this.contexts);
	}

	public void addIntent(String value, Double confidence) {
		this.intents.add(new Intent(value, confidence));
	}
	
	public List<Intent> intents() {
		return new ArrayList<>(this.intents);
	}

}
