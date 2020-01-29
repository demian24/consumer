package com.ibm.lab.customer.service;

import java.util.concurrent.CountDownLatch;
import java.util.stream.StreamSupport;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(CustomerService.class);

  private CountDownLatch latch = new CountDownLatch(1);
  

  /**
   * Kafka Consumer
   * @param payload
   * @param partitionList
   * @param offsetList
   */
  @KafkaListener(topics = "${kafka.topic-name}")
  public void listenAsObject(ConsumerRecord<String, String> cr,
          @Payload String payload, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
          @Header(KafkaHeaders.OFFSET) int offset) { 
	try {
		LOGGER.info("Logger 1 ==================================================");
		LOGGER.info("Logger 1 PARTITION_ID: {} | Key {}: | Payload: {} | OFFSET: {}", partition, cr.key(),
                payload, offset );
        latch.countDown();
	} catch (Exception e) {
		LOGGER.error("Push the messaged to Error Stream : " + e);
	} finally {
		
	}    
  }
  

  /**
   * 
   * @param headers
   * @return
   */
  private static String typeIdHeader(Headers headers) {
      return StreamSupport.stream(headers.spliterator(), false)
              .filter(header -> header.key().equals("__TypeId__"))
              .findFirst().map(header -> new String(header.value())).orElse("N/A");
  }  
}
