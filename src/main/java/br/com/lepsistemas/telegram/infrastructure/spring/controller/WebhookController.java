package br.com.lepsistemas.telegram.infrastructure.spring.controller;

import java.util.List;
import java.util.stream.Collectors;

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
import br.com.lepsistemas.telegram.domain.usecase.AnswerRecruiter;
import br.com.lepsistemas.telegram.infrastructure.convert.UpdateToEntryMessage;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class WebhookController {
	
	private AnswerRecruiter answer;
	
	@PostMapping("/webhook")
	public ResponseEntity<List<String>> webhook(@RequestBody String body) {
		WebhookController.log.info("--- Body: {} ---", body);
		Update update = BotUtils.parseUpdate(body);
		EntryMessage message = UpdateToEntryMessage.convert(update);
		List<ResponseMessage> response = this.answer.to(message);
		return ResponseEntity.status(HttpStatus.OK).body(response != null ? response(response) : null);
	}
	
	private List<String> response(List<ResponseMessage> response) {
		return response.stream().map(r -> r.text()).collect(Collectors.toList());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<List<String>> exception(Exception e) {
		WebhookController.log.error("--- Error: {} {} ---", e.getCause(), e.getMessage());
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@Autowired
	public void setAnswerRecruiter(AnswerRecruiter answer) {
		this.answer = answer;
	}

}
