package br.com.lepsistemas.telegram.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.lepsistemas.telegram.domain.model.EntryMessage;
import br.com.lepsistemas.telegram.domain.model.ResponseMessage;

@ExtendWith(MockitoExtension.class)
public class MessageHandlerTest {
	
	private MessageHandler entry;
	
	@Mock
	private Bot bot;
	
	@Mock
	private EntryMessageEnrichment enrich;
	
	@BeforeEach
	public void setUp() {
		this.entry = new MessageHandler(this.bot, this.enrich);
	}
	
	@Test
	public void should_just_return_when_message_is_start() {
		EntryMessage entry = new EntryMessage(1L, "/start");
		ResponseMessage result = this.entry.handle(entry);
		
		assertThat(result).isNull();
		
		verify(this.enrich, never()).message(any(EntryMessage.class));
		verify(this.bot, never()).send(any(ResponseMessage.class));
	}
	
	@Test
	public void should_send_message() {
		EntryMessage entry = new EntryMessage(1L, "Hi!");
		EntryMessage enrichedEntry = new EntryMessage(1L, "Enriched - Hi!");
		when(this.enrich.message(entry)).thenReturn(enrichedEntry);
		
		ResponseMessage result = this.entry.handle(entry);
		ResponseMessage message = new ResponseMessage(1L, "Enriched - Hi!");
		
		assertThat(result).isEqualTo(message);
		
		verify(this.bot).send(message);
	}

}
