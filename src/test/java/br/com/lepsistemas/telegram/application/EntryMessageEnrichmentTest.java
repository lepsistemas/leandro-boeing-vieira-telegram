package br.com.lepsistemas.telegram.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.lepsistemas.telegram.domain.model.EntryMessage;

@ExtendWith(MockitoExtension.class)
public class EntryMessageEnrichmentTest {
	
	private EntryMessageEnrichment enrichment;
	
	@Mock
	private DiscoverSpeakerRepository repository;
	
	@BeforeEach
	public void setUp() {
		this.enrichment = new EntryMessageEnrichment(repository);
	}
	
	@Test
	public void should_enrich_entry_message() {
		EntryMessage entry = new EntryMessage(1L, "Hi Leandro! I'm Sara.");
		EntryMessage enrichedEntry = new EntryMessage(1L, "Hi Leandro! I'm Sara. ");
		enrichedEntry.speaker("Sara");
		when(this.repository.discover(entry)).thenReturn(enrichedEntry);
		
		EntryMessage result = this.enrichment.message(entry);
		
		assertThat(result).isEqualTo(enrichedEntry);
	}

}
