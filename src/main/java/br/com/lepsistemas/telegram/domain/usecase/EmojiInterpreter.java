package br.com.lepsistemas.telegram.domain.usecase;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import br.com.lepsistemas.telegram.domain.model.ResponseMessage;

public class EmojiInterpreter {
	
	private Map<String, String> emojis;
	
	public EmojiInterpreter() {
		this.emojis = new HashMap<>();
		this.emojis.put(":\\)", "\\\\xF0\\\\x9F\\\\x98\\\\x89");
	}

	public ResponseMessage interpret(ResponseMessage message) {
		
		String text = message.text();
		for(Entry<String, String> emoji : this.emojis.entrySet()) {
			text = text.replaceAll(emoji.getKey(), emoji.getValue());
		}
		
		return new ResponseMessage(message.id(), text);
	}

}
