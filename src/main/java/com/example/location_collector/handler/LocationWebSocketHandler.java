package com.example.location_collector.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Component
public class LocationWebSocketHandler implements WebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(LocationWebSocketHandler.class);

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .doOnNext(message -> {
                    logger.info("Received message: {}", message);
                })
                .flatMap(message -> session.send(Mono.just(session.textMessage("Echo: " + message))))
                .then();
    }
}
