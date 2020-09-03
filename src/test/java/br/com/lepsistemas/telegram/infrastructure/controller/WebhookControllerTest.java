package br.com.lepsistemas.telegram.infrastructure.controller;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;

import br.com.lepsistemas.telegram.domain.model.EntryMessage;
import br.com.lepsistemas.telegram.domain.usecase.MessageHandler;
import br.com.lepsistemas.telegram.infrastructure.convert.UpdateToChatMessage;
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
		this.controller.webhook("{update_id:1, message: {message_id: 2, text: 'Hi!', chat: {id: 3}}}");
		
		Update update = BotUtils.parseUpdate("{update_id:1, message: {message_id: 2, text: 'Hi!', chat: {id: 3}}}");
		EntryMessage message = UpdateToChatMessage.convert(update);
		
		verify(this.entry).handle(message);
	}

}
