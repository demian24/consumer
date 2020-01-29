package com.ibm.lab.customer.service;

import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
	private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);
	
	@Value("${kafka.topic-name}")
	private  String topicName;
		
	private final int messagePerRequest;

	private KafkaTemplate<String,String> kafkaTemplate;
	
	public ProducerService(KafkaTemplate kafkaTemplate, @Value("${kafka.message-per-request}") final int messagePerRequest) {
		this.kafkaTemplate = kafkaTemplate;
		this.messagePerRequest = messagePerRequest;
	}
	
	/**
	 * Send message to topic
	 * @param message
	 */
	public void sendMessage(String message){
		logger.info(String.format("$$ -> Producing message --> %s",message));
		
		IntStream.range(0, messagePerRequest)
		    .forEach( i -> this.kafkaTemplate.send(topicName, String.valueOf(i), message));
	}
}
