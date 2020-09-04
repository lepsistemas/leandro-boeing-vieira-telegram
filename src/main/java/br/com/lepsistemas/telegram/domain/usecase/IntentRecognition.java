package br.com.lepsistemas.telegram.domain.usecase;

import java.util.List;

import br.com.lepsistemas.telegram.domain.exception.NoIntentRecognizedException;
import br.com.lepsistemas.telegram.domain.model.Intent;

public class IntentRecognition {
	
	private IntentRepository repository;
	
	public IntentRecognition(IntentRepository repository) {
		this.repository = repository;
	}

	public List<Intent> identify(String text) {
		List<Intent> intents = this.repository.by(text);
		if (intents.isEmpty()) {
			throw new NoIntentRecognizedException();
		}
		return intents;
	}

}
