package local.happysixplus.backendcodeanalysis;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

@ServerEndpoint(value = "/chat")
@Component
public class Test {
    private static int onlineCount = 0;
    private static CopyOnWriteArraySet<Test> webSocketSet = new CopyOnWriteArraySet<>();
    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        onlineCount++;
        System.out.println("On open");
        try {
            session.getBasicRemote().sendText("hello cnmd");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        onlineCount--;
        System.out.println("bye cnm");
    }

    @OnMessage
    public void onMessage(String msg, Session session) {
        System.out.println("cnm msg come" + msg);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("cnm err");
        error.printStackTrace();
    }

    public static int GetOnlineCount() {
        return onlineCount;
    }
}
