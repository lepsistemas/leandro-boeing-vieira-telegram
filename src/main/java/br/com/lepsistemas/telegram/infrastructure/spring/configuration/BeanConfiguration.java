package br.com.lepsistemas.telegram.infrastructure.spring.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pengrad.telegrambot.TelegramBot;

import br.com.lepsistemas.telegram.domain.usecase.Bot;
import br.com.lepsistemas.telegram.domain.usecase.IntentRecognition;
import br.com.lepsistemas.telegram.domain.usecase.IntentThreshold;
import br.com.lepsistemas.telegram.domain.usecase.MessageHandler;
import br.com.lepsistemas.telegram.infrastructure.nlp.repository.WatsonAssistantDialogFlowRepository;
import br.com.lepsistemas.telegram.infrastructure.telegram.ChatBot;

@Configuration
public class BeanConfiguration {
	
	@Value("${telegram.bot.token}")
	private String telegramBotToken;
	
	@Autowired
	private WatsonAssistantDialogFlowRepository watsonAssistantIntentRepository;

	@Bean
	public MessageHandler entryUpdate() {
		return new MessageHandler(chatBot(), intentRecognition(), intentThreshold());
	}
	
	@Bean
	public IntentRecognition intentRecognition() {
		return new IntentRecognition(this.watsonAssistantIntentRepository);
	}
	
//	@Bean IntentRepository intentRepository() {
//		return new WatsonAssistantIntentRepository(watsonApiKey, watsonAssistantId);
//	}
	
	@Bean
	public IntentThreshold intentThreshold() {
		return new IntentThreshold();
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
