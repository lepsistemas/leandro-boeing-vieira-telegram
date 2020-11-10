package br.com.lepsistemas.telegram.infrastructure.watson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ibm.cloud.sdk.core.http.ServiceCall;
import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.assistant.v2.model.CreateSessionOptions;
import com.ibm.watson.assistant.v2.model.MessageContext;
import com.ibm.watson.assistant.v2.model.MessageContextSkill;
import com.ibm.watson.assistant.v2.model.MessageContextSkills;
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

	private static final String MAIN_SKILL = "main skill";
	
	private String assistantId;
	private Assistant service;
	
	private Map<Long, MessageContext> contexts;
	
	public WatsonAssistant(String assistantId, Assistant service) {
		this.assistantId = assistantId;
		this.service = service;
		this.contexts = new HashMap<>();
	}

	@Override
	public List<EnrichedMessage> understand(EntryMessage entry) {
		MessageContext messageContext = this.contexts.get(entry.id());
		if (messageContext == null) {
			messageContext = this.createMessageContext(entry);
		}
		
		String sessionId = messageContext.global() != null ? messageContext.global().sessionId() : null;
		if (sessionId == null) {
			sessionId = createSession();
		}
		
		MessageInput messageInput = createMessageInput(entry);
		
		MessageOptions messageOptions = createMessageOptions(messageContext, sessionId, messageInput);
		
		MessageResponse messageResponse = this.service.message(messageOptions).execute().getResult();
		
		messageContext = updateMessageContext(entry, messageResponse);
		
		return prepareEnrichedMessages(entry, messageContext, messageResponse);
	}

	private MessageOptions createMessageOptions(MessageContext messageContext, String sessionId, MessageInput messageInput) {
		MessageOptions messageOptions = new MessageOptions.Builder()
				  .assistantId(this.assistantId)
				  .sessionId(sessionId)
				  .input(messageInput)
				  .context(messageContext)
				  .build();
		return messageOptions;
	}

	private MessageInput createMessageInput(EntryMessage entry) {
		MessageInput messageInput = new MessageInput.Builder()
				.text(entry.text())
				.options(new MessageInputOptions.Builder().returnContext(true).build())
				.build();
		return messageInput;
	}

	private MessageContext updateMessageContext(EntryMessage entry, MessageResponse messageResponse) {
		MessageContext messageContext = messageResponse.getContext();
		this.contexts.put(entry.id(), messageContext);
		return messageContext;
	}

	private List<EnrichedMessage> prepareEnrichedMessages(EntryMessage entry, MessageContext messageContext, MessageResponse messageResponse) {
		List<EnrichedMessage> enrichedMessages = new ArrayList<>();
		if (messageResponse.getOutput() != null && messageResponse.getOutput().getGeneric() != null) {
			for (RuntimeResponseGeneric generic : messageResponse.getOutput().getGeneric()) {
				EnrichedMessage enriched = new EnrichedMessage(entry);
				
				this.extractResponse(enriched, generic);
				this.extractIntents(enriched, messageResponse);
				this.extractContext(enriched, messageContext);
				
				enrichedMessages.add(enriched);
			}
		}
		return enrichedMessages;
	}
	
	private MessageContext createMessageContext(EntryMessage entry) {
		MessageContextSkills skills = new MessageContextSkills();
		Map<String, Object> userDefined = new HashMap<>();
		if (entry.sender() != null) {
			userDefined.put("recruiter_name", entry.sender());
		}
		MessageContextSkill skill = new MessageContextSkill.Builder().userDefined(userDefined).build();
		skills.put(MAIN_SKILL, skill);
		
		return new MessageContext.Builder().skills(skills).build();
	}

	private void extractResponse(EnrichedMessage enriched, RuntimeResponseGeneric generic) {
		if ("text".equals(generic.responseType())) {
			enriched.response((generic.text()));
		}
	}

	private void extractContext(EnrichedMessage enriched, MessageContext messageContext) {
		Map<String, Object> userDefined = messageContext.skills().get(MAIN_SKILL).userDefined();
		if (userDefined != null) {
			for(Entry<String, Object> context : userDefined.entrySet()) {
				enriched.addContext(context.getKey(), context.getValue());
			}
		}
	}

	private void extractIntents(EnrichedMessage enriched, MessageResponse messageResponse) {
		for(RuntimeIntent intent : messageResponse.getOutput().getIntents()) {
			enriched.addIntent(intent.intent(), intent.confidence());
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
