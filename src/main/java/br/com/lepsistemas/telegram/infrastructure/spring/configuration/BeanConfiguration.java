package br.com.lepsistemas.telegram.infrastructure.spring.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.assistant.v2.Assistant;
import com.pengrad.telegrambot.TelegramBot;

import br.com.lepsistemas.telegram.domain.usecase.AnswerRecruiter;
import br.com.lepsistemas.telegram.domain.usecase.Bot;
import br.com.lepsistemas.telegram.domain.usecase.EmojiInterpolation;
import br.com.lepsistemas.telegram.domain.usecase.EntryMessageEnrichment;
import br.com.lepsistemas.telegram.domain.usecase.NaturalLanguageProcessingService;
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
	public AnswerRecruiter answerRecruiter(Bot bot, EntryMessageEnrichment entryMessageEnrichment, EmojiInterpolation emojiInterpreter) {
		return new AnswerRecruiter(bot, entryMessageEnrichment, emojiInterpreter);
	}
	
	@Bean
	public EmojiInterpolation emojiInterpreter() {
		return new EmojiInterpolation();
	}

	@Bean
	public EntryMessageEnrichment entryMessageEnrichment(NaturalLanguageProcessingService nlpService) {
		return new EntryMessageEnrichment(nlpService);
	}
	
	@Bean
	public NaturalLanguageProcessingService nlpService(Assistant assistant) {
		return new WatsonAssistant(this.watsonAssistantId, assistant);
	}
	
	@Bean
	public Authenticator authenticator() {
		return new IamAuthenticator(this.watsonAssistantKey);
	}
	
	@Bean
	public Assistant watsonService(Authenticator authenticator) {
		return new Assistant("2020-04-01", authenticator);
	}

	@Bean
	public Bot bot() {
		return new ChatBot();
	}
	
	@Bean
	public TelegramBot telegramBot() {
		return new TelegramBot(this.telegramBotToken);
	}

}
