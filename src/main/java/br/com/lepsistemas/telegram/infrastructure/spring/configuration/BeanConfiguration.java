package br.com.lepsistemas.telegram.infrastructure.spring.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pengrad.telegrambot.TelegramBot;

import br.com.lepsistemas.telegram.application.Bot;
import br.com.lepsistemas.telegram.application.EntryMessageEnrichment;
import br.com.lepsistemas.telegram.application.MessageHandler;
import br.com.lepsistemas.telegram.infrastructure.stanfordnlp.StanfordDiscoverSpeakerRepository;
import br.com.lepsistemas.telegram.infrastructure.telegram.ChatBot;

@Configuration
public class BeanConfiguration {
	
	@Value("${telegram.bot.token}")
	private String telegramBotToken;
	
	@Autowired
	private StanfordDiscoverSpeakerRepository nluRepository;

	@Bean
	public MessageHandler entryUpdate() {
		return new MessageHandler(chatBot(), entryMessageEnrichment());
	}
	
	@Bean
	public EntryMessageEnrichment entryMessageEnrichment() {
		return new EntryMessageEnrichment(nluRepository);
	}
	
//	@Bean IntentRepository intentRepository() {
//		return new WatsonAssistantIntentRepository(watsonApiKey, watsonAssistantId);
//	}

	@Bean
	public Bot chatBot() {
		return new ChatBot();
	}
	
	@Bean
	public TelegramBot telegramBot() {
		return new TelegramBot(telegramBotToken);
	}

}
