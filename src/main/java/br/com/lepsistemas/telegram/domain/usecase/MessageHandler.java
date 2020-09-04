package br.com.lepsistemas.telegram.domain.usecase;

import java.util.List;

import br.com.lepsistemas.telegram.domain.model.EntryMessage;
import br.com.lepsistemas.telegram.domain.model.Intent;
import br.com.lepsistemas.telegram.domain.model.ResponseMessage;

public class MessageHandler {
	
	private static final String COMMAND_BOT_STARTING_TEXT = "/";
	
	private Bot bot;
	private IntentRecognition recognition;
//	private IntentThreshold threshold;
	
	public MessageHandler(Bot bot, IntentRecognition recognition, IntentThreshold threshold) {
		this.bot = bot;
		this.recognition = recognition;
//		this.threshold = threshold;
	}

	public void handle(EntryMessage entry) {
		if (COMMAND_BOT_STARTING_TEXT.equals(entry.text())) {
			return;
		}
		List<Intent> intents = this.recognition.identify(entry.text());
//		Intent intent = this.threshold.verify(intents);
		
		ResponseMessage message = new ResponseMessage(entry.id(), "I believe you mean to interact with me about " + intents.get(0).key());
		this.bot.send(message);
	}

}
