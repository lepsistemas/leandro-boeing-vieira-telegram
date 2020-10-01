package br.com.lepsistemas.telegram.infrastructure.event;

import org.apache.kafka.common.serialization.Serializer;

import com.google.gson.Gson;

import br.com.lepsistemas.telegram.domain.model.event.ResponseMessageEvent;

public class ResponseMessageEventSerializer implements Serializer<ResponseMessageEvent>{

	@Override
	public byte[] serialize(String topic, ResponseMessageEvent data) {
		return new Gson().toJson(data).getBytes();
	}

}
