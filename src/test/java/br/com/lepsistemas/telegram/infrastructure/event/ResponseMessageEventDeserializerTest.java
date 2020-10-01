package br.com.lepsistemas.telegram.infrastructure.event;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import br.com.lepsistemas.telegram.domain.model.event.ResponseMessageEvent;

public class ResponseMessageEventDeserializerTest {
	
	@Test
	public void should_deserialize_response_message_event() {
		ResponseMessageEventDeserializer deserializer = new ResponseMessageEventDeserializer();
		
		String data = "{\"data\":{\"id\":1,\"text\":\"Hi!\"}}";
		
		ResponseMessageEvent result = deserializer.deserialize("TOPIC", data.getBytes());
		
		assertThat(result.data().id()).isEqualTo(1L);
		assertThat(result.data().text()).isEqualTo("Hi!");
		
		deserializer.close();
	}

}
