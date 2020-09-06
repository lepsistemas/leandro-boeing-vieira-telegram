package br.com.lepsistemas.telegram.infrastructure.nlp.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.assistant.v2.model.CreateSessionOptions;
import com.ibm.watson.assistant.v2.model.MessageInput;
import com.ibm.watson.assistant.v2.model.MessageOptions;
import com.ibm.watson.assistant.v2.model.MessageResponse;
import com.ibm.watson.assistant.v2.model.SessionResponse;

import br.com.lepsistemas.telegram.domain.model.Entity;
import br.com.lepsistemas.telegram.domain.model.Intent;
import br.com.lepsistemas.telegram.domain.model.Output;
import br.com.lepsistemas.telegram.domain.usecase.DialogFlowRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WatsonAssistantDialogFlowRepository implements DialogFlowRepository {
	
	private Assistant assistant;
	private String watsonAssistantId;
	
	public WatsonAssistantDialogFlowRepository(@Value("${watson.api.key}") String watsonApiKey, @Value("${watson.assistant.id}") String watsonAssistantId) {
		this.watsonAssistantId = watsonAssistantId;
		Authenticator authenticator = new IamAuthenticator(watsonApiKey);
		this.assistant = new Assistant("2019-02-28", authenticator);
	}

	@Override
	public Output forMessage(String message) {
		log.info("--- Retrieving intent for text: {} ---", message);
		MessageInput input = new MessageInput.Builder().text(message).build();
		MessageOptions options = new MessageOptions.Builder().assistantId(this.watsonAssistantId).sessionId(this.sessionId()).input(input).build();
		MessageResponse response = this.assistant.message(options).execute().getResult();
		
		Output output = new Output();
		
		response.getOutput().getIntents().forEach(intent -> {
			output.addIntent(new Intent(intent.intent(), intent.confidence()));
		});
		
		response.getOutput().getEntities().forEach(entity -> {
			output.addEntity(new Entity(entity.entity(), entity.value(), entity.confidence()));
		});
		
		response.getOutput().getGeneric().forEach(generic -> {
			output.addText(generic.text());
		});
		return output;
	}

	private String sessionId() {
		CreateSessionOptions options = new CreateSessionOptions.Builder(this.watsonAssistantId).build();
		SessionResponse response = assistant.createSession(options).execute().getResult();
		return response.getSessionId();
	}

}
