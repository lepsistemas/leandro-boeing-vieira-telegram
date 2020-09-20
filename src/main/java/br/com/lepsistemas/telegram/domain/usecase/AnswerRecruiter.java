package br.com.lepsistemas.telegram.domain.usecase;

import br.com.lepsistemas.telegram.domain.model.EnrichedMessage;
import br.com.lepsistemas.telegram.domain.model.EntryMessage;
import br.com.lepsistemas.telegram.domain.model.ResponseMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AnswerRecruiter {
	
	private static final String COMMAND_BOT_STARTING_TEXT = "/";
	
	private Bot bot;
	private EntryMessageEnrichment enrich;
	private EmojiInterpolation emoji;
	
	public AnswerRecruiter(Bot bot, EntryMessageEnrichment enrich, EmojiInterpolation emoji) {
		this.bot = bot;
		this.enrich = enrich;
		this.emoji = emoji;
	}

	public ResponseMessage to(EntryMessage entry) {
		AnswerRecruiter.log.info("--- Received: {} ---", entry);
		if (entry.text().startsWith(COMMAND_BOT_STARTING_TEXT)) {
			return null;
		}
		EnrichedMessage enriched = this.enrich.message(entry);
		AnswerRecruiter.log.info("--- Enriched: {} ---", enriched);
		
		// Depending on the enriched message, I would like to answer accordingly
		
		ResponseMessage message = new ResponseMessage(entry.id(), enriched.response());
		AnswerRecruiter.log.info("--- Response: {} ---", message);
		
		ResponseMessage messageWithEmoji = this.emoji.interpret(message);
		AnswerRecruiter.log.info("--- Emoji Response: {} ---", messageWithEmoji);
		
		if (message.text() != null) {
			AnswerRecruiter.log.info("--- Sending: {} ---", messageWithEmoji);
			this.bot.send(messageWithEmoji);
		}
		
		return message;
	}

}
