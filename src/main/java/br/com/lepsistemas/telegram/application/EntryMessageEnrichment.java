package br.com.lepsistemas.telegram.application;

import br.com.lepsistemas.telegram.domain.model.EntryMessage;

public class EntryMessageEnrichment {
	
	private DiscoverSpeakerRepository speakerRepository;

	public EntryMessageEnrichment(DiscoverSpeakerRepository enrichmentRepository) {
		this.speakerRepository = enrichmentRepository;
	}

	public EntryMessage message(EntryMessage entry) {
		String normalizedText = entry.text().replaceAll("\n", "").replaceAll("\r", "").trim();
		EntryMessage speakerEntry = this.speakerRepository.discover(new EntryMessage(entry.id(), normalizedText));
		return speakerEntry;
	}

}
