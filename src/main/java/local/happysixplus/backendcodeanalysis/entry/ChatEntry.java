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

import local.happysixplus.backendcodeanalysis.service.ContactService;
import local.happysixplus.backendcodeanalysis.service.MessageService;
import local.happysixplus.backendcodeanalysis.service.UserService;
import local.happysixplus.backendcodeanalysis.vo.MessageVo;
import lombok.var;

@ServerEndpoint(value = "/chat")
@Component
public class ChatEntry {
    private static int onlineCount = 0;
    private static CopyOnWriteArraySet<ChatEntry> webSocketSet = new CopyOnWriteArraySet<>();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static MessageService messageService;

    public static ContactService contactService;

    public static UserService userService;

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
        System.out.println("bye");
    }

    @OnMessage
    public void onMessage(String msg, Session session) {
        System.out.println("msg come: " + msg);
        if (msg.charAt(0) == 'm') {
            // 发送信息
            if (userId == null || userId.equals(0L))
                return;
            String[] strs = msg.substring(1).split(",", 2);
            Long toUserId = Long.parseLong(strs[0]);
            String m = strs[1];
            String timeStr = sdf.format(new Date());
            Long msgId = messageService.addMessage(new MessageVo(0L, userId, toUserId, m, timeStr, 0));
            // 发给自己
            try {
                session.getBasicRemote()
                        .sendText("m" + JSON.toJSONString(new MessageVo(msgId, userId, toUserId, m, timeStr, 0)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 发送给目标并尝试为目标添加联系人
            boolean needAddContact = false;
            if (!contactService.existContact(toUserId, userId)) {
                contactService.addContact(toUserId, userId);
                needAddContact = true;
            }
            for (var entry : webSocketSet)
                if (entry.userId.equals(toUserId)) {
                    try {
                        entry.session.getBasicRemote().sendText(
                                "m" + JSON.toJSONString(new MessageVo(msgId, userId, toUserId, m, timeStr, 0)));
                        if (needAddContact) {
                            var user = userService.getUser(userId);
                            entry.session.getBasicRemote().sendText("c" + JSON.toJSONString(user));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
        } else if (msg.charAt(0) == 'i')
            // 初始化用户id
            userId = Long.parseLong(msg.substring(1));
        else if (msg.charAt(0) == 'c') {
            Long toUserId = Long.parseLong(msg.substring(1));
            if (!contactService.existContact(userId, toUserId)) {
                contactService.addContact(userId, toUserId);
                var toUser = userService.getUser(toUserId);
                try {
                    session.getBasicRemote().sendText("c" + JSON.toJSONString(toUser));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("err");
        error.printStackTrace();
    }

    public static int GetOnlineCount() {
        return onlineCount;
    }
}
