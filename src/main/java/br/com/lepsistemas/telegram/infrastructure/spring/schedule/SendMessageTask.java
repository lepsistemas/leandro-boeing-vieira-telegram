package br.com.lepsistemas.telegram.infrastructure.spring.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.lepsistemas.telegram.domain.model.ResponseMessage;
import br.com.lepsistemas.telegram.domain.model.event.ResponseMessageEvent;
import br.com.lepsistemas.telegram.domain.usecase.Messaging;
import br.com.lepsistemas.telegram.domain.usecase.event.EventSubscriber;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SendMessageTask {
	
	private EventSubscriber<ResponseMessageEvent> subscriber;
	private Messaging messaging;

	@Scheduled(fixedRate = 10000)
	public void messages() {
		List<ResponseMessageEvent> events = this.subscriber.subscribe();
		for (ResponseMessageEvent event : events) {
			ResponseMessage message = event.data();
			SendMessageTask.log.info("--- Sending message: {} ---", message);
			this.messaging.send(message);
		}
	}
	
	@Autowired
	public void setMessaging(Messaging messaging) {
		this.messaging = messaging;
	}
	
	@Autowired
	public void setSubscriber(EventSubscriber<ResponseMessageEvent> subscriber) {
		this.subscriber = subscriber;
	}

}
