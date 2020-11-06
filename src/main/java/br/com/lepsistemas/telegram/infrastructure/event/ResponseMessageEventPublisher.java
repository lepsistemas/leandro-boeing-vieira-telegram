package br.com.lepsistemas.telegram.infrastructure.event;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.TimeoutException;
import org.apache.kafka.common.serialization.StringSerializer;

import br.com.lepsistemas.telegram.domain.model.event.ResponseMessageEvent;
import br.com.lepsistemas.telegram.domain.usecase.event.EventPublisher;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseMessageEventPublisher implements EventPublisher<ResponseMessageEvent> {
	
	private final static String TOPIC = "bot-responses";
	
	private static final String JAAS_CONFIG_PATTERN = "org.apache.kafka.common.security.plain.PlainLoginModule required\n" +
			"    username=\"%s\"\n" +
			"    password=\"%s\";";

	private final KafkaProducer<String, ResponseMessageEvent> kafkaProducer;
	
	public ResponseMessageEventPublisher(String bootstrapServers, String clientId, String user, String password) {
		Map<String, Object> configs = new HashMap<>();
		
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		configs.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ResponseMessageEventSerializer.class);
		
		configs.put("security.protocol", "SASL_SSL");
		configs.put("sasl.mechanism", "PLAIN");
		configs.put("sasl.jaas.config", String.format(JAAS_CONFIG_PATTERN, user, password));
		configs.put("ssl.protocol", "TLSv1.2");
		configs.put("ssl.enabled.protocols", "TLSv1.2");
		configs.put("ssl.endpoint.identification.algorithm", "HTTPS");
		
		this.kafkaProducer = new KafkaProducer<>(configs);
		
		try {
			kafkaProducer.partitionsFor(ResponseMessageEventPublisher.TOPIC);
		} catch (TimeoutException e) {
			this.kafkaProducer.close(Duration.ofSeconds(5L));
		}
	}

	@Override
	public void publish(ResponseMessageEvent event) {
		ProducerRecord<String, ResponseMessageEvent> record = new ProducerRecord<>(ResponseMessageEventPublisher.TOPIC, event);
		kafkaProducer.send(record, sent());
	}

	private Callback sent() {
		return new Callback() {
			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				ResponseMessageEventPublisher.log.info("--- Message sent to: {} ---", metadata.topic());
			}
			
		};
	}

}
