package local.happysixplus.backendcodeanalysis.entry;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;

import org.springframework.stereotype.Component;

import local.happysixplus.backendcodeanalysis.service.MessageService;
import local.happysixplus.backendcodeanalysis.vo.MessageVo;
import lombok.var;

@ServerEndpoint(value = "/chat")
@Component
public class ChatEntry {
    private static int onlineCount = 0;
    private static CopyOnWriteArraySet<ChatEntry> webSocketSet = new CopyOnWriteArraySet<>();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static MessageService messageService;

    private Session session;
    private Long userId = null;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        onlineCount++;
        System.out.println("On open");
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        onlineCount--;
        System.out.println("bye cnm");
    }

    @OnMessage
    public void onMessage(String msg, Session session) {
        System.out.println("cnm msg come: " + msg);
        if (msg.contains(",")) {
            // 发送信息
            if (userId == null || userId.equals(0L))
                return;
            String[] strs = msg.split(",", 2);
            Long toUserId = Long.parseLong(strs[0]);
            String m = strs[1];
            String timeStr = sdf.format(new Date());
            Long msgId = messageService.addMessage(new MessageVo(0L, userId, toUserId, m, timeStr, 0));
            try {
                session.getBasicRemote()
                        .sendText(JSON.toJSONString(new MessageVo(msgId, userId, toUserId, m, timeStr, 0)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (var entry : webSocketSet)
                if (entry.userId.equals(toUserId)) {
                    try {
                        entry.session.getBasicRemote()
                                .sendText(JSON.toJSONString(new MessageVo(msgId, userId, toUserId, m, timeStr, 0)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
        } else
            // 初始化用户id
            userId = Long.parseLong(msg);
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
