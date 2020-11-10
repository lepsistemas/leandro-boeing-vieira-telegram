package br.com.lepsistemas.telegram.infrastructure.event;

import static java.util.Arrays.asList;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import br.com.lepsistemas.telegram.domain.model.event.ResponseMessageEvent;
import br.com.lepsistemas.telegram.domain.usecase.event.EventSubscriber;

public class ResponseMessageEventSubscriber implements EventSubscriber<ResponseMessageEvent> {
	
	private final static String TOPIC = "bot-responses";
	
	private static final String JAAS_CONFIG_PATTERN = "org.apache.kafka.common.security.plain.PlainLoginModule required\n" +
			"    username=\"%s\"\n" +
			"    password=\"%s\";";
	
	private final KafkaConsumer<String, ResponseMessageEvent> kafkaConsumer;
	
	public ResponseMessageEventSubscriber(String bootstrapServers, String groupId, String clientId, String user, String password) {
		Map<String, Object> configs = new HashMap<>();
		
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//		configs.put(ConsumerConfig.CLIENT_ID_CONFIG, clientId);
//		configs.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ResponseMessageEventDeserializer.class);
        
        configs.put("security.protocol", "SASL_SSL");
		configs.put("sasl.mechanism", "PLAIN");
		configs.put("sasl.jaas.config", String.format(JAAS_CONFIG_PATTERN, user, password));
		configs.put("ssl.protocol", "TLSv1.2");
		configs.put("ssl.enabled.protocols", "TLSv1.2");
		configs.put("ssl.endpoint.identification.algorithm", "HTTPS");
        
		this.kafkaConsumer = new KafkaConsumer<>(configs);
		
		if (kafkaConsumer.partitionsFor(ResponseMessageEventSubscriber.TOPIC) != null) {
			this.kafkaConsumer.subscribe(asList(ResponseMessageEventSubscriber.TOPIC));
		}
	}

	@Override
	public List<ResponseMessageEvent> subscribe() {
		ConsumerRecords<String, ResponseMessageEvent> records = this.kafkaConsumer.poll(Duration.ofMillis(3000L));
		List<ResponseMessageEvent> events = new ArrayList<>();
		for (ConsumerRecord<String, ResponseMessageEvent> record : records) {
			events.add(record.value());
		}
		return events;
	}

}
