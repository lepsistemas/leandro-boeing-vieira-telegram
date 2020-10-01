package br.com.lepsistemas.telegram.infrastructure.spring.schedule;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.lepsistemas.telegram.domain.model.ResponseMessage;
import br.com.lepsistemas.telegram.domain.model.event.ResponseMessageEvent;
import br.com.lepsistemas.telegram.domain.usecase.Messaging;
import br.com.lepsistemas.telegram.domain.usecase.event.EventSubscriber;

@ExtendWith(MockitoExtension.class)
public class SendMessageTaskTest {
	
	@Mock
	private Messaging messaging;
	
	@Mock
	private EventSubscriber<ResponseMessageEvent> subscriber;

	@Test
	public void should_send_subscribed_messages() {
		SendMessageTask task = new SendMessageTask();
		task.setMessaging(messaging);
		task.setSubscriber(subscriber);
		
		ResponseMessage message = new ResponseMessage(1L, "Hey!");
		ResponseMessageEvent event = new ResponseMessageEvent(message);
		when(subscriber.subscribe()).thenReturn(asList(event));
		
		task.messages();
		
		verify(messaging).send(message);
		
	}

}
