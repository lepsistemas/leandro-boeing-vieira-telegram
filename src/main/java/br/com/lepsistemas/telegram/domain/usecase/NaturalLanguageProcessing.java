package br.com.lepsistemas.telegram.domain.usecase;

import br.com.lepsistemas.telegram.domain.model.EnrichedMessage;
import br.com.lepsistemas.telegram.domain.model.EntryMessage;

public interface NaturalLanguageProcessing {
	
	EnrichedMessage understand(EntryMessage entry);

}
