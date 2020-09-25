package br.com.lepsistemas.telegram;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.lepsistemas.telegram.infrastructure.spring.controller.WebhookController;

@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class SmokeTest {
	
	@Autowired
	private WebhookController controller;
	
	@Test
	public void run() {
		assertThat(this.controller).isNotNull();
	}

}
