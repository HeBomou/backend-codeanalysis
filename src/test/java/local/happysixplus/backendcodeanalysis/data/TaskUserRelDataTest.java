package local.happysixplus.backendcodeanalysis.data;

import lombok.var;
import local.happysixplus.backendcodeanalysis.po.TaskUserRelPo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class TaskUserRelDataTest {

    @Autowired
    TaskUserRelData data;

    TaskUserRelPo taskUserRelPo = new TaskUserRelPo();

    @BeforeEach
    void init() {
        data.deleteAll();
        taskUserRelPo = new TaskUserRelPo(1234L, 1L, 2L,3L);
    }

    @AfterEach
    void tearDown() {
        data.deleteAll();
    }

    @Test
    public void testInsert() {
        taskUserRelPo = data.save(taskUserRelPo);
        TaskUserRelPo resPo = data.findById(taskUserRelPo.getId()).get();
        assertEquals(resPo.getId(), taskUserRelPo.getId());
        assertEquals(resPo.getTaskId(),taskUserRelPo.getTaskId());
        assertEquals(resPo.getUserId(),taskUserRelPo.getUserId());
        assertEquals(resPo.getGroupId(),taskUserRelPo.getGroupId());
    }

    @Test
    public void testUpdate() {
        taskUserRelPo = data.save(taskUserRelPo);
        taskUserRelPo = new TaskUserRelPo(taskUserRelPo.getId(), 4396L,4397L,4398L);
        taskUserRelPo = data.save(taskUserRelPo);
        TaskUserRelPo resPo = data.findById(taskUserRelPo.getId()).get();
        assertEquals(resPo.getId(), taskUserRelPo.getId());
        assertEquals(resPo.getTaskId(),taskUserRelPo.getTaskId());
        assertEquals(resPo.getUserId(),taskUserRelPo.getUserId());
        assertEquals(resPo.getGroupId(),taskUserRelPo.getGroupId());
    }

    @Test
    public void testRemove() {
        taskUserRelPo = data.save(taskUserRelPo);
        data.deleteById(taskUserRelPo.getId());
        var res = data.findById(taskUserRelPo.getId()).orElse(null);
        assertEquals(res, null);
    }

    @Test
    public void testFindByGroupIdAndUserId() {
        taskUserRelPo = data.save(taskUserRelPo);
        TaskUserRelPo taskUserRelPo1 = data.save(new TaskUserRelPo(12345L, 1L,3L,9L));
        TaskUserRelPo taskUserRelPo2 = data.save(new TaskUserRelPo(12346L, 2L,3L,9L));
        List<TaskUserRelPo> list = data.findByGroupIdAndUserId(9L,3L);
        assertEquals(list.size(),2);
        list.sort((a, b) -> (int) (a.getId() - b.getId()));
        assertEquals(list.get(0).getId(), taskUserRelPo1.getId());
        assertEquals(list.get(0).getTaskId(),taskUserRelPo1.getTaskId());
        assertEquals(list.get(0).getUserId(),taskUserRelPo1.getUserId());
        assertEquals(list.get(0).getGroupId(),taskUserRelPo1.getGroupId());

        assertEquals(list.get(1).getId(), taskUserRelPo2.getId());
        assertEquals(list.get(1).getTaskId(),taskUserRelPo2.getTaskId());
        assertEquals(list.get(1).getUserId(),taskUserRelPo2.getUserId());
        assertEquals(list.get(1).getGroupId(),taskUserRelPo2.getGroupId());

        
    }

    @Test
    public void testDeleteByGroupId() {
        taskUserRelPo = data.save(taskUserRelPo);
        // TaskUserRelPo taskUserRelPo1 = 
        data.save(new TaskUserRelPo(12345L, 1L,3L,9L));
        // TaskUserRelPo taskUserRelPo2 = 
        data.deleteByGroupId(9L);
        List<TaskUserRelPo> list = data.findByGroupIdAndUserId(9L,3L);
        assertEquals(list.size(),0);    
    }
    @Test
    public void testDeleteByGroupIdAndUserId() {
        taskUserRelPo = data.save(taskUserRelPo);
        // TaskUserRelPo taskUserRelPo1 = 
        data.save(new TaskUserRelPo(12345L, 1L,3L,9L));
        // TaskUserRelPo taskUserRelPo2 = 
        data.deleteByUserIdAndGroupId(3L,9L);
        List<TaskUserRelPo> list = data.findByGroupIdAndUserId(9L,3L);
        assertEquals(list.size(),0);    
    }
    @Test
    public void testDeleteByTaskId() {
        taskUserRelPo = data.save(taskUserRelPo);
        // TaskUserRelPo taskUserRelPo1 = 
        data.save(new TaskUserRelPo(12345L, 1L,3L,4L));
        // TaskUserRelPo taskUserRelPo2 = 
        data.save(new TaskUserRelPo(12346L, 2L,3L,9L));
        data.deleteByTaskId(2L);
        List<TaskUserRelPo> list = data.findByGroupIdAndUserId(9L,3L);
        assertEquals(list.size(),0);    
        
    }
    
    
}
