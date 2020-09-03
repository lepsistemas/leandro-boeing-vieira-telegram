package br.com.lepsistemas.telegram.infrastructure.spring.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pengrad.telegrambot.TelegramBot;

import br.com.lepsistemas.telegram.domain.usecase.Bot;
import br.com.lepsistemas.telegram.domain.usecase.EntryUpdate;
import br.com.lepsistemas.telegram.infrastructure.telegram.ChatBot;

@Configuration
public class BeanConfiguration {
	
	@Value("${telegram.bot.token}")
	private String telegramBotToken;

	@Bean
	public EntryUpdate entryUpdate() {
		return new EntryUpdate(chatBot());
	}
	
	@Bean
	public Bot chatBot() {
		return new ChatBot();
	}
	
	@Bean
	public TelegramBot telegramBot() {
		return new TelegramBot(telegramBotToken);
	}

}
