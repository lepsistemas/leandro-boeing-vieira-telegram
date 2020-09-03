package br.com.lepsistemas.telegram.domain.usecase;

import br.com.lepsistemas.telegram.domain.model.EntryMessage;
import br.com.lepsistemas.telegram.domain.model.ResponseMessage;

public class MessageHandler {
	
	private Bot bot;
	private PrepareResponseMessage prepare;
	
	public MessageHandler(Bot bot, PrepareResponseMessage prepare) {
		this.bot = bot;
		this.prepare = prepare;
	}

	public void handle(EntryMessage entry) {
		ResponseMessage response = new ResponseMessage(entry.id(), entry.text());
		ResponseMessage output = this.prepare.prepare(response);
		this.bot.send(output);
	}

}
