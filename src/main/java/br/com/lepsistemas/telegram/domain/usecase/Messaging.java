package br.com.lepsistemas.telegram.domain.usecase;

import br.com.lepsistemas.telegram.domain.model.ResponseMessage;

public interface Messaging {
	
	public void send(ResponseMessage message);

}
