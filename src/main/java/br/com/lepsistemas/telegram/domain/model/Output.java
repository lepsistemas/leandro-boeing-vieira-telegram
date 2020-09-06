package br.com.lepsistemas.telegram.domain.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Output {
	
	private Set<Intent> intents;
	private Set<Entity> entities;
	private List<String> texts;
	
	public Output() {
		this.intents = new HashSet<>();
		this.entities = new HashSet<>();
		this.texts = new ArrayList<>();
	}
	
	public void addIntent(Intent intent) {
		this.intents.add(intent);
	}

	public void addEntity(Entity entity) {
		this.entities.add(entity);
	}

	public void addText(String text) {
		this.texts.add(text);
	}

	public boolean hasIntent() {
		return !this.intents.isEmpty();
	}

	public String firstText() {
		String response = "";
		for(String text : this.texts) {
			if (!text.isEmpty()) {
				response = text;
			}
		}
		return response;
	}

}
