package ru.jigulin.room.endpoint;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/voice")
public class VoiceEndpoint {

    private static final Set<Session> SESSIONS = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session) {
        SESSIONS.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        SESSIONS.remove(session);
    }

    @OnMessage
    public void onMessage(ByteBuffer voice, Session sender) throws IOException {
        for (var session : SESSIONS) {
            if (!session.equals(sender)) {
                session.getBasicRemote().sendBinary(voice);
            }
        }
    }
}
