package br.com.lepsistemas.telegram.domain.usecase;

import br.com.lepsistemas.telegram.domain.exception.NoIntentRecognizedException;
import br.com.lepsistemas.telegram.domain.model.Output;

public class IntentRecognition {
	
	private DialogFlowRepository repository;
	
	public IntentRecognition(DialogFlowRepository repository) {
		this.repository = repository;
	}

	public Output identify(String text) {
		Output output = this.repository.forMessage(text);
		if (!output.hasIntent()) {
			throw new NoIntentRecognizedException();
		}
		return output;
	}

}
