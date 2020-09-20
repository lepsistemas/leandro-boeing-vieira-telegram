package br.com.lepsistemas.telegram.domain.usecase;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import br.com.lepsistemas.telegram.domain.model.ResponseMessage;

public class EmojiInterpolation {
	
	private Map<String, String> emojis;
	
	public EmojiInterpolation() {
		this.emojis = new HashMap<>();
		this.emojis.put(";\\)", "😉");
	}

	public ResponseMessage interpolate(ResponseMessage message) {
		String text = message.text();
		for(Entry<String, String> emoji : this.emojis.entrySet()) {
			text = text.replaceAll(emoji.getKey(), emoji.getValue());
		}
		
		return new ResponseMessage(message.id(), text);
	}

}