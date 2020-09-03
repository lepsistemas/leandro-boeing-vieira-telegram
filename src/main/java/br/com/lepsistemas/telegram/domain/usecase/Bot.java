package br.com.lepsistemas.telegram.domain.usecase;

import br.com.lepsistemas.telegram.domain.model.EntryMessage;

public interface Bot {
	
	public void send(EntryMessage message);

}
