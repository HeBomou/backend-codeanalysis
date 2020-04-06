package local.happysixplus.backendcodeanalysis.data;

import local.happysixplus.backendcodeanalysis.po.SubgraphDynamicPo;
import lombok.var;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

@SpringBootTest
class SubgraphDynamicDataTest {

    @Autowired
    SubgraphDynamicData data;

    SubgraphDynamicPo po;

    @BeforeEach
    void init() {
        data.deleteAll();
        po = new SubgraphDynamicPo(737L, 1L, "SKTelecomT1Faker");
    }

    @AfterEach
    void tearDown() {
        data.deleteAll();
    }

    @Test
    public void testInsert() {
        po = data.save(po);
        SubgraphDynamicPo resPo = data.findById(po.getId()).get();
        assertEquals(resPo.getId(), po.getId());
        assertEquals(resPo.getProjectId(), po.getProjectId());
        assertEquals(resPo.getName(), po.getName());
    }

    @Test
    public void testUpdate() {
        po = data.save(po);
        po = new SubgraphDynamicPo(po.getId(), 4396L, "Mother fucker");
        po = data.save(po);
        SubgraphDynamicPo resPo = data.findById(po.getId()).get();
        assertEquals(resPo.getId(), po.getId());
        assertEquals(resPo.getProjectId(), po.getProjectId());
        assertEquals(resPo.getName(), po.getName());
    }

    @Test
    public void testRemove() {
        po = data.save(po);
        data.deleteById(po.getId());
        var res = data.findById(po.getId()).orElse(null);
        assertEquals(res, null);
    }

    @Test
    public void testFindByProjectId() {
        po = data.save(po);
        SubgraphDynamicPo po1 = data.save(new SubgraphDynamicPo(765L, 1L, "SKTKhan"));
        SubgraphDynamicPo po2 = data.save(new SubgraphDynamicPo(768L, 1L, "SKTCLid"));
        List<SubgraphDynamicPo> list = data.findByProjectId(1L);
        list.sort((a, b) -> (int) (a.getId() - b.getId()));
        assertEquals(list.get(0).getId(), po.getId());
        assertEquals(list.get(0).getProjectId(), po.getProjectId());
        assertEquals(list.get(0).getName(), po.getName());
        assertEquals(list.get(1).getId(), po1.getId());
        assertEquals(list.get(1).getProjectId(), po1.getProjectId());
        assertEquals(list.get(1).getName(), po1.getName());
        assertEquals(list.get(2).getId(), po2.getId());
        assertEquals(list.get(2).getProjectId(), po2.getProjectId());
        assertEquals(list.get(2).getName(), po2.getName());
    }

    @Test
    public void testDeleteByProjectId() {
        po = data.save(po);
        data.save(new SubgraphDynamicPo(765L, 1L, "SKTKhan"));
        data.save(new SubgraphDynamicPo(768L, 1L, "SKTCLid"));
        data.deleteByProjectId(1L);
        List<SubgraphDynamicPo> list = data.findByProjectId(1L);
        assertEquals(list, new ArrayList<SubgraphDynamicPo>());
    }

}
