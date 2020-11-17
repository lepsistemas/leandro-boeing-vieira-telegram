package br.com.lepsistemas.telegram.domain.usecase;

import java.util.ArrayList;
import java.util.List;

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

	public List<ResponseMessage> to(EntryMessage entry) {
		AnswerRecruiter.log.info("--- Received: {} ---", entry);
		if (entry.text().startsWith(COMMAND_BOT_STARTING_TEXT)) {
			return null;
		}
		
		EntryMessage normalizedEntry = new EntryMessage(entry.id(), entry.sender(), entry.text().replaceAll("\n", " ").replaceAll("\r", " ").trim());
		List<EnrichedMessage> enrichedMessages = this.nlp.understand(normalizedEntry);
		AnswerRecruiter.log.info("--- Enriched: {} ---", enrichedMessages);
		
		// Depending on the enriched message, I would like to answer accordingly
		
		List<ResponseMessage> messages = new ArrayList<>();
		for (EnrichedMessage enriched : enrichedMessages) {
			ResponseMessage message = new ResponseMessage(entry.id(), enriched.response());
			AnswerRecruiter.log.info("--- Response: {} ---", message);
			
			ResponseMessage messageWithEmoji = this.emoji.interpolate(message);
			AnswerRecruiter.log.info("--- Emoji Response: {} ---", messageWithEmoji);
			
			messages.add(messageWithEmoji);
			
			if (messageWithEmoji.text() != null) {
				this.messaging.send(messageWithEmoji);
			}
		}
		
		return messages;
	}

}
