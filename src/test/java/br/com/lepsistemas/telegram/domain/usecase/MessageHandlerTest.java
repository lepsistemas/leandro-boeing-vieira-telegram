package br.com.lepsistemas.telegram.domain.usecase;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.lepsistemas.telegram.domain.model.EntryMessage;
import br.com.lepsistemas.telegram.domain.model.Intent;
import br.com.lepsistemas.telegram.domain.model.Output;
import br.com.lepsistemas.telegram.domain.model.ResponseMessage;

@ExtendWith(MockitoExtension.class)
public class MessageHandlerTest {
	
	private MessageHandler entry;
	
	@Mock
	private Bot bot;
	
	@Mock
	private IntentRecognition recognition;
	
	@Mock
	private IntentThreshold threshold;
	
	@BeforeEach
	public void setUp() {
		this.entry = new MessageHandler(this.bot, this.recognition, this.threshold);
	}
	
	@Test
	public void should_just_return_when_message_is_start() {
		EntryMessage entry = new EntryMessage(1L, "/start");
		this.entry.handle(entry);
		
		verify(this.recognition, never()).identify("/start");
		verify(this.threshold, never()).verify(anyList());
	}
	
	@Test
	public void should_send_message() {
		Output output = new Output();
		Intent intent = new Intent("greetings", 0.92);
		output.addIntent(intent);
		output.addText("Hey!");
		when(this.recognition.identify("Hi!")).thenReturn(output);
//		when(this.threshold.verify(asList(intent))).thenReturn(intent);
		
		EntryMessage entry = new EntryMessage(1L, "Hi!");
		this.entry.handle(entry);
		
		ResponseMessage message = new ResponseMessage(1L, "Hey!");
		verify(this.bot).send(message);
	}

}
