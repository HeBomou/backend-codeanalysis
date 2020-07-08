package local.happysixplus.backendcodeanalysis.data;

import lombok.var;
import local.happysixplus.backendcodeanalysis.po.GroupUserRelPo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class GroupUserRelDataTest {

    @Autowired
    GroupUserRelData data;

    GroupUserRelPo groupUserRelPo = new GroupUserRelPo();

    @BeforeEach
    void init() {
        data.deleteAll();
        groupUserRelPo = new GroupUserRelPo(1234L, 1L, 2L,"Manager");
    }

    @AfterEach
    void tearDown() {
        data.deleteAll();
    }

    @Test
    public void testInsert() {
        groupUserRelPo = data.save(groupUserRelPo);
        GroupUserRelPo resPo = data.findById(groupUserRelPo.getId()).get();
        assertEquals(resPo.getId(), groupUserRelPo.getId());
        assertEquals(resPo.getGroupId(),groupUserRelPo.getGroupId());
        assertEquals(resPo.getUserId(),groupUserRelPo.getUserId());
        assertEquals(resPo.getLevel(),groupUserRelPo.getLevel());
    }

    @Test
    public void testUpdate() {
        groupUserRelPo = data.save(groupUserRelPo);
        groupUserRelPo = new GroupUserRelPo(groupUserRelPo.getId(), 4396L,4397L,"NULL");
        groupUserRelPo = data.save(groupUserRelPo);
        GroupUserRelPo resPo = data.findById(groupUserRelPo.getId()).get();
        assertEquals(resPo.getId(), groupUserRelPo.getId());
        assertEquals(resPo.getGroupId(),groupUserRelPo.getGroupId());
        assertEquals(resPo.getUserId(),groupUserRelPo.getUserId());
        assertEquals(resPo.getLevel(),groupUserRelPo.getLevel());
    }

    @Test
    public void testRemove() {
        groupUserRelPo = data.save(groupUserRelPo);
        data.deleteById(groupUserRelPo.getId());
        var res = data.findById(groupUserRelPo.getId()).orElse(null);
        assertEquals(res, null);
    }

    @Test
    public void testFindByUserId() {
        groupUserRelPo = data.save(groupUserRelPo);
        GroupUserRelPo groupUserRelPo1 = data.save(new GroupUserRelPo(12345L, 1L,3L,"asda"));
        GroupUserRelPo groupUserRelPo2 = data.save(new GroupUserRelPo(12346L, 2L,3L,"4"));
        List<GroupUserRelPo> list = data.findByUserId(3L);
        assertEquals(list.size(),2);
        list.sort((a, b) -> (int) (a.getId() - b.getId()));
        assertEquals(list.get(0).getId(), groupUserRelPo1.getId());
        assertEquals(list.get(0).getGroupId(),groupUserRelPo1.getGroupId());
        assertEquals(list.get(0).getUserId(),groupUserRelPo1.getUserId());
        assertEquals(list.get(0).getLevel(),groupUserRelPo1.getLevel());

        assertEquals(list.get(1).getId(), groupUserRelPo2.getId());
        assertEquals(list.get(1).getGroupId(),groupUserRelPo2.getGroupId());
        assertEquals(list.get(1).getUserId(),groupUserRelPo2.getUserId());
        assertEquals(list.get(1).getLevel(),groupUserRelPo2.getLevel());

        
    }

    @Test
    public void testFindByGroupId() {
        groupUserRelPo = data.save(groupUserRelPo);
        GroupUserRelPo groupUserRelPo1 = data.save(new GroupUserRelPo(12345L, 1L,3L,"asda"));
        // GroupUserRelPo groupUserRelPo2 = 
        data.save(new GroupUserRelPo(12346L, 2L,3L,"4"));
        List<GroupUserRelPo> list = data.findByGroupId(1L);
        assertEquals(list.size(),2);
        list.sort((a, b) -> (int) (a.getId() - b.getId()));
        assertEquals(list.get(0).getId(), groupUserRelPo.getId());
        assertEquals(list.get(0).getGroupId(),groupUserRelPo.getGroupId());
        assertEquals(list.get(0).getUserId(),groupUserRelPo.getUserId());
        assertEquals(list.get(0).getLevel(),groupUserRelPo.getLevel());

        assertEquals(list.get(1).getId(), groupUserRelPo1.getId());
        assertEquals(list.get(1).getGroupId(),groupUserRelPo1.getGroupId());
        assertEquals(list.get(1).getUserId(),groupUserRelPo1.getUserId());
        assertEquals(list.get(1).getLevel(),groupUserRelPo1.getLevel());

        
    }
    @Test
    public void testFindByGroupIdAndUserId() {
        groupUserRelPo = data.save(groupUserRelPo);
        GroupUserRelPo groupUserRelPo1 = data.save(new GroupUserRelPo(12345L, 1L,3L,"asda"));
        // GroupUserRelPo groupUserRelPo2 = 
        data.save(new GroupUserRelPo(12346L, 2L,3L,"4"));
        GroupUserRelPo resPo= data.findByGroupIdAndUserId(1L,3L);

        assertEquals(resPo.getId(), groupUserRelPo1.getId());
        assertEquals(resPo.getGroupId(),groupUserRelPo1.getGroupId());
        assertEquals(resPo.getUserId(),groupUserRelPo1.getUserId());
        assertEquals(resPo.getLevel(),groupUserRelPo1.getLevel());
        
    }
    @Test
    public void testDeleteByGroupId() {
        groupUserRelPo = data.save(groupUserRelPo);
        // GroupUserRelPo groupUserRelPo1 = 
        data.save(new GroupUserRelPo(12345L, 1L,3L,"asda"));
        // GroupUserRelPo groupUserRelPo2 = 
        data.save(new GroupUserRelPo(12346L, 2L,3L,"4"));
        data.deleteByGroupId(1L);
        List<GroupUserRelPo> list = data.findByGroupId(1L);
        assertEquals(list.size(),0);
        list=data.findByGroupId(2L);
        assertEquals(list.size(),1);
        
    }
    @Test
    public void testDeleteByGroupIdAndUserId() {
        groupUserRelPo = data.save(groupUserRelPo);
        GroupUserRelPo groupUserRelPo1 = data.save(new GroupUserRelPo(12345L, 1L,3L,"asda"));
        // GroupUserRelPo groupUserRelPo2 = 
        data.save(new GroupUserRelPo(12346L, 2L,3L,"4"));
        data.deleteByGroupIdAndUserId(1L,2L);
        List<GroupUserRelPo> list = data.findByGroupId(1L);
        assertEquals(list.size(),1);

        assertEquals(list.get(0).getId(), groupUserRelPo1.getId());
        assertEquals(list.get(0).getGroupId(),groupUserRelPo1.getGroupId());
        assertEquals(list.get(0).getUserId(),groupUserRelPo1.getUserId());
        assertEquals(list.get(0).getLevel(),groupUserRelPo1.getLevel());

        
    }


    
}
