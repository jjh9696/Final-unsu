package com.kh.lucky.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.kh.lucky.websocket.ChatbotWebSocketServer;
import com.kh.lucky.websocket.MemberChatWebSocketServer;

@EnableWebSocket
@Configuration
public class WebSocketServerConfiguration implements WebSocketConfigurer {

    @Autowired
    private ChatbotWebSocketServer chatbotWebSocketServer;

    @Autowired
    private MemberChatWebSocketServer memberChatWebSocketServer;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatbotWebSocketServer, "/ws/chatbot")
        		.setAllowedOriginPatterns("*")
                .withSockJS();

        registry.addHandler(memberChatWebSocketServer, "/ws/memberChat")
		        .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}

