package br.com.lepsistemas.telegram.infrastructure.spring.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.assistant.v2.Assistant;
import com.pengrad.telegrambot.TelegramBot;

import br.com.lepsistemas.telegram.domain.usecase.AnswerRecruiter;
import br.com.lepsistemas.telegram.domain.usecase.Messaging;
import br.com.lepsistemas.telegram.domain.usecase.EmojiInterpolation;
import br.com.lepsistemas.telegram.domain.usecase.NaturalLanguageProcessing;
import br.com.lepsistemas.telegram.infrastructure.telegram.TelegramMessaging;
import br.com.lepsistemas.telegram.infrastructure.telegram.UnicodeEmojiInterpolation;
import br.com.lepsistemas.telegram.infrastructure.watson.WatsonAssistant;

@Configuration
public class BeanConfiguration {
	
	private static final String WATSON_ASSISTANT_VERSION = "2020-04-01";

	@Value("${telegram.bot.token}")
	private String telegramBotToken;
	
	@Value("${watson.assistant.key}")
	private String watsonAssistantKey;
	
	@Value("${watson.assistant.id}")
	private String watsonAssistantId;
	
	@Bean
	public AnswerRecruiter answerRecruiter(Messaging bot, NaturalLanguageProcessing nlp, EmojiInterpolation emojiInterpolation) {
		return new AnswerRecruiter(bot, nlp, emojiInterpolation);
	}
	
	@Bean
	public NaturalLanguageProcessing nlp(Assistant assistant) {
		return new WatsonAssistant(this.watsonAssistantId, assistant);
	}
	
	@Bean
	public Authenticator authenticator() {
		return new IamAuthenticator(this.watsonAssistantKey);
	}
	
	@Bean
	public Assistant assistant(Authenticator authenticator) {
		return new Assistant(WATSON_ASSISTANT_VERSION, authenticator);
	}
	
	@Bean
	public TelegramBot telegramBot() {
		return new TelegramBot(this.telegramBotToken);
	}

	@Bean
	public Messaging messaging() {
		return new TelegramMessaging();
	}
	
	@Bean
	public EmojiInterpolation emojiInterpolation() {
		return new UnicodeEmojiInterpolation();
	}

}
