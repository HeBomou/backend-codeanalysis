package local.happysixplus.backendcodeanalysis.data;

import local.happysixplus.backendcodeanalysis.po.ConnectiveDomainColorDynamicPo;
import lombok.var;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

@SpringBootTest
class ConnectiveDomainColorDynamicDataTest {

    @Autowired
    ConnectiveDomainColorDynamicData data;

    ConnectiveDomainColorDynamicPo connectiveDomainColorDynamicPo = new ConnectiveDomainColorDynamicPo();

    @BeforeEach
    void init() {
        data.deleteAll();
        connectiveDomainColorDynamicPo = new ConnectiveDomainColorDynamicPo(1234L, 1L, "SKTFaker");
    }

    @AfterEach
    void tearDown() {
        data.deleteAll();
    }

    @Test
    public void testInsert() {
        connectiveDomainColorDynamicPo = data.save(connectiveDomainColorDynamicPo);
        ConnectiveDomainColorDynamicPo resPo = data.findById(connectiveDomainColorDynamicPo.getId()).get();
        assertEquals(resPo.getId(), connectiveDomainColorDynamicPo.getId());
        assertEquals(resPo.getProjectId(), connectiveDomainColorDynamicPo.getProjectId());
        assertEquals(resPo.getColor(), connectiveDomainColorDynamicPo.getColor());
    }

    @Test
    public void testUpdate() {
        connectiveDomainColorDynamicPo = data.save(connectiveDomainColorDynamicPo);
        connectiveDomainColorDynamicPo = new ConnectiveDomainColorDynamicPo(connectiveDomainColorDynamicPo.getId(),
                4396L, "Mother fucker");
        connectiveDomainColorDynamicPo = data.save(connectiveDomainColorDynamicPo);
        ConnectiveDomainColorDynamicPo resPo = data.findById(connectiveDomainColorDynamicPo.getId()).get();
        assertEquals(resPo.getId(), connectiveDomainColorDynamicPo.getId());
        assertEquals(resPo.getProjectId(), connectiveDomainColorDynamicPo.getProjectId());
        assertEquals(resPo.getColor(), connectiveDomainColorDynamicPo.getColor());
    }

    @Test
    public void testRemove() {
        connectiveDomainColorDynamicPo = data.save(connectiveDomainColorDynamicPo);
        data.deleteById(connectiveDomainColorDynamicPo.getId());
        var res = data.findById(connectiveDomainColorDynamicPo.getId()).orElse(null);
        assertEquals(res, null);
    }

    @Test
    public void testFindByProjectId() {
        connectiveDomainColorDynamicPo = data.save(connectiveDomainColorDynamicPo);
        ConnectiveDomainColorDynamicPo connectiveDomainColorDynamicPo1 = data
                .save(new ConnectiveDomainColorDynamicPo(12345L, 1L, "SKTKhan"));
        ConnectiveDomainColorDynamicPo connectiveDomainColorDynamicPo2 = data
                .save(new ConnectiveDomainColorDynamicPo(12346L, 1L, "SKTCLid"));
        List<ConnectiveDomainColorDynamicPo> list = data.findByProjectId(1L);
        list.sort((a, b) -> (int) (a.getId() - b.getId()));
        assertEquals(list.get(0).getId(), connectiveDomainColorDynamicPo.getId());
        assertEquals(list.get(0).getProjectId(), connectiveDomainColorDynamicPo.getProjectId());
        assertEquals(list.get(0).getColor(), connectiveDomainColorDynamicPo.getColor());

        assertEquals(list.get(1).getId(), connectiveDomainColorDynamicPo1.getId());
        assertEquals(list.get(1).getProjectId(), connectiveDomainColorDynamicPo1.getProjectId());
        assertEquals(list.get(1).getColor(), connectiveDomainColorDynamicPo1.getColor());

        assertEquals(list.get(2).getId(), connectiveDomainColorDynamicPo2.getId());
        assertEquals(list.get(2).getProjectId(), connectiveDomainColorDynamicPo2.getProjectId());
        assertEquals(list.get(2).getColor(), connectiveDomainColorDynamicPo2.getColor());
    }

    @Test
    public void testDeleteByProjectId() {
        connectiveDomainColorDynamicPo = data.save(connectiveDomainColorDynamicPo);
        data.save(new ConnectiveDomainColorDynamicPo(12347L, 1L, "SKTKhan"));
        data.save(new ConnectiveDomainColorDynamicPo(12348L, 1L, "SKTCLid"));
        data.deleteByProjectId(connectiveDomainColorDynamicPo.getId());
        List<ConnectiveDomainColorDynamicPo> list = data.findByProjectId(connectiveDomainColorDynamicPo.getId());
        assertEquals(list, new ArrayList<ConnectiveDomainColorDynamicPo>());
    }

    @Test
    public void testCountByProjectId() {
        connectiveDomainColorDynamicPo = data.save(connectiveDomainColorDynamicPo);
        data.save(new ConnectiveDomainColorDynamicPo(12347L, 1L, "SKTKhan"));
        data.save(new ConnectiveDomainColorDynamicPo(12348L, 1L, "SKTCLid"));
        int count = data.countByProjectId(1L);
        assertEquals(3, count);
    }
}
