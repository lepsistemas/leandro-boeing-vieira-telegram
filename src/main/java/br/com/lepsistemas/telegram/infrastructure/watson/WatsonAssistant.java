package br.com.lepsistemas.telegram.infrastructure.watson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.ibm.cloud.sdk.core.http.ServiceCall;
import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.assistant.v2.model.CreateSessionOptions;
import com.ibm.watson.assistant.v2.model.MessageContext;
import com.ibm.watson.assistant.v2.model.MessageInput;
import com.ibm.watson.assistant.v2.model.MessageInputOptions;
import com.ibm.watson.assistant.v2.model.MessageOptions;
import com.ibm.watson.assistant.v2.model.MessageResponse;
import com.ibm.watson.assistant.v2.model.RuntimeIntent;
import com.ibm.watson.assistant.v2.model.RuntimeResponseGeneric;
import com.ibm.watson.assistant.v2.model.SessionResponse;

import br.com.lepsistemas.telegram.domain.model.EnrichedMessage;
import br.com.lepsistemas.telegram.domain.model.EntryMessage;
import br.com.lepsistemas.telegram.domain.usecase.NaturalLanguageProcessing;

public class WatsonAssistant implements NaturalLanguageProcessing {

	private String assistantId;
	private Assistant service;
	
	private Map<Long, MessageContext> contexts;
	
	public WatsonAssistant(String assistantId, Assistant service) {
		this.assistantId = assistantId;
		this.service = service;
		this.contexts = new HashMap<>();
	}

	@Override
	public EnrichedMessage understand(EntryMessage entry) {
		EnrichedMessage enriched = new EnrichedMessage(entry);
		
		MessageContext messageContext = this.contexts.get(entry.id());
		if (messageContext == null) {
			messageContext = new MessageContext.Builder().build();
		}
		
		String sessionId = messageContext.global() != null ? messageContext.global().sessionId() : null;
		if (sessionId == null) {
			sessionId = createSession();
		}
		
		MessageInput messageInput = new MessageInput.Builder()
				.text(entry.text())
				.options(new MessageInputOptions.Builder().returnContext(true).build())
				.build();
		
		MessageOptions messageOptions = new MessageOptions.Builder()
				  .assistantId(this.assistantId)
				  .sessionId(sessionId)
				  .input(messageInput)
				  .context(messageContext)
				  .build();
		
		MessageResponse messageResponse = this.service.message(messageOptions).execute().getResult();
		
		messageContext = messageResponse.getContext();
		this.contexts.put(entry.id(), messageContext);
		
		this.extractResponse(enriched, messageResponse);
		this.extractIntents(enriched, messageResponse);
		this.extractContext(enriched, messageContext);
		
		return enriched;
	}

	private void extractContext(EnrichedMessage enriched, MessageContext messageContext) {
		Map<String, Object> userDefined = messageContext.skills().get("main skill").userDefined();
		if (userDefined != null) {
			for(Entry<String, Object> context : userDefined.entrySet()) {
				enriched.addContext(context.getKey(), context.getValue());
			}
		}
	}

	private void extractIntents(EnrichedMessage enriched, MessageResponse messageResponse) {
		if (messageResponse.getOutput() != null) {
			for(RuntimeIntent intent : messageResponse.getOutput().getIntents()) {
				enriched.addIntent(intent.intent(), intent.confidence());
			}
		}
	}

	private void extractResponse(EnrichedMessage enriched, MessageResponse messageResponse) {
		List<String> responses = new ArrayList<>();
		if (messageResponse.getOutput() != null && messageResponse.getOutput().getGeneric() != null) {
			for (RuntimeResponseGeneric generic : messageResponse.getOutput().getGeneric()) {
				if ("text".equals(generic.responseType())) {
					responses.add(generic.text());
				}
			}
		}
		
		if (!responses.isEmpty()) {
			enriched.response(responses.get(new Random().nextInt(responses.size())));
		}
	}

	private String createSession() {
		CreateSessionOptions createSessionOptions = new CreateSessionOptions.Builder(assistantId).build();
	    ServiceCall<SessionResponse> createSession = this.service.createSession(createSessionOptions);
		SessionResponse session = createSession.execute().getResult();
	    String sessionId = session.getSessionId();
		return sessionId;
	}
	
}
