package br.com.lepsistemas.telegram.domain.usecase;

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
	private PrepareResponseMessage prepare;
	
	@BeforeEach
	public void setUp() {
		this.entry = new MessageHandler(this.bot, this.prepare);
	}
	
	@Test
	public void should_send_message() {
		ResponseMessage preResponse = new ResponseMessage(1L, "Hi!");
		ResponseMessage postResponse = new ResponseMessage(1L, "You typed: Hi!");
		when(this.prepare.prepare(preResponse)).thenReturn(postResponse);
		
		EntryMessage entry = new EntryMessage(1L, "Hi!");
		this.entry.handle(entry);
		
		verify(this.bot).send(postResponse);
	}

}
