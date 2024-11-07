package com.eheiste.laureatnet.config.ws;

import org.springframework.context.event.EventListener;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

public class WebSocketEventListener {
    @EventListener
    private void handleSessionConnected(SessionConnectEvent event) {
        System.out.println("A client connected");
    }

    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {
        System.out.println("A client disconnected");
    }

    @EventListener
    private void handleSessionSubscribe(SessionSubscribeEvent event) {
        System.out.println("A client Subscribe");
    }

    @EventListener
    private void handleSessionUnsubscribe(SessionUnsubscribeEvent event) {
        System.out.println("A client Unsubscribe");
    }
}