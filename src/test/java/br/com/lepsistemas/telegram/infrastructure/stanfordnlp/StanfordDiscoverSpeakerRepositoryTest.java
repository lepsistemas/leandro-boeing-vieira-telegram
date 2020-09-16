package br.com.lepsistemas.telegram.infrastructure.stanfordnlp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.lepsistemas.telegram.domain.model.EntryMessage;

public class StanfordDiscoverSpeakerRepositoryTest {
	
	private StanfordDiscoverSpeakerRepository repository;
	
	@BeforeEach
	public void setUp() {
		this.repository = new StanfordDiscoverSpeakerRepository();
	}

	@Test
	public void should_understand_speakers_name() {
		EntryMessage input = new EntryMessage(1L, "My name is Lep");
		EntryMessage output = this.repository.discover(input);
		
		assertThat(output.speaker()).isEqualTo("Lep");
	}
	
	@Test
	public void should_ignore_my_own_name() {
		EntryMessage input = new EntryMessage(1L, "Hi Leandro. My name is Lep");
		EntryMessage output = this.repository.discover(input);
		
		assertThat(output.speaker()).isEqualTo("Lep");
	}

}
