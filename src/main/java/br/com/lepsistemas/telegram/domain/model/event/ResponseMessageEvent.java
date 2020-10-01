package br.com.lepsistemas.telegram.domain.model.event;

import br.com.lepsistemas.telegram.domain.model.ResponseMessage;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class ResponseMessageEvent extends Event<ResponseMessage> {
	
	private static final long serialVersionUID = 1L;
	
	private static String KEY = "RESPONSE_MESSAGE:%s";
	
	protected ResponseMessageEvent() {}
	
	public ResponseMessageEvent(ResponseMessage data) {
		super(data);
	}

	@Override
	public String key() {
		return String.format(ResponseMessageEvent.KEY, this.data().id());
	}

}
