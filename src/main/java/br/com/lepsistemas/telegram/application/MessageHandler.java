package br.com.lepsistemas.telegram.application;

import br.com.lepsistemas.telegram.domain.model.EntryMessage;
import br.com.lepsistemas.telegram.domain.model.ResponseMessage;

public class MessageHandler {
	
	private static final String COMMAND_BOT_STARTING_TEXT = "/";
	
	private Bot bot;
	private EntryMessageEnrichment enrich;
	
	public MessageHandler(Bot bot, EntryMessageEnrichment enrich) {
		this.bot = bot;
		this.enrich = enrich;
	}

	public ResponseMessage handle(EntryMessage entry) {
		if (entry.text().startsWith(COMMAND_BOT_STARTING_TEXT)) {
			return null;
		}
		EntryMessage enrichedEntry = this.enrich.message(entry);
		
		ResponseMessage message = new ResponseMessage(entry.id(), enrichedEntry.text());
		this.bot.send(message);
		
		return message;
	}

}
