package com.ibm.lab.customer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.lab.customer.service.ProducerService;

@RestController
public class CustomerController {

	Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	private final ProducerService producer;

	public CustomerController(ProducerService producer) {
		this.producer = producer;
	}

	@PostMapping("/send")
	public ResponseEntity<String> sendMessage(@RequestBody String message) {
		logger.info("REST send message to kafka:"+message);
//		if (!StringUtils.isEmpty(message))
//			kafkaTemplate.send(topicName, "Message is " + message);
//		logger.info(message);
		this.producer.sendMessage(message);
			
		return ResponseEntity.ok("");
	}
}
