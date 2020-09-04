package br.com.lepsistemas.telegram.domain.usecase;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.lepsistemas.telegram.domain.exception.NoIntentRecognizedException;
import br.com.lepsistemas.telegram.domain.model.Intent;

@ExtendWith(MockitoExtension.class)
public class IntentRecognitionTest {
	
	private IntentRecognition recognition;
	
	@Mock
	private IntentRepository repository;
	
	@BeforeEach
	public void setUp() {
		this.recognition = new IntentRecognition(this.repository);
	}
	
	@Test
	public void should_identify_intents() {
		Intent intent1 = new Intent("intent1", 0.92);
		Intent intent2 = new Intent("intent2", 0.8);
		when(this.repository.by("Hi!")).thenReturn(asList(intent1, intent2));
		
		List<Intent> intents = this.recognition.identify("Hi!");
		
		assertThat(intents.size()).isEqualTo(2);
	}
	
	@Test
	public void should_throw_exception_if_does_not_recognize_any_intent() {
		assertThatThrownBy(() -> {
			this.recognition.identify("Hi!");
		}).isInstanceOf(NoIntentRecognizedException.class);
	}

}
