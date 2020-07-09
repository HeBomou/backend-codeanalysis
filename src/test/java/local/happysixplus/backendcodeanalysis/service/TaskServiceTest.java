package local.happysixplus.backendcodeanalysis.service;

import local.happysixplus.backendcodeanalysis.data.GroupTaskData;
import local.happysixplus.backendcodeanalysis.data.TaskUserRelData;
import local.happysixplus.backendcodeanalysis.po.GroupTaskPo;
import local.happysixplus.backendcodeanalysis.vo.GroupTaskVo;
import local.happysixplus.backendcodeanalysis.po.TaskUserRelPo;
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
class TaskServiceTest {

    @MockBean
    GroupTaskData data;

    @MockBean
    TaskUserRelData taskRelData;

    @Autowired
    TaskService service;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addGroupTask() throws Exception {
        // 执行
        var taskVo = new GroupTaskVo(null, 1L,"tester1", "xxxx","123123",1);
        service.addTask(taskVo);

        // 验证
        var taskPo = new GroupTaskPo(null, 1L,"tester1", "xxxx","123123",1);
        Mockito.verify(data).save(taskPo);
    }

    @Test
    void removeGroupTask() {
        var id = 12L;
        service.removeTask(id);
        Mockito.verify(data).deleteById(id);
        Mockito.verify(taskRelData).deleteByTaskId(id);
    }

    @Test
    void updateGroupTask() {
        // 执行
        var taskVo = new GroupTaskVo(2L, 3L,"tester3", "hello","ssss",0);
        service.updateTask(taskVo);

        // 验证
        var taskPo = new GroupTaskPo(2L,3L,"tester3", "hello","ssss",0);
        Mockito.verify(data).save(taskPo);
    }

    @Test
    void assignTask() {
        var rel=new TaskUserRelPo(null,1L,2L,3L);
        service.assignTask(1L, 2L,3L);
        Mockito.verify(taskRelData).save(rel);
    }

    @Test
    void getAllTask() {
        var po1 = new GroupTaskPo(15L,44L, "tester100", "fsfs","寒冰风暴",0);
        var po2 = new GroupTaskPo(16L,44L, "tester101", "zszs","旋风斩",1);
        var po3 = new GroupTaskPo(17L,44L, "tester102", "ssss","混乱风暴",3);
        var pos=Arrays.asList(po1,po2,po3);
        Mockito.when(data.findAllByGroupId(44L)).thenReturn(pos);
        
        var vo1 = new GroupTaskVo(15L,44L, "tester100", "fsfs","寒冰风暴",0);
        var vo2 = new GroupTaskVo(16L,44L, "tester101", "zszs","旋风斩",1);
        var vo3 = new GroupTaskVo(17L,44L, "tester102", "ssss","混乱风暴",3);
        var vos=Arrays.asList(vo1,vo2,vo3);

        var res = service.getAllTask(44L);
        assertEquals(new HashSet<>(res), new HashSet<>(vos));
    }

    @Test
    void getTask(){
        var po1 = new GroupTaskPo(15L,44L, "tester100", "fsfs","寒冰风暴",0);
        var po2 = new GroupTaskPo(16L,44L, "tester101", "zszs","旋风斩",1);
        var po3 = new GroupTaskPo(17L,44L, "tester102", "ssss","混乱风暴",3);
        var pos=Arrays.asList(po1,po2,po3);
        Mockito.when(data.findAllByGroupId(44L)).thenReturn(pos);
        
        var vo1 = new GroupTaskVo(15L,44L, "tester100", "fsfs","寒冰风暴",0);
        var vo2 = new GroupTaskVo(16L,44L, "tester101", "zszs","旋风斩",1);
        var vo3 = new GroupTaskVo(17L,44L, "tester102", "ssss","混乱风暴",3);
        var vos=Arrays.asList(vo1,vo2,vo3);

        var rel1=new TaskUserRelPo(1L,15L,13L,44L);
        var rel2=new TaskUserRelPo(2L,16L,13L,44L);
        var rel3=new TaskUserRelPo(3L,17L,13L,44L);
        var rels=Arrays.asList(rel1,rel2,rel3);
        Mockito.when(taskRelData.findByGroupIdAndUserId(44L, 13L)).thenReturn(rels);

        var res = service.getAllTask(44L);
        assertEquals(new HashSet<>(res), new HashSet<>(vos));
    }
}