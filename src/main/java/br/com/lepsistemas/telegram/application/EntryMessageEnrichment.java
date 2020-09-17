package br.com.lepsistemas.telegram.application;

import br.com.lepsistemas.telegram.domain.model.EntryMessage;

public class EntryMessageEnrichment {
	
	public EntryMessage message(EntryMessage entry) {
		String normalizedText = entry.text().replaceAll("\n", "").replaceAll("\r", "").trim();
		return new EntryMessage(entry.id(), normalizedText);
	}

}
