package br.com.lepsistemas.telegram.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.lepsistemas.telegram.domain.exception.NoIntentRecognizedException;
import br.com.lepsistemas.telegram.domain.model.Intent;
import br.com.lepsistemas.telegram.domain.model.Output;

@ExtendWith(MockitoExtension.class)
public class IntentRecognitionTest {
	
	private IntentRecognition recognition;
	
	@Mock
	private DialogFlowRepository repository;
	
	@BeforeEach
	public void setUp() {
		this.recognition = new IntentRecognition(this.repository);
	}
	
	@Test
	public void should_identify_intents() {
		Intent intent1 = new Intent("intent1", 0.92);
		Intent intent2 = new Intent("intent2", 0.8);
		Output output = new Output();
		output.addIntent(intent1);
		output.addIntent(intent2);
		when(this.repository.forMessage("Hi!")).thenReturn(output);
		
		Output result = this.recognition.identify("Hi!");
		
		assertThat(result.hasIntent()).isTrue();
	}
	
	@Test
	public void should_throw_exception_if_does_not_recognize_any_intent() {
		Output output = new Output();
		when(this.repository.forMessage("Hi!")).thenReturn(output);
		assertThatThrownBy(() -> {
			this.recognition.identify("Hi!");
		}).isInstanceOf(NoIntentRecognizedException.class);
	}

}
