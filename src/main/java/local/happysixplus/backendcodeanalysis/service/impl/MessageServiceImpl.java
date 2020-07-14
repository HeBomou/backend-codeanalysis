package local.happysixplus.backendcodeanalysis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import local.happysixplus.backendcodeanalysis.data.MessageData;
import local.happysixplus.backendcodeanalysis.po.MessagePo;
import local.happysixplus.backendcodeanalysis.service.MessageService;
import local.happysixplus.backendcodeanalysis.service.UserService;
import local.happysixplus.backendcodeanalysis.vo.MessageVo;
import lombok.var;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    UserService userService;

    @Autowired
    MessageData messageData;

    @Override
    public Long addMessage(MessageVo vo) {
        return messageData.save(new MessagePo(null, vo.getSenderId(), vo.getReceiverId(), vo.getContent(), vo.getTime())).getId();
    }

    @Override
    public void removeMessage(Long id) {
        messageData.deleteById(id);
    }

    @Override
    public List<MessageVo> getMessage(Long senderId, Long receiverId) {
        var pos = messageData.findBySenderIdAndReceiverId(senderId, receiverId);
        var res = new ArrayList<MessageVo>(pos.size());
        for (var po : pos)
            res.add(new MessageVo(po.getId(), po.getSenderId(), po.getReceiverId(), po.getContent(), po.getTime()));
        pos = messageData.findBySenderIdAndReceiverId(receiverId, senderId);
        for (var po : pos)
            res.add(new MessageVo(po.getId(), po.getSenderId(), po.getReceiverId(), po.getContent(), po.getTime()));
        res.sort((a, b) -> a.getTime().compareTo(b.getTime()));
        return res;
    }

}