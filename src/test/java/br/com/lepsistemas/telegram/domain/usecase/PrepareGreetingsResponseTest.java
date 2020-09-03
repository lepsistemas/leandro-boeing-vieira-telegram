package br.com.lepsistemas.telegram.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import br.com.lepsistemas.telegram.domain.model.ResponseMessage;

public class PrepareGreetingsResponseTest {
	
	@Test
	public void should_create_greetings_response() {
		PrepareGreetingsResponse prepare = new PrepareGreetingsResponse();
		ResponseMessage input = new ResponseMessage(1L, "Hi!");
		ResponseMessage output = prepare.prepare(input);
		
		assertThat(output.id()).isEqualTo(1L);
		assertThat(output.text()).isEqualTo("You just typed: Hi!");
	}

}
