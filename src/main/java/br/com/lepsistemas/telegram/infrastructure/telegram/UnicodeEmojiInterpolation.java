package br.com.lepsistemas.telegram.infrastructure.telegram;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import br.com.lepsistemas.telegram.domain.model.ResponseMessage;
import br.com.lepsistemas.telegram.domain.usecase.EmojiInterpolation;

public class UnicodeEmojiInterpolation implements EmojiInterpolation {

	private Map<String, String> emojis;
	
	public UnicodeEmojiInterpolation() {
		this.emojis = new HashMap<>();
		this.emojis.put(";\\)", "😉");
		this.emojis.put(";\'\\(", "😥");
	}

	public ResponseMessage interpolate(ResponseMessage message) {
		if (message.text() == null) {
			return message;
		}
		String text = message.text();
		for(Entry<String, String> emoji : this.emojis.entrySet()) {
			text = text.replaceAll(emoji.getKey(), emoji.getValue());
		}
		
		return new ResponseMessage(message.id(), text);
	}

}
