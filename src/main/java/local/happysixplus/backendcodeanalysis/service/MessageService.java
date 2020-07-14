package local.happysixplus.backendcodeanalysis.service;

import java.util.List;

import local.happysixplus.backendcodeanalysis.vo.MessageVo;

public interface MessageService {

    /**
     * 添加私信
     * 
     * @param vo
     */
    Long addMessage(MessageVo vo);

    /**
     * 删除私信
     */
    void removeMessage(Long id);

    /**
     * 获取私信列表
     * 
     * @param senderId
     * @param receiverId
     * @return
     */
    List<MessageVo> getMessage(Long senderId, Long receiverId);

}