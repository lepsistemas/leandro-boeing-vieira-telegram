package br.com.lepsistemas.telegram.infrastructure.telegram;

import org.springframework.beans.factory.annotation.Autowired;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.lepsistemas.telegram.application.Bot;
import br.com.lepsistemas.telegram.domain.model.ResponseMessage;

public class ChatBot implements Bot {
	
	private TelegramBot bot;

	@Override
	public void send(ResponseMessage message) {
		this.bot.execute(new SendMessage(message.id(), message.text()));
	}
	
	@Autowired
	public void setBot(TelegramBot bot) {
		this.bot = bot;
	}

}
