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
            userService.getUser(userId);
            String[] strs = msg.substring(1).split(",", 2);
            Long toUserId = Long.parseLong(strs[0]);
            String m = strs[1];
            String timeStr = sdf.format(new Date());
            Long msgId = messageService.addMessage(new MessageVo(0L, userId, toUserId, m, timeStr, 0));
            // 发给自己
            sendText("m" + JSON.toJSONString(new MessageVo(msgId, userId, toUserId, m, timeStr, 0)));
            // 发送给目标并尝试为目标添加联系人
            boolean needAddContact = false;
            if (!contactService.existContact(toUserId, userId)) {
                contactService.addContact(toUserId, userId, 0);
                needAddContact = true;
            } else
                contactService.updateContactRead(toUserId, userId, 0);
            for (var entry : webSocketSet)
                if (entry.userId.equals(toUserId)) {
                    try {
                        if (needAddContact) {
                            var contact = contactService.getContact(toUserId, userId);
                            entry.session.getBasicRemote().sendText("c" + JSON.toJSONString(contact));
                        }
                        entry.session.getBasicRemote().sendText(
                                "m" + JSON.toJSONString(new MessageVo(msgId, userId, toUserId, m, timeStr, 0)));
                        entry.session.getBasicRemote().sendText("r0" + userId);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
        } else if (msg.charAt(0) == 'i') {
            // 初始化用户id
            userId = Long.parseLong(msg.substring(1));
            userService.getUser(userId);
        } else if (msg.charAt(0) == 'c') {
            Long toUserId = Long.parseLong(msg.substring(1));
            if (!contactService.existContact(userId, toUserId)) {
                contactService.addContact(userId, toUserId, 1);
                var contact = contactService.getContact(userId, toUserId);
                sendText("c" + JSON.toJSONString(contact));
            }
        } else if (msg.charAt(0) == 'r') {
            Long toUserId = Long.parseLong(msg.substring(1));
            contactService.updateContactRead(userId, toUserId, 1);
            sendText("r1" + toUserId);
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

    private void sendText(String text) {
        try {
            session.getBasicRemote().sendText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
