package br.com.lepsistemas.telegram;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmokeTest {
	
	@Value("${watson.api.key:dummyWatsonApiKey}")
	private String watsonApiKey;
	
	@Value("${watson.assistant.id:dummyWatsonAssistantId}")
	private String watsonAssistantId;
	
	@Test
	void should_run() {
		assertTrue(true);
	}

}
