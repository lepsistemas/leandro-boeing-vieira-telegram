package br.com.lepsistemas.telegram.infrastructure.convert;

import com.pengrad.telegrambot.model.Update;

import br.com.lepsistemas.telegram.domain.model.EntryMessage;

public class UpdateToEntryMessage {
	
	private UpdateToEntryMessage() {}
	
	public static EntryMessage convert(Update update) {
		return new EntryMessage(update.message().chat().id(), update.message().text());
	}

}
