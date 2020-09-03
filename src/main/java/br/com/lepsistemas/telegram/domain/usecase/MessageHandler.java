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
		ResponseMessage preResponse = new ResponseMessage(entry.id(), entry.text());
		ResponseMessage postResponse = this.prepare.prepare(preResponse);
		this.bot.send(postResponse);
	}

}
