package br.com.lepsistemas.telegram.infrastructure.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;

import br.com.lepsistemas.telegram.domain.model.EntryMessage;
import br.com.lepsistemas.telegram.domain.model.ResponseMessage;
import br.com.lepsistemas.telegram.domain.usecase.MessageHandler;
import br.com.lepsistemas.telegram.infrastructure.convert.UpdateToEntryMessage;

@RestController
public class WebhookController {
	
	private MessageHandler entry;
	
	@PostMapping("/webhook")
	public ResponseEntity<String> webhook(@RequestBody String body) {
		Update update = BotUtils.parseUpdate(body);
		EntryMessage message = UpdateToEntryMessage.convert(update);
		ResponseMessage response = this.entry.handle(message);
		return ResponseEntity.status(HttpStatus.OK).body(response != null ? response.text() : null);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exception() {
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@Autowired
	public void setEntryUpdate(MessageHandler entry) {
		this.entry = entry;
	}
	
	

}
