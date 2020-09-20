package br.com.lepsistemas.telegram.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.lepsistemas.telegram.domain.model.EnrichedMessage;
import br.com.lepsistemas.telegram.domain.model.EntryMessage;
import br.com.lepsistemas.telegram.domain.usecase.EntryMessageEnrichment;
import br.com.lepsistemas.telegram.domain.usecase.NaturalLanguageProcessingService;

@ExtendWith(MockitoExtension.class)
public class EntryMessageEnrichmentTest {
	
	private EntryMessageEnrichment enrichment;
	
	@Mock
	private NaturalLanguageProcessingService nlpEnrichment;
	
	@BeforeEach
	public void setUp() {
		this.enrichment = new EntryMessageEnrichment(this.nlpEnrichment);
	}
	
	@Test
	public void should_enrich_entry_message() {
		EntryMessage entry = new EntryMessage(1L, "Hi Leandro! I'm Sara.");
		EnrichedMessage enriched = new EnrichedMessage(entry);
		when(this.nlpEnrichment.understand(entry)).thenReturn(enriched);
		
		EnrichedMessage result = this.enrichment.message(entry);
		
		assertThat(result).isEqualTo(enriched);
		assertThat(result.entry()).isEqualTo(enriched.entry());
	}

}
