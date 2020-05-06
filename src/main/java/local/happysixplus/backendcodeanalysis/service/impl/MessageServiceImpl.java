package local.happysixplus.backendcodeanalysis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import local.happysixplus.backendcodeanalysis.data.MessageData;
import local.happysixplus.backendcodeanalysis.service.MessageService;
import local.happysixplus.backendcodeanalysis.vo.MessageVo;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageData messageData;

    @Override
    public void addMessage(MessageVo vo) {

    }

    @Override
    public void removeMessage(Long id) {

    }

    @Override
    public void updateMessage(MessageVo vo) {

    }

    @Override
    public List<MessageVo> getMessage(Long senderId, Long receiverId) {
        return null;
    }

}