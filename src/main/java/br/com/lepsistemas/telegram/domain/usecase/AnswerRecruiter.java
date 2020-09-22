package br.com.lepsistemas.telegram.domain.usecase;

import br.com.lepsistemas.telegram.domain.model.EnrichedMessage;
import br.com.lepsistemas.telegram.domain.model.EntryMessage;
import br.com.lepsistemas.telegram.domain.model.ResponseMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AnswerRecruiter {
	
	private static final String COMMAND_BOT_STARTING_TEXT = "/";
	
	private Messaging messaging;
	private NaturalLanguageProcessing nlp;
	private EmojiInterpolation emoji;
	
	public AnswerRecruiter(Messaging messaging, NaturalLanguageProcessing nlp, EmojiInterpolation emoji) {
		this.messaging = messaging;
		this.nlp = nlp;
		this.emoji = emoji;
	}

	public ResponseMessage to(EntryMessage entry) {
		AnswerRecruiter.log.info("--- Received: {} ---", entry);
		if (entry.text().startsWith(COMMAND_BOT_STARTING_TEXT)) {
			return null;
		}
		
		EntryMessage normalizedEntry = new EntryMessage(entry.id(), entry.text().replaceAll("\n", " ").replaceAll("\r", " ").trim());
		EnrichedMessage enriched = this.nlp.understand(normalizedEntry);
		AnswerRecruiter.log.info("--- Enriched: {} ---", enriched);
		
		// Depending on the enriched message, I would like to answer accordingly
		
		ResponseMessage message = new ResponseMessage(entry.id(), enriched.response());
		AnswerRecruiter.log.info("--- Response: {} ---", message);
		
		ResponseMessage messageWithEmoji = this.emoji.interpolate(message);
		AnswerRecruiter.log.info("--- Emoji Response: {} ---", messageWithEmoji);
		
		if (message.text() != null) {
			AnswerRecruiter.log.info("--- Sending: {} ---", messageWithEmoji);
			this.messaging.send(messageWithEmoji);
		}
		
		return message;
	}

}
