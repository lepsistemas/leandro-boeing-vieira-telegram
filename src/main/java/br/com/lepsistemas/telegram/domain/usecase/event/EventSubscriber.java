package br.com.lepsistemas.telegram.domain.usecase.event;

import java.util.List;

import br.com.lepsistemas.telegram.domain.model.event.Event;

public interface EventSubscriber<E extends Event<?>> {
	
	List<E> subscribe();

}
