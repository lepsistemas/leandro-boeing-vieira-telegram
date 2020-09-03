package br.com.lepsistemas.telegram.domain.usecase;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.lepsistemas.telegram.domain.model.EntryMessage;

@ExtendWith(MockitoExtension.class)
public class EntryUpdateTest {
	
	private EntryUpdate entry;
	
	@Mock
	private Bot bot;
	
	@BeforeEach
	public void setUp() {
		this.entry = new EntryUpdate(this.bot);
	}
	
	@Test
	public void should_send_message() {
		EntryMessage message = new EntryMessage(1L, "Hi!");
		
		this.entry.handle(message);
		
		verify(this.bot).send(message);
	}

}
