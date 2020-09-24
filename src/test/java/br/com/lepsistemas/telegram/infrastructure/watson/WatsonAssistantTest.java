package br.com.lepsistemas.telegram.infrastructure.watson;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ibm.cloud.sdk.core.http.Response;
import com.ibm.cloud.sdk.core.http.ServiceCall;
import com.ibm.cloud.sdk.core.http.ServiceCallback;
import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.assistant.v2.model.CreateSessionOptions;
import com.ibm.watson.assistant.v2.model.MessageContext;
import com.ibm.watson.assistant.v2.model.MessageContextSkill;
import com.ibm.watson.assistant.v2.model.MessageContextSkills;
import com.ibm.watson.assistant.v2.model.MessageOptions;
import com.ibm.watson.assistant.v2.model.MessageOutput;
import com.ibm.watson.assistant.v2.model.MessageResponse;
import com.ibm.watson.assistant.v2.model.RuntimeIntent;
import com.ibm.watson.assistant.v2.model.RuntimeResponseGeneric;
import com.ibm.watson.assistant.v2.model.SessionResponse;

import br.com.lepsistemas.telegram.domain.model.EnrichedMessage;
import br.com.lepsistemas.telegram.domain.model.EntryMessage;
import io.reactivex.Single;
import okhttp3.Protocol;
import okhttp3.Request;

@ExtendWith(MockitoExtension.class)
public class WatsonAssistantTest {
	
	private static final String ASSISTANT_ID = "ASSISTANT-ID";
	public static final String SESSION_ID = "SESSION-ID";
	
	private static final String RESPONSE = "Response";
	private static final String INTENT = "intent";
	private static final Double CONFIDENCE = Double.valueOf(0.9);
	
	private WatsonAssistant assistant;
	
	@Mock
	private Assistant service;
	
	@BeforeEach
	public void setUp() {
		this.assistant = new WatsonAssistant(ASSISTANT_ID, service);
	}
	
	@Test
	public void should_understand_full_message_with_context_intent_and_response() {
		ServiceCall<SessionResponse> session = new ServiceSessionResponse();
		when(this.service.createSession(any(CreateSessionOptions.class))).thenReturn(session);
		
		Map<String, Object> userDefined = new HashMap<>();
		userDefined.put("key", "value");
		ServiceCall<MessageResponse> options = new ServiceMessageResponse(null, userDefined, true);
		when(this.service.message(any(MessageOptions.class))).thenReturn(options);
		
		EntryMessage entry = new EntryMessage(1L, "Hi Leandro! My name is Sara and I'm from Amazon offering you a Java Developer job.");
		EnrichedMessage result = this.assistant.understand(entry);
		
		assertThat(result.intents()).hasSize(1);
		assertThat(result.intents().get(0).value()).isEqualTo("intent");
		assertThat(result.intents().get(0).confidence()).isEqualTo(Double.valueOf(0.9));
		
		assertThat(result.contexts()).hasSize(1);
		assertThat(result.contexts().get(0).key()).isEqualTo("key");
		assertThat(result.contexts().get(0).value()).isEqualTo("value");
		
		assertThat(result.response()).isEqualTo("Response");
	}
	
	@Test
	public void should_understand_full_message_with_intent_and_response() {
		ServiceCall<SessionResponse> session = new ServiceSessionResponse();
		when(this.service.createSession(any(CreateSessionOptions.class))).thenReturn(session);
		
		ServiceCall<MessageResponse> options = new ServiceMessageResponse(null, null, true);
		when(this.service.message(any(MessageOptions.class))).thenReturn(options);
		
		EntryMessage entry = new EntryMessage(1L, "Hi Leandro! My name is Sara and I'm from Amazon offering you a Java Developer job.");
		EnrichedMessage result = this.assistant.understand(entry);
		
		assertThat(result.intents()).hasSize(1);
		assertThat(result.intents().get(0).value()).isEqualTo("intent");
		assertThat(result.intents().get(0).confidence()).isEqualTo(Double.valueOf(0.9));
		
		assertThat(result.contexts()).hasSize(0);
		
		assertThat(result.response()).isEqualTo("Response");
	}
	
	@Test
	public void should_understand_full_message_with_response() {
		ServiceCall<SessionResponse> session = new ServiceSessionResponse();
		when(this.service.createSession(any(CreateSessionOptions.class))).thenReturn(session);
		
		Map<String, Object> system = new HashMap<>();
		system.put("sessionId", "session-id");
		ServiceCall<MessageResponse> options = new ServiceMessageResponse(system , null, false);
		when(this.service.message(any(MessageOptions.class))).thenReturn(options);
		
		EntryMessage entry = new EntryMessage(1L, "Hi Leandro! My name is Sara and I'm from Amazon offering you a Java Developer job.");
		EnrichedMessage result = this.assistant.understand(entry);
		
		assertThat(result.intents()).hasSize(0);
		
		assertThat(result.contexts()).hasSize(0);
		
		assertThat(result.response()).isEqualTo("Response");
	}
	
	private static class ServiceMessageResponse implements ServiceCall<MessageResponse> {
		
		private Map<String, Object> system;
		private Map<String, Object> userDefined;
		private boolean hasIntents;

		public ServiceMessageResponse(Map<String, Object> system, Map<String, Object> userDefined, boolean hasIntents) {
			this.system = system;
			this.userDefined = userDefined;
			this.hasIntents = hasIntents;
		}

		@Override
		public ServiceCall<MessageResponse> addHeader(String name, String value) {
			return null;
		}

		@Override
		public Response<MessageResponse> execute() throws RuntimeException {
			Request request = new Request.Builder().url("http://localhost").build();
			okhttp3.Response response = new okhttp3.Response.Builder()
					.request(request)
					.protocol(Protocol.HTTP_1_1)
					.code(200)
					.message("")
					.build();
			MessageResponse message = new FakeMessageResponse(this.system, this.userDefined, this.hasIntents);
			return new Response<MessageResponse>(message, response);
		}

		@Override
		public void enqueue(ServiceCallback<MessageResponse> callback) {
		}

		@Override
		public Single<Response<MessageResponse>> reactiveRequest() {
			return null;
		}

		@Override
		public void cancel() {
		}
		
	}
	
	private static class FakeMessageOutput extends MessageOutput {
		
		private boolean hasIntents;

		public FakeMessageOutput(boolean hasIntents) {
			this.hasIntents = hasIntents;
		}

		@Override
		public List<RuntimeResponseGeneric> getGeneric() {
			return asList(new RuntimeResponseGeneric.Builder().responseType("text").text(RESPONSE).build());
		}
		
		@Override
		public List<RuntimeIntent> getIntents() {
			List<RuntimeIntent> intents = new ArrayList<>();
			if (this.hasIntents) {
				intents = asList(new RuntimeIntent.Builder().confidence(CONFIDENCE).intent(INTENT).build());
			}
			return intents;
		}
		
	}
	
	private static class FakeMessageResponse extends MessageResponse {
		
		private Map<String, Object> system;
		private Map<String, Object> userDefined;
		private boolean hasIntents;

		public FakeMessageResponse(Map<String, Object> system, Map<String, Object> userDefined, boolean hasIntents) {
			this.system = system;
			this.userDefined = userDefined;
			this.hasIntents = hasIntents;
		}
		
		@Override
		public MessageContext getContext() {
			MessageContextSkills skills = new MessageContextSkills();
			MessageContextSkill skill = new MessageContextSkill.Builder()
					.system(this.system)
					.userDefined(this.userDefined)
					.build();
			skills.put("main skill", skill);
			MessageContext context = new MessageContext.Builder()
					.skills(skills)
					.build();
			return context;
		}
		
		@Override
		public MessageOutput getOutput() {
			return new FakeMessageOutput(this.hasIntents);
		}
	}
	
	private static class ServiceSessionResponse implements ServiceCall<SessionResponse> {

		@Override
		public ServiceCall<SessionResponse> addHeader(String name, String value) {
			return null;
		}

		@Override
		public Response<SessionResponse> execute() throws RuntimeException {
			SessionResponse session = new FakeSessionResponse();
			Request request = new Request.Builder().url("http://localhost").build();
			okhttp3.Response response = new okhttp3.Response.Builder()
					.request(request)
					.protocol(Protocol.HTTP_1_1)
					.code(200)
					.message("")
					.build();
			return new Response<SessionResponse>(session, response);
		}

		@Override
		public void enqueue(ServiceCallback<SessionResponse> callback) {
		}

		@Override
		public Single<Response<SessionResponse>> reactiveRequest() {
			return null;
		}

		@Override
		public void cancel() {
		}
	}
	
	private static class FakeSessionResponse extends SessionResponse {
		
		@Override
		public String getSessionId() {
			return SESSION_ID;
		}
		
	}

}
