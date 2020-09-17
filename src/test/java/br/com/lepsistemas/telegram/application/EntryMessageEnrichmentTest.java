package br.com.lepsistemas.telegram.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.lepsistemas.telegram.domain.model.EntryMessage;

@ExtendWith(MockitoExtension.class)
public class EntryMessageEnrichmentTest {
	
	private EntryMessageEnrichment enrichment;
	
	@BeforeEach
	public void setUp() {
		this.enrichment = new EntryMessageEnrichment();
	}
	
	@Test
	public void should_enrich_entry_message() {
		EntryMessage entry = new EntryMessage(1L, "Hi Leandro! I'm Sara. ");
		
		EntryMessage result = this.enrichment.message(entry);
		
		assertThat(result.id()).isEqualTo(1L);
		assertThat(result.text()).isEqualTo("Hi Leandro! I'm Sara.");
	}

}
