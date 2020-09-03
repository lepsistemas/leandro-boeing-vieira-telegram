package br.com.lepsistemas.telegram.domain.usecase;

import br.com.lepsistemas.telegram.domain.model.ResponseMessage;

public abstract class PrepareResponseMessage {
	
	protected PrepareResponseMessage next;
	
	public PrepareResponseMessage(PrepareResponseMessage next) {
		this.next = next;
	}
	
	public abstract ResponseMessage prepare(ResponseMessage entry);

}
