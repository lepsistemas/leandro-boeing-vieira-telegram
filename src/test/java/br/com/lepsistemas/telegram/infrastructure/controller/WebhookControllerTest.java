package br.com.lepsistemas.telegram.infrastructure.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;

import br.com.lepsistemas.telegram.application.MessageHandler;
import br.com.lepsistemas.telegram.domain.model.EntryMessage;
import br.com.lepsistemas.telegram.domain.model.ResponseMessage;
import br.com.lepsistemas.telegram.infrastructure.convert.UpdateToEntryMessage;
import br.com.lepsistemas.telegram.infrastructure.spring.controller.WebhookController;

@ExtendWith(MockitoExtension.class)
public class WebhookControllerTest {
	
	private WebhookController controller;
	
	@Mock
	private MessageHandler entry;
	
	@BeforeEach
	public void setUp() {
		this.controller = new WebhookController();
		this.controller.setEntryUpdate(entry);
	}
	
	@Test
	public void should_call_entry_update_on_webhook() {
		String request = "{update_id:1, message: {message_id: 2, text: 'Hi!', chat: {id: 3}}}";
		Update update = BotUtils.parseUpdate(request);
		EntryMessage message = UpdateToEntryMessage.convert(update);
		ResponseMessage response = new ResponseMessage(1L, "Hi!");
		when(this.entry.handle(message)).thenReturn(response);
		
		ResponseEntity<String> result = this.controller.webhook(request);
		
		assertThat(result.getBody()).isEqualTo("Hi!");
	}

}
