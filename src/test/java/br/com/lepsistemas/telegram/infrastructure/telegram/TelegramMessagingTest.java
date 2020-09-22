package br.com.lepsistemas.telegram.infrastructure.telegram;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.lepsistemas.telegram.domain.model.ResponseMessage;

@ExtendWith(MockitoExtension.class)
public class TelegramMessagingTest {
	
	private TelegramMessaging bot;
	
	@Mock
	private TelegramBot telegram;
	
	@BeforeEach
	public void setUp() {
		this.bot = new TelegramMessaging();
		this.bot.setBot(telegram);
	}
	
	@Test
	public void should_send_message() {
		ResponseMessage message = new ResponseMessage(1L, "Hi!");
		this.bot.send(message);
		
		verify(this.telegram).execute(Mockito.any(SendMessage.class));
	}

}
