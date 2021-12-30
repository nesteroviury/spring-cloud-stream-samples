package ru.spring.cloud.example.first.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import ru.spring.cloud.example.dto.Payload;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class KafkaConsumer {
    private static final String CONSUMER_PATH = "end";
    private static final String KAFKA_RECEIVED_PARTITION_ID_HEADER_KEY = "kafka_receivedPartitionId";
    private static final String KAFKA_RECEIVED_TOPIC_HEADER_KEY = "kafka_receivedTopic";

    @Bean
    Consumer<Message<Payload>> consume() {
        return message -> {
            MessageHeaders headers = message.getHeaders();
            int kafkaReceivedPartitionId = (int) headers.get(KAFKA_RECEIVED_PARTITION_ID_HEADER_KEY);
            String kafkaReceivedTopic = (String) headers.get(KAFKA_RECEIVED_TOPIC_HEADER_KEY);
            Payload payload = message.getPayload();
            String path = payload.getPath();
            payload.setPath(path + CONSUMER_PATH);
            String logMessage = String.format("Received: "
                    + "\n\tkafkaReceivedTopic: %s"
                    + "\n\tkafkaReceivedPartitionId: %s"
                    + "\n\tpayload: %s", kafkaReceivedTopic, kafkaReceivedPartitionId, payload);
            log.info(logMessage);
        };
    }
}
