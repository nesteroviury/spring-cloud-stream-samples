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
    private static final String INTERCEPTOR_PAYLOAD = "/" + SourceOutDestinationChannelInterceptor.class.getSimpleName() + "/";

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        MessageHeaderAccessor mutableAccessor = MessageHeaderAccessor.getMutableAccessor(message);
        mutableAccessor.setHeader(CREATION_TIME_HEADER_KEY, System.currentTimeMillis());
        Optional<Payload> payload = Optional.ofNullable((Payload) message.getPayload());
        payload.ifPresent(data -> {
            String path = data.getPath();
            data.setPath(path + INTERCEPTOR_PAYLOAD);
        });
        return MessageBuilder.withPayload(payload).setHeaders(mutableAccessor).build();
    }
}
