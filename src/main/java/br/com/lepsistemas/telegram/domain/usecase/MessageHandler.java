package br.com.lepsistemas.telegram.domain.usecase;

import br.com.lepsistemas.telegram.domain.model.EnrichedMessage;
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
		EnrichedMessage enriched = this.enrich.message(entry);
		
		// Depending on the enriched message, I would like to answer accordingly
		// For now I'm only returning what the user typed
		
		ResponseMessage message = new ResponseMessage(entry.id(), enriched.response());
		if (message.text() != null) {
			this.bot.send(message);
		}
		
		return message;
	}

}
