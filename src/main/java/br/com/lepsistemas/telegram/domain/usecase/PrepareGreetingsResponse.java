package br.com.lepsistemas.telegram.domain.usecase;

import br.com.lepsistemas.telegram.domain.model.ResponseMessage;

public class PrepareGreetingsResponse extends PrepareResponseMessage {

	public PrepareGreetingsResponse() {
		super(null);
	}

	@Override
	public ResponseMessage prepare(ResponseMessage input) {
		ResponseMessage output = new ResponseMessage(input.id(), "You just typed: " + input.text());
		return output;
	}

}
