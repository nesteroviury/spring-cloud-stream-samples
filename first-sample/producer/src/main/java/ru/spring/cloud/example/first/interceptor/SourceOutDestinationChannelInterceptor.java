package ru.spring.cloud.example.first.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Service;
import ru.spring.cloud.example.dto.Payload;

import java.nio.charset.StandardCharsets;

@GlobalChannelInterceptor(patterns = "source-out-*")
@RequiredArgsConstructor
@Service
public class SourceOutDestinationChannelInterceptor implements ChannelInterceptor {
    private static final String UPDATE_TIME_HEADER_KEY = "Update-Time";
    private static final String INTERCEPTOR_PAYLOAD = "/" + SourceOutDestinationChannelInterceptor.class.getSimpleName() + "/";

    private final ObjectMapper objectMapper;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        MessageHeaderAccessor mutableAccessor = buildHeaders(message);
        byte[] payload = buildPayload(message.getPayload());
        return MessageBuilder.withPayload(payload).setHeaders(mutableAccessor).build();
    }

    private MessageHeaderAccessor buildHeaders(Message<?> message) {
        MessageHeaderAccessor mutableAccessor = MessageHeaderAccessor.getMutableAccessor(message);
        mutableAccessor.setHeader(UPDATE_TIME_HEADER_KEY, System.currentTimeMillis());
        return mutableAccessor;
    }

    @SneakyThrows
    private byte[] buildPayload(Object data) {
        Payload payload = objectMapper.readValue(new String((byte[]) data), Payload.class);
        String path = payload.getPath();
        payload.setPath(path + INTERCEPTOR_PAYLOAD);
        return objectMapper.writeValueAsBytes(payload);
    }
}
