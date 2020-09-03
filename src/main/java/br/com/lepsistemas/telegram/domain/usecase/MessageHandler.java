package br.com.lepsistemas.telegram.domain.usecase;

import br.com.lepsistemas.telegram.domain.model.EntryMessage;
import br.com.lepsistemas.telegram.domain.model.ResponseMessage;

public class MessageHandler {
	
	private Bot bot;
	
	public MessageHandler(Bot bot) {
		this.bot = bot;
	}

	public void handle(EntryMessage entry) {
		ResponseMessage response = new ResponseMessage(entry.id(), "You typed: " + entry.text());
		this.bot.send(response);
	}

}
