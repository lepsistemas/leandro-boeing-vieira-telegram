package br.com.lepsistemas.telegram.domain.usecase;

import br.com.lepsistemas.telegram.domain.model.EnrichedMessage;
import br.com.lepsistemas.telegram.domain.model.EntryMessage;

public class EntryMessageEnrichment {
	
	private NaturalLanguageProcessingService nlpEnrichment;

	public EntryMessageEnrichment(NaturalLanguageProcessingService nlpEnrichment) {
		this.nlpEnrichment = nlpEnrichment;
	}
	
	public EnrichedMessage message(EntryMessage entry) {
		String normalizedText = entry.text().replaceAll("\n", " ").replaceAll("\r", " ").trim();
		
		EntryMessage normalizedEntry = new EntryMessage(entry.id(), normalizedText);
		EnrichedMessage enrichedEntry = this.nlpEnrichment.understand(normalizedEntry);
		
		return enrichedEntry;
	}

}
