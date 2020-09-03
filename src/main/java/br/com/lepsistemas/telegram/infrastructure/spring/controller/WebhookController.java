package br.com.lepsistemas.telegram.infrastructure.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;

import br.com.lepsistemas.telegram.domain.model.EntryMessage;
import br.com.lepsistemas.telegram.domain.usecase.EntryUpdate;
import br.com.lepsistemas.telegram.infrastructure.UpdateToChatMessage;

@RestController
public class WebhookController {
	
	private EntryUpdate entry;
	
	@PostMapping("/webhook")
	public void webhook(@RequestBody String body) {
		Update update = BotUtils.parseUpdate(body);
		EntryMessage message = UpdateToChatMessage.convert(update);
		this.entry.handle(message);
	}
	
	@Autowired
	public void setEntryUpdate(EntryUpdate entry) {
		this.entry = entry;
	}

}
