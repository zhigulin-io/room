package ru.jigulin.room.endpoint;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint(value = "/text/{username}")
public final class TextEndpoint {

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
    public void onMessage(@PathParam("username") String username, String message) throws IOException {
        for (var session : SESSIONS) {
            session.getBasicRemote().sendText(username + " : " + message);
        }
    }
}
