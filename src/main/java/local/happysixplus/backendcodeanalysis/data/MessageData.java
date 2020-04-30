package local.happysixplus.backendcodeanalysis.data;

import org.springframework.data.jpa.repository.JpaRepository;

import local.happysixplus.backendcodeanalysis.po.MessagePo;

public interface MessageData extends JpaRepository<MessagePo, Long> {

}