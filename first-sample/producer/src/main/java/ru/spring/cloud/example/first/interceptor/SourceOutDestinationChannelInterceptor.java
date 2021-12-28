package ru.spring.cloud.example.first.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import ru.spring.cloud.example.dto.Payload;

import java.util.Optional;

public class SourceOutDestinationChannelInterceptor implements ChannelInterceptor {
    private static final String CREATION_TIME_HEADER_KEY = "Creation-Time";
    private static final String PARTITION_KEY_HEADER_KEY = "partitionKey";
    private static final int DEFAULT_PARTITION_VALUE = 0;
    private static final String INTERCEPTOR_PAYLOAD = "/" + SourceOutDestinationChannelInterceptor.class.getSimpleName() + "/";

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        MessageHeaderAccessor mutableAccessor = buildHeaders(message);
        Payload payload = buildPayload(message.getPayload());
        return MessageBuilder.withPayload(payload).setHeaders(mutableAccessor).build();
    }

    private MessageHeaderAccessor buildHeaders(Message<?> message) {
        MessageHeaderAccessor mutableAccessor = MessageHeaderAccessor.getMutableAccessor(message);
        Optional<Payload> payload = (Optional<Payload>) message.getPayload();
        mutableAccessor.setHeader(CREATION_TIME_HEADER_KEY, System.currentTimeMillis());
        mutableAccessor.setHeader(PARTITION_KEY_HEADER_KEY, payload.map(Payload::getPartition).orElse(DEFAULT_PARTITION_VALUE));
        return mutableAccessor;
    }

    private Payload buildPayload(Object data) {
        Optional<Payload> payload = Optional.ofNullable((Payload) data);
        payload.ifPresent(p -> {
            String path = p.getPath();
            p.setPath(path + INTERCEPTOR_PAYLOAD);
        });
        return null;
    }
}
