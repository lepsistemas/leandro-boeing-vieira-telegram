package br.com.lepsistemas.telegram.domain.usecase;

import br.com.lepsistemas.telegram.domain.model.EnrichedMessage;
import br.com.lepsistemas.telegram.domain.model.EntryMessage;

public interface NaturalLanguageProcessingEnrichment {
	
	EnrichedMessage understand(EntryMessage entry);

}
