package br.com.lepsistemas.telegram.application;

import br.com.lepsistemas.telegram.domain.model.ResponseMessage;

public interface Bot {
	
	public void send(ResponseMessage message);

}
