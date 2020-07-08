package local.happysixplus.backendcodeanalysis.data;

import lombok.var;
import local.happysixplus.backendcodeanalysis.po.GroupPo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class GroupDataTest {

    @Autowired
    GroupData data;

    GroupPo groupPo = new GroupPo();

    @BeforeEach
    void init() {
        data.deleteAll();
        groupPo = new GroupPo(1234L, 1L, "SKT","Faker");
    }

    @AfterEach
    void tearDown() {
        data.deleteAll();
    }

    @Test
    public void testInsert() {
        groupPo = data.save(groupPo);
        GroupPo resPo = data.findById(groupPo.getId()).get();
        assertEquals(resPo.getId(), groupPo.getId());
        assertEquals(resPo.getLeaderId(),groupPo.getLeaderId());
        assertEquals(resPo.getInviteCode(),groupPo.getInviteCode());
        assertEquals(resPo.getName(),groupPo.getName());
    }

    @Test
    public void testUpdate() {
        groupPo = data.save(groupPo);
        groupPo = new GroupPo(groupPo.getId(), 4396L,"RNG","Uzi");
        groupPo = data.save(groupPo);
        GroupPo resPo = data.findById(groupPo.getId()).get();
        assertEquals(resPo.getId(), groupPo.getId());
        assertEquals(resPo.getLeaderId(),groupPo.getLeaderId());
        assertEquals(resPo.getInviteCode(),groupPo.getInviteCode());
        assertEquals(resPo.getName(),groupPo.getName());
    }

    @Test
    public void testRemove() {
        groupPo = data.save(groupPo);
        data.deleteById(groupPo.getId());
        var res = data.findById(groupPo.getId()).orElse(null);
        assertEquals(res, null);
    }

    @Test
    public void testFindByIdIn() {
        groupPo = data.save(groupPo);
        GroupPo groupPo1 = data.save(new GroupPo(12345L, 1L,"SKT","otto"));
        GroupPo groupPo2 = data.save(new GroupPo(12346L, 2L, "RNG","Xiaohu"));
        List<Long> ids=new ArrayList<>();
        ids.add(groupPo1.getId());ids.add(groupPo2.getId());
        List<GroupPo> list = data.findByIdIn(ids);
        list.sort((a, b) -> (int) (a.getId() - b.getId()));
        assertEquals(list.get(0).getId(), groupPo1.getId());
        assertEquals(list.get(0).getLeaderId(), groupPo1.getLeaderId());
        assertEquals(list.get(0).getName(), groupPo1.getName());
        assertEquals(list.get(0).getInviteCode(),groupPo1.getInviteCode());

        assertEquals(list.get(1).getId(), groupPo2.getId());
        assertEquals(list.get(1).getLeaderId(), groupPo2.getLeaderId());
        assertEquals(list.get(1).getName(), groupPo2.getName());
        assertEquals(list.get(1).getInviteCode(),groupPo2.getInviteCode());

        
    }

    
}
