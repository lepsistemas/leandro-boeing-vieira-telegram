package br.com.lepsistemas.telegram.domain.usecase;

import br.com.lepsistemas.telegram.domain.model.ResponseMessage;

public interface EmojiInterpolation {

	public ResponseMessage interpolate(ResponseMessage message);

}
