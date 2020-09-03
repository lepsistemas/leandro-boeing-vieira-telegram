package br.com.lepsistemas.telegram.domain.usecase;

import br.com.lepsistemas.telegram.domain.model.EntryMessage;

public class EntryUpdate {
	
	private Bot bot;
	
	public EntryUpdate(Bot bot) {
		this.bot = bot;
	}

	public void handle(EntryMessage message) {
		this.bot.send(message);
	}

}
