package local.happysixplus.backendcodeanalysis.data;

import lombok.var;
import local.happysixplus.backendcodeanalysis.po.GroupTaskPo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class GroupTaskDataTest {

    @Autowired
    GroupTaskData data;

    GroupTaskPo groupTaskPo = new GroupTaskPo();

    @BeforeEach
    void init() {
        data.deleteAll();
        groupTaskPo = new GroupTaskPo(1234L, 1L, "SKT","Faker","sada",0);
    }

    @AfterEach
    void tearDown() {
        data.deleteAll();
    }

    @Test
    public void testInsert() {
        groupTaskPo = data.save(groupTaskPo);
        GroupTaskPo resPo = data.findById(groupTaskPo.getId()).get();
        assertEquals(resPo.getId(), groupTaskPo.getId());
        assertEquals(resPo.getGroupId(),groupTaskPo.getGroupId());
        assertEquals(resPo.getInfo(),groupTaskPo.getInfo());
        assertEquals(resPo.getName(),groupTaskPo.getName());
        assertEquals(resPo.getDeadline(),groupTaskPo.getDeadline());
        assertEquals(resPo.getIsFinished(),groupTaskPo.getIsFinished());
    }

    @Test
    public void testUpdate() {
        groupTaskPo = data.save(groupTaskPo);
        groupTaskPo = new GroupTaskPo(groupTaskPo.getId(), 4396L,"RNG","Uzi","ASDASD",1);
        groupTaskPo = data.save(groupTaskPo);
        GroupTaskPo resPo = data.findById(groupTaskPo.getId()).get();
        assertEquals(resPo.getId(), groupTaskPo.getId());
        assertEquals(resPo.getGroupId(),groupTaskPo.getGroupId());
        assertEquals(resPo.getInfo(),groupTaskPo.getInfo());
        assertEquals(resPo.getName(),groupTaskPo.getName());
        assertEquals(resPo.getDeadline(),groupTaskPo.getDeadline());
        assertEquals(resPo.getIsFinished(),groupTaskPo.getIsFinished());
    }

    @Test
    public void testRemove() {
        groupTaskPo = data.save(groupTaskPo);
        data.deleteById(groupTaskPo.getId());
        var res = data.findById(groupTaskPo.getId()).orElse(null);
        assertEquals(res, null);
    }

    @Test
    public void testFindByIdIn() {
        groupTaskPo = data.save(groupTaskPo);
        GroupTaskPo groupTaskPo1 = data.save(new GroupTaskPo(12345L, 1L,"SKT","otto","S",9));
        GroupTaskPo groupTaskPo2 = data.save(new GroupTaskPo(12346L, 2L, "RNG","Xiaohu","B",1));
        List<Long> ids=new ArrayList<>();
        ids.add(groupTaskPo1.getId());ids.add(groupTaskPo2.getId());
        List<GroupTaskPo> list = data.findByIdIn(ids);
        list.sort((a, b) -> (int) (a.getId() - b.getId()));
        assertEquals(list.get(0).getId(), groupTaskPo1.getId());
        assertEquals(list.get(0).getGroupId(),groupTaskPo1.getGroupId());
        assertEquals(list.get(0).getInfo(),groupTaskPo1.getInfo());
        assertEquals(list.get(0).getName(),groupTaskPo1.getName());
        assertEquals(list.get(0).getDeadline(),groupTaskPo1.getDeadline());
        assertEquals(list.get(0).getIsFinished(),groupTaskPo1.getIsFinished());

        assertEquals(list.get(1).getId(), groupTaskPo2.getId());
        assertEquals(list.get(1).getGroupId(),groupTaskPo2.getGroupId());
        assertEquals(list.get(1).getInfo(),groupTaskPo2.getInfo());
        assertEquals(list.get(1).getName(),groupTaskPo2.getName());
        assertEquals(list.get(1).getDeadline(),groupTaskPo2.getDeadline());
        assertEquals(list.get(1).getIsFinished(),groupTaskPo2.getIsFinished());
    }

    @Test
    public void testFindAllByGroupId() {
        groupTaskPo = data.save(groupTaskPo);
        GroupTaskPo groupTaskPo1 = data.save(new GroupTaskPo(12345L, 9L,"SKT","otto","S",9));
        // GroupTaskPo groupTaskPo2 = 
        data.save(new GroupTaskPo(12346L, 2L, "RNG","Xiaohu","B",1));
        List<GroupTaskPo> list = data.findAllByGroupId(9L);
        assertEquals(list.size(),1);
        assertEquals(list.get(0).getId(), groupTaskPo1.getId());
        assertEquals(list.get(0).getGroupId(),groupTaskPo1.getGroupId());
        assertEquals(list.get(0).getInfo(),groupTaskPo1.getInfo());
        assertEquals(list.get(0).getName(),groupTaskPo1.getName());
        assertEquals(list.get(0).getDeadline(),groupTaskPo1.getDeadline());
        assertEquals(list.get(0).getIsFinished(),groupTaskPo1.getIsFinished());
    }

    @Test 
    public void testDeleteByGroupId(){
        groupTaskPo = data.save(groupTaskPo);
        // GroupTaskPo groupTaskPo1 = 
        data.save(new GroupTaskPo(12345L, 1L,"SKT","otto","S",9));
        data.save(new GroupTaskPo(12346L, 1L, "RNG","Xiaohu","B",1));
        data.deleteByGroupId(1L);
        List<GroupTaskPo> list = data.findAllByGroupId(1L);
        assertEquals(list.size(),0);
    }

    
}
