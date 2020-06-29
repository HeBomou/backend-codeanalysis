package local.happysixplus.backendcodeanalysis.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import local.happysixplus.backendcodeanalysis.po.MessagePo;

public interface MessageData extends JpaRepository<MessagePo, Long> {

    List<MessagePo> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    List<MessagePo> findBySenderId(Long senderId);
}