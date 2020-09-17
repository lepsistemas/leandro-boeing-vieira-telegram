package br.com.lepsistemas.telegram.infrastructure.telegram.watson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.assistant.v2.model.CreateSessionOptions;
import com.ibm.watson.assistant.v2.model.DeleteSessionOptions;
import com.ibm.watson.assistant.v2.model.MessageInput;
import com.ibm.watson.assistant.v2.model.MessageOptions;
import com.ibm.watson.assistant.v2.model.MessageResponse;
import com.ibm.watson.assistant.v2.model.RuntimeResponseGeneric;
import com.ibm.watson.assistant.v2.model.SessionResponse;

import br.com.lepsistemas.telegram.domain.model.EnrichedMessage;
import br.com.lepsistemas.telegram.domain.model.EntryMessage;
import br.com.lepsistemas.telegram.domain.usecase.NaturalLanguageProcessingEnrichment;

public class WatsonAssistant implements NaturalLanguageProcessingEnrichment {

	private String assistantId;
	private Assistant service;
	
	public WatsonAssistant(String assistantId, Assistant service) {
		this.assistantId = assistantId;
		this.service = service;
	}

	@Override
	public EnrichedMessage understand(EntryMessage entry) {
		EnrichedMessage enriched = new EnrichedMessage(entry);
		
		MessageInput input = new MessageInput.Builder().text(entry.text()).build();
		
		String sessionId = createSession();
		
		MessageOptions messageOptions = new MessageOptions.Builder()
				  .assistantId(this.assistantId)
				  .sessionId(sessionId)
				  .input(input)
				  .build();
		
		MessageResponse response = this.service.message(messageOptions).execute().getResult();
		
		enriched.response(this.randomResponse(response));
		
		deleteSession(sessionId);
		
		return enriched;
	}
	
	private String randomResponse(MessageResponse response) {
		List<String> responses = new ArrayList<>();
		if (response.getOutput() != null && response.getOutput().getGeneric() != null) {
			for (RuntimeResponseGeneric generic : response.getOutput().getGeneric()) {
				if ("text".equals(generic.responseType())) {
					responses.add(generic.text());
				}
			}
		}
		
		if (responses.isEmpty()) {
			return null;
		}
		
		return responses.get(new Random().nextInt(responses.size()));
	}

	private void deleteSession(String sessionId) {
		DeleteSessionOptions deleteSessionOptions = new DeleteSessionOptions.Builder(assistantId, sessionId).build();
	    this.service.deleteSession(deleteSessionOptions).execute();
	}

	private String createSession() {
		CreateSessionOptions createSessionOptions = new CreateSessionOptions.Builder(assistantId).build();
	    SessionResponse session = this.service.createSession(createSessionOptions).execute().getResult();
	    String sessionId = session.getSessionId();
		return sessionId;
	}

}