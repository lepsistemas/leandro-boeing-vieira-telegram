package br.com.lepsistemas.telegram;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class SmokeTest {
	
	@Test
	void should_run() {
		assertTrue(true);
	}

}
