package br.com.lepsistemas.telegram.infrastructure.event;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.kafka.common.serialization.Deserializer;

import com.google.gson.Gson;

import br.com.lepsistemas.telegram.domain.model.event.ResponseMessageEvent;

public class ResponseMessageEventDeserializer implements Deserializer<ResponseMessageEvent> {

	@Override
	public ResponseMessageEvent deserialize(String topic, byte[] data) {
		Charset charset = StandardCharsets.UTF_8;
		return new Gson().fromJson(new String(data, charset), ResponseMessageEvent.class);
	}

}
