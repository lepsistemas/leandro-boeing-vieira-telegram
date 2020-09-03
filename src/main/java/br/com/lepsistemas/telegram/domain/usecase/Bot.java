package br.com.lepsistemas.telegram.domain.usecase;

import br.com.lepsistemas.telegram.domain.model.ResponseMessage;

public interface Bot {
	
	public void send(ResponseMessage message);

}
