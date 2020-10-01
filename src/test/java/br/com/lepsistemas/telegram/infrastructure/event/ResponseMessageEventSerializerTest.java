package br.com.lepsistemas.telegram.infrastructure.event;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import br.com.lepsistemas.telegram.domain.model.ResponseMessage;
import br.com.lepsistemas.telegram.domain.model.event.ResponseMessageEvent;

public class ResponseMessageEventSerializerTest {
	
	@Test
	public void should_serielize_response_message_event() {
		ResponseMessageEventSerializer serializer = new ResponseMessageEventSerializer();
		
		ResponseMessageEvent data = new ResponseMessageEvent(new ResponseMessage(1L, "Hi!"));
		byte[] result = serializer.serialize("TOPIC", data);
		
		assertThat(new String(result)).isEqualTo("{\"data\":{\"id\":1,\"text\":\"Hi!\"}}");
		
		serializer.close();
	}

}
