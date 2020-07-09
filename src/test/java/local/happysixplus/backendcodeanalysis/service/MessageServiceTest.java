package local.happysixplus.backendcodeanalysis.service;

import local.happysixplus.backendcodeanalysis.data.MessageData;
import local.happysixplus.backendcodeanalysis.po.MessagePo;
import local.happysixplus.backendcodeanalysis.vo.MessageVo;
import lombok.var;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class MessageServiceTest {

    @MockBean
    MessageData data;

    @Autowired
    MessageService service;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addMessage() throws Exception {
        // 执行
        var messageVo = new MessageVo(null, 1L,2L, "xxxx","123123",1);
        var messagePo = new MessagePo(null, 1L,2L, "xxxx","123123",1);
        Mockito.when(data.save(messagePo)).thenReturn(new MessagePo(1L, 1L,2L, "xxxx","123123",1));
        service.addMessage(messageVo);

        // 验证
        
        
        Mockito.verify(data).save(messagePo);
    }

    @Test
    void removeGroupMessage() {
        var id = 12L;
        service.removeMessage(id);
        Mockito.verify(data).deleteById(id);
    }

    @Test
    void updateGroupMessage() {
        // 执行
        var taskVo = new MessageVo(2L, 3L,4L, "hello","ssss",0);
        service.updateMessage(taskVo);

        // 验证
        var taskPo = new MessagePo(2L,3L,4L, "hello","ssss",0);
        Mockito.verify(data).save(taskPo);
    }

    @Test
    void getMessage(){
        var po1 = new MessagePo(15L,44L,45L, "fsfs","1",0);
        var po2 = new MessagePo(16L,44L,45L, "zszs","2",1);
        var po3 = new MessagePo(17L,44L,45L,  "ssss","3",3);
        var pos1=Arrays.asList(po1,po2,po3);
        Mockito.when(data.findBySenderIdAndReceiverId(44L,45L)).thenReturn(pos1);
        
        var po4 = new MessagePo(18L,45L,44L, "fsfsfs","4",0);
        var po5 = new MessagePo(19L,45L,44L, "zszszs","5",1);
        var po6 = new MessagePo(20L,45L,44L,  "ssssss","6",3);
        var pos2=Arrays.asList(po4,po5,po6);
        Mockito.when(data.findBySenderIdAndReceiverId(45L,44L)).thenReturn(pos2);
        
        var vo1 = new MessageVo(15L,44L,45L, "fsfs","1",0);
        var vo2 = new MessageVo(16L,44L,45L, "zszs","2",1);
        var vo3 = new MessageVo(17L,44L,45L,  "ssss","3",3);
        var vo4 = new MessageVo(18L,45L,44L, "fsfsfs","4",0);
        var vo5 = new MessageVo(19L,45L,44L, "zszszs","5",1);
        var vo6 = new MessageVo(20L,45L,44L,  "ssssss","6",3);
        var vos=Arrays.asList(vo1,vo2,vo3,vo4,vo5,vo6);

        var res1 = service.getMessage(44L,45L);
        assertEquals(new HashSet<>(res1), new HashSet<>(vos));
        var res2 = service.getMessage(45L,44L);
        assertEquals(new HashSet<>(res2), new HashSet<>(vos));
    }
}