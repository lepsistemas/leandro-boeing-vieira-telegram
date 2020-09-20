package br.com.lepsistemas.telegram.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import br.com.lepsistemas.telegram.domain.model.ResponseMessage;

public class EmojiInterpolationTest {
	
	@Test
	public void should_replace_emojis() {
		EmojiInterpolation emoji = new EmojiInterpolation();
		
		ResponseMessage message = new ResponseMessage(1L, "Hi ;) ;'(");
		ResponseMessage result = emoji.interpolate(message);
		
		assertThat(result.text()).isEqualTo("Hi 😉 😥");
	}

}
