package br.com.lepsistemas.telegram.domain.usecase;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.lepsistemas.telegram.domain.model.EnrichedMessage;
import br.com.lepsistemas.telegram.domain.model.EntryMessage;
import br.com.lepsistemas.telegram.domain.model.ResponseMessage;

@ExtendWith(MockitoExtension.class)
public class AnswerRecruiterTest {
	
	private AnswerRecruiter entry;
	
	@Mock
	private Messaging bot;
	
	@Mock
	private NaturalLanguageProcessing nlp;

	@Mock
	private EmojiInterpolation emoji;
	
	@BeforeEach
	public void setUp() {
		this.entry = new AnswerRecruiter(this.bot, this.nlp, this.emoji);
	}
	
	@Test
	public void should_just_return_when_message_is_start() {
		EntryMessage entry = new EntryMessage(1L, "/start");
		List<ResponseMessage> result = this.entry.to(entry);
		
		assertThat(result).isNull();
		
		verify(this.nlp, never()).understand(any(EntryMessage.class));
		verify(this.bot, never()).send(any(ResponseMessage.class));
	}
	
	@Test
	public void should_send_message() {
		EntryMessage entry = new EntryMessage(1L, "Hi!");
		EnrichedMessage enriched = new EnrichedMessage(entry);
		enriched.response("Hey!");
		when(this.nlp.understand(entry)).thenReturn(asList(enriched));
		
		ResponseMessage message = new ResponseMessage(1L, "Hey!");
		when(this.emoji.interpolate(message)).thenReturn(message);
		
		List<ResponseMessage> result = this.entry.to(entry);
		
		assertThat(result).hasSize(1);
		assertThat(result.get(0)).isEqualTo(message);
		verify(this.bot).send(message);
	}
	
	@Test
	public void should_not_send_null_message() {
		EntryMessage entry = new EntryMessage(1L, "Hi!");
		EnrichedMessage enriched = new EnrichedMessage(entry);
		when(this.nlp.understand(entry)).thenReturn(asList(enriched));
		
		List<ResponseMessage> result = this.entry.to(entry);
		ResponseMessage message = new ResponseMessage(1L, null);
		
		assertThat(result).hasSize(1);
		assertThat(result.get(0)).isNull();
		verify(this.bot, never()).send(message);
	}

}
