package local.happysixplus.backendcodeanalysis.service;

import java.util.List;

import local.happysixplus.backendcodeanalysis.vo.MessageVo;

public interface Message {

    /**
     * 添加私信
     * 
     * @param vo
     */
    void addMessage(MessageVo vo);

    /**
     * 删除私信
     */
    void removeMessage(Long id);

    /**
     * 修改私信（已读/未读）
     * 
     * @param vo
     */
    void updateMessage(MessageVo vo);

    /**
     * 获取私信列表
     * 
     * @param senderId
     * @param receiverId
     * @return
     */
    List<MessageVo> getMessage(Long senderId, Long receiverId);

}