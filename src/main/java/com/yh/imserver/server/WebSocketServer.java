package com.yh.imserver.server;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket")
@Component
public class WebSocketServer {

    // 用于存储所有连接的 WebSocket 客户端
    private static final CopyOnWriteArraySet<WebSocketServer> clients = new CopyOnWriteArraySet<>();

    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        clients.add(this);
        System.out.println("新连接加入: " + session.getId());
    }

    @OnClose
    public void onClose() {
        clients.remove(this);
        System.out.println("连接关闭: " + this.session.getId());
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("收到消息: " + message);
    }

    // 推送消息给所有客户端
    public static void broadcast(String message) {
        for (WebSocketServer client : clients) {
            try {
                client.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
