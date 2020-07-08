package local.happysixplus.backendcodeanalysis.data;

import lombok.var;
import local.happysixplus.backendcodeanalysis.po.GroupNoticePo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class GroupNoticeDataTest {

    @Autowired
    GroupNoticeData data;

    GroupNoticePo groupNoticePo = new GroupNoticePo();

    @BeforeEach
    void init() {
        data.deleteAll();
        groupNoticePo = new GroupNoticePo(1234L, 1L, "SKT","Faker","123");
    }

    @AfterEach
    void tearDown() {
        data.deleteAll();
    }

    @Test
    public void testInsert() {
        groupNoticePo = data.save(groupNoticePo);
        GroupNoticePo resPo = data.findById(groupNoticePo.getId()).get();
        assertEquals(resPo.getId(), groupNoticePo.getId());
        assertEquals(resPo.getGroupId(),groupNoticePo.getGroupId());
        assertEquals(resPo.getTitle(),groupNoticePo.getTitle());
        assertEquals(resPo.getTime(),groupNoticePo.getTime());
        assertEquals(resPo.getContent(),groupNoticePo.getContent());
    }

    @Test
    public void testUpdate() {
        groupNoticePo = data.save(groupNoticePo);
        groupNoticePo = new GroupNoticePo(groupNoticePo.getId(), 4396L,"RNG","Uzi","sss");
        groupNoticePo = data.save(groupNoticePo);
        GroupNoticePo resPo = data.findById(groupNoticePo.getId()).get();
        assertEquals(resPo.getId(), groupNoticePo.getId());
        assertEquals(resPo.getGroupId(),groupNoticePo.getGroupId());
        assertEquals(resPo.getTitle(),groupNoticePo.getTitle());
        assertEquals(resPo.getTime(),groupNoticePo.getTime());
        assertEquals(resPo.getContent(),groupNoticePo.getContent());
    }

    @Test
    public void testRemove() {
        groupNoticePo = data.save(groupNoticePo);
        data.deleteById(groupNoticePo.getId());
        var res = data.findById(groupNoticePo.getId()).orElse(null);
        assertEquals(res, null);
    }

    @Test
    public void testFindByAllByGroupId() {
        groupNoticePo = data.save(groupNoticePo);
        GroupNoticePo groupNoticePo1 = data.save(new GroupNoticePo(12345L, 1L,"SKT","otto","璐璐King"));
        // GroupNoticePo groupNoticePo2 = 
        data.save(new GroupNoticePo(12346L, 2L, "RNG","Xiaohu","谁敢横刀立马"));
        List<GroupNoticePo> list = data.findAllByGroupId(1L);
        list.sort((a, b) -> (int) (a.getId() - b.getId()));
        assertEquals(list.get(0).getId(), groupNoticePo.getId());
        assertEquals(list.get(0).getGroupId(),groupNoticePo.getGroupId());
        assertEquals(list.get(0).getTitle(),groupNoticePo.getTitle());
        assertEquals(list.get(0).getTime(),groupNoticePo.getTime());
        assertEquals(list.get(0).getContent(),groupNoticePo.getContent());

        assertEquals(list.get(1).getId(), groupNoticePo1.getId());
        assertEquals(list.get(1).getGroupId(),groupNoticePo1.getGroupId());
        assertEquals(list.get(1).getTitle(),groupNoticePo1.getTitle());
        assertEquals(list.get(1).getTime(),groupNoticePo1.getTime());
        assertEquals(list.get(1).getContent(),groupNoticePo1.getContent());
    }

    @Test
    public void testDeleteByGroupId(){
        groupNoticePo = data.save(groupNoticePo);
        // GroupNoticePo groupNoticePo1 = 
        data.save(new GroupNoticePo(12345L, 1L,"SKT","otto","S"));
        data.save(new GroupNoticePo(12346L, 1L, "RNG","Xiaohu","B"));
        data.deleteByGroupId(1L);
        List<GroupNoticePo> list = data.findAllByGroupId(1L);
        assertEquals(list.size(),0);
    }
    
}
