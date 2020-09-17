package br.com.lepsistemas.telegram.domain.usecase;

import br.com.lepsistemas.telegram.domain.model.EnrichedMessage;
import br.com.lepsistemas.telegram.domain.model.EntryMessage;
import br.com.lepsistemas.telegram.domain.model.ResponseMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageHandler {
	
	private static final String COMMAND_BOT_STARTING_TEXT = "/";
	
	private Bot bot;
	private EntryMessageEnrichment enrich;
	private EmojiInterpreter emoji;
	
	public MessageHandler(Bot bot, EntryMessageEnrichment enrich, EmojiInterpreter emoji) {
		this.bot = bot;
		this.enrich = enrich;
		this.emoji = emoji;
	}

	public ResponseMessage handle(EntryMessage entry) {
		MessageHandler.log.info("--- ChatId: {} - {} ---", entry.id(), entry);
		if (entry.text().startsWith(COMMAND_BOT_STARTING_TEXT)) {
			return null;
		}
		EnrichedMessage enriched = this.enrich.message(entry);
		MessageHandler.log.info("--- ChatId: {} - {} ---", enriched.entry().id(), enriched);
		
		// Depending on the enriched message, I would like to answer accordingly
		// For now I'm only returning what the user typed
		
		ResponseMessage message = new ResponseMessage(entry.id(), enriched.response());
		MessageHandler.log.info("--- ChatId: {} - {} ---", message.id(), message);
		ResponseMessage messageWithEmoji = this.emoji.interpret(message);
		MessageHandler.log.info("--- ChatId: {} - {} ---", message.id(), message);
		
		if (message.text() != null) {
			MessageHandler.log.info("--- Sending... ChatId: {} - {} ---", message.id(), message);
			this.bot.send(messageWithEmoji);
		}
		
		return message;
	}

}
