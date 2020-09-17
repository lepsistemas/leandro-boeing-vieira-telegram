package br.com.lepsistemas.telegram.infrastructure.spring.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.assistant.v2.Assistant;
import com.pengrad.telegrambot.TelegramBot;

import br.com.lepsistemas.telegram.domain.usecase.Bot;
import br.com.lepsistemas.telegram.domain.usecase.EmojiInterpreter;
import br.com.lepsistemas.telegram.domain.usecase.EntryMessageEnrichment;
import br.com.lepsistemas.telegram.domain.usecase.MessageHandler;
import br.com.lepsistemas.telegram.domain.usecase.NaturalLanguageProcessingEnrichment;
import br.com.lepsistemas.telegram.infrastructure.telegram.ChatBot;
import br.com.lepsistemas.telegram.infrastructure.watson.WatsonAssistant;

@Configuration
public class BeanConfiguration {
	
	@Value("${telegram.bot.token}")
	private String telegramBotToken;
	
	@Value("${watson.assistant.key}")
	private String watsonAssistantKey;
	
	@Value("${watson.assistant.id}")
	private String watsonAssistantId;
	
	@Bean
	public MessageHandler entryUpdate() {
		return new MessageHandler(chatBot(), entryMessageEnrichment(), emojiInterpreter());
	}
	
	@Bean
	public EmojiInterpreter emojiInterpreter() {
		return new EmojiInterpreter();
	}

	@Bean
	public EntryMessageEnrichment entryMessageEnrichment() {
		return new EntryMessageEnrichment(nlpEnrichment());
	}
	
	@Bean
	public NaturalLanguageProcessingEnrichment nlpEnrichment() {
		return new WatsonAssistant(this.watsonAssistantId, watsonService());
	}
	
	@Bean
	public Assistant watsonService() {
		return new Assistant("2020-04-01", new IamAuthenticator(this.watsonAssistantKey));
	}

	@Bean
	public Bot chatBot() {
		return new ChatBot();
	}
	
	@Bean
	public TelegramBot telegramBot() {
		return new TelegramBot(this.telegramBotToken);
	}

}
