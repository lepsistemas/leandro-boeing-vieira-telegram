package br.com.lepsistemas.telegram.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import br.com.lepsistemas.telegram.domain.model.ResponseMessage;

public class EmojiInterpreterTest {
	
	@Test
	public void should_replace_emoji() {
		EmojiInterpreter emoji = new EmojiInterpreter();
		
		ResponseMessage message = new ResponseMessage(1L, "Hi :)");
		ResponseMessage result = emoji.interpret(message);
		
		assertThat(result.text()).isEqualTo("Hi \u1F609");
	}

}
