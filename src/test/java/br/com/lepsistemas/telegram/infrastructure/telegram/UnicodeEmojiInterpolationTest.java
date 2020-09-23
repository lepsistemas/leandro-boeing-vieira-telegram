package br.com.lepsistemas.telegram.infrastructure.telegram;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import br.com.lepsistemas.telegram.domain.model.ResponseMessage;
import br.com.lepsistemas.telegram.domain.usecase.EmojiInterpolation;

public class UnicodeEmojiInterpolationTest {
	
	@Test
	public void should_replace_emojis() {
		EmojiInterpolation emoji = new UnicodeEmojiInterpolation();
		
		ResponseMessage message = new ResponseMessage(1L, "Hi ;) ;'(");
		ResponseMessage result = emoji.interpolate(message);
		
		assertThat(result.text()).isEqualTo("Hi 😉 😥");
	}
	
	@Test
	public void should_not_interpolate_if_text_is_null() {
		EmojiInterpolation emoji = new UnicodeEmojiInterpolation();
		
		ResponseMessage message = new ResponseMessage(1L, null);
		ResponseMessage result = emoji.interpolate(message);
		
		assertThat(result.text()).isNull();
	}

}
