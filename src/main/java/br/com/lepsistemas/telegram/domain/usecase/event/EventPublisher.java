package br.com.lepsistemas.telegram.domain.usecase.event;

import br.com.lepsistemas.telegram.domain.model.event.Event;

public interface EventPublisher<E extends Event<?>> {

	void publish(E event);

}
