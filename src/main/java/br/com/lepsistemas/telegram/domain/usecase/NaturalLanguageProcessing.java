package br.com.lepsistemas.telegram.domain.usecase;

import java.util.List;

import br.com.lepsistemas.telegram.domain.model.EnrichedMessage;
import br.com.lepsistemas.telegram.domain.model.EntryMessage;

public interface NaturalLanguageProcessing {
	
	List<EnrichedMessage> understand(EntryMessage entry);

}
