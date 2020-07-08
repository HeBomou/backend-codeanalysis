package local.happysixplus.backendcodeanalysis.data;

import lombok.var;
import local.happysixplus.backendcodeanalysis.po.MessagePo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class MessageDataTest {

    @Autowired
    MessageData data;

    MessagePo messagePo = new MessagePo();

    @BeforeEach
    void init() {
        data.deleteAll();
        messagePo = new MessagePo(1234L, 1L,2L, "SKT","Faker",0);
    }

    @AfterEach
    void tearDown() {
        data.deleteAll();
    }

    @Test
    public void testInsert() {
        messagePo = data.save(messagePo);
        MessagePo resPo = data.findById(messagePo.getId()).get();
        assertEquals(resPo.getId(), messagePo.getId());
        assertEquals(resPo.getSenderId(),messagePo.getSenderId());
        assertEquals(resPo.getReceiverId(),messagePo.getReceiverId());
        assertEquals(resPo.getTime(),messagePo.getTime());
        assertEquals(resPo.getIsRead(),messagePo.getIsRead());
        assertEquals(resPo.getContent(),messagePo.getContent());
    }

    @Test
    public void testUpdate() {
        messagePo = data.save(messagePo);
        messagePo = new MessagePo(messagePo.getId(),3L,4L,"RNG","Uzi",1);
        messagePo = data.save(messagePo);
        MessagePo resPo = data.findById(messagePo.getId()).get();
        assertEquals(resPo.getId(), messagePo.getId());
        assertEquals(resPo.getSenderId(),messagePo.getSenderId());
        assertEquals(resPo.getReceiverId(),messagePo.getReceiverId());
        assertEquals(resPo.getTime(),messagePo.getTime());
        assertEquals(resPo.getIsRead(),messagePo.getIsRead());
        assertEquals(resPo.getContent(),messagePo.getContent());
    }

    @Test
    public void testRemove() {
        messagePo = data.save(messagePo);
        data.deleteById(messagePo.getId());
        var res = data.findById(messagePo.getId()).orElse(null);
        assertEquals(res, null);
    }

    @Test
    public void testFindBySenderId() {
        messagePo = data.save(messagePo);
        MessagePo messagePo1 = data.save(new MessagePo(12345L, 1L,3L,"SKT","otto",1));
        MessagePo messagePo2 = 
        data.save(new MessagePo(12346L, 1L,4L,"RNG","Xiaohu",1));
        List<MessagePo> list = data.findBySenderId(1L);
        assertEquals(list.size(),3);
        list.sort((a,b)->(int)(a.getReceiverId()-b.getReceiverId()));

        assertEquals(list.get(0).getId(), messagePo.getId());
        assertEquals(list.get(0).getSenderId(),messagePo.getSenderId());
        assertEquals(list.get(0).getReceiverId(),messagePo.getReceiverId());
        assertEquals(list.get(0).getTime(),messagePo.getTime());
        assertEquals(list.get(0).getIsRead(),messagePo.getIsRead());
        assertEquals(list.get(0).getContent(),messagePo.getContent());

        assertEquals(list.get(1).getId(), messagePo1.getId());
        assertEquals(list.get(1).getSenderId(),messagePo1.getSenderId());
        assertEquals(list.get(1).getReceiverId(),messagePo1.getReceiverId());
        assertEquals(list.get(1).getTime(),messagePo1.getTime());
        assertEquals(list.get(1).getIsRead(),messagePo1.getIsRead());
        assertEquals(list.get(1).getContent(),messagePo1.getContent());

        assertEquals(list.get(2).getId(), messagePo2.getId());
        assertEquals(list.get(2).getSenderId(),messagePo2.getSenderId());
        assertEquals(list.get(2).getReceiverId(),messagePo2.getReceiverId());
        assertEquals(list.get(2).getTime(),messagePo2.getTime());
        assertEquals(list.get(2).getIsRead(),messagePo2.getIsRead());
        assertEquals(list.get(2).getContent(),messagePo2.getContent());
    }

    @Test
    public void testFindBySenderIdAndReceiverId(){
        messagePo = data.save(messagePo);
        MessagePo messagePo1 = data.save(new MessagePo(12345L, 1L,3L,"SKT","otto",1));
        // MessagePo messagePo2 = 
        List<MessagePo> list = data.findBySenderIdAndReceiverId(1L,3L);
        assertEquals(list.size(),1);

        assertEquals(list.get(0).getId(), messagePo1.getId());
        assertEquals(list.get(0).getSenderId(),messagePo1.getSenderId());
        assertEquals(list.get(0).getReceiverId(),messagePo1.getReceiverId());
        assertEquals(list.get(0).getTime(),messagePo1.getTime());
        assertEquals(list.get(0).getIsRead(),messagePo1.getIsRead());
        assertEquals(list.get(0).getContent(),messagePo1.getContent());

    }

    
}
