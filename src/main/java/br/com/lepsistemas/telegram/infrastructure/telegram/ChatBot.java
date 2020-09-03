package br.com.lepsistemas.telegram.infrastructure.telegram;

import org.springframework.beans.factory.annotation.Autowired;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.lepsistemas.telegram.domain.model.EntryMessage;
import br.com.lepsistemas.telegram.domain.usecase.Bot;

public class ChatBot implements Bot {
	
	private TelegramBot bot;

	@Override
	public void send(EntryMessage message) {
		SendMessage sendMessage = new SendMessage(message.id(), "You typed: " + message.text());
		this.bot.execute(sendMessage);
	}
	
	@Autowired
	public void setBot(TelegramBot bot) {
		this.bot = bot;
	}

}
