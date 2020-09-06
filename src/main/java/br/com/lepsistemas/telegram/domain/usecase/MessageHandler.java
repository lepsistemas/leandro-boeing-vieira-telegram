package br.com.lepsistemas.telegram.domain.usecase;

import br.com.lepsistemas.telegram.domain.model.EntryMessage;
import br.com.lepsistemas.telegram.domain.model.Output;
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
		if (entry.text().startsWith(COMMAND_BOT_STARTING_TEXT)) {
			return;
		}
		String normalizedText = entry.text().replaceAll("\n", "").replaceAll("\r", "").trim();
		Output output = this.recognition.identify(normalizedText);
//		Intent intent = this.threshold.verify(intents);
		
		ResponseMessage message = new ResponseMessage(entry.id(), output.firstText());
		this.bot.send(message);
	}

}
