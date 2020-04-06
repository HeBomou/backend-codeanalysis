package local.happysixplus.backendcodeanalysis.data;

import local.happysixplus.backendcodeanalysis.po.ConnectiveDomainDynamicPo;
import lombok.var;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

@SpringBootTest
class ConnectiveDomainDynamicDataTest {

    @Autowired
    ConnectiveDomainDynamicData data;

    ConnectiveDomainDynamicPo connectiveDomainDynamicPo = new ConnectiveDomainDynamicPo();

    @BeforeEach
    void init() {
        data.deleteAll();
        connectiveDomainDynamicPo = new ConnectiveDomainDynamicPo(1234L, 1L, "SKTFaker");
    }

    @AfterEach
    void tearDown() {
        data.deleteAll();
    }

    @Test
    public void testInsert() {
        connectiveDomainDynamicPo = data.save(connectiveDomainDynamicPo);
        ConnectiveDomainDynamicPo resPo = data.findById(connectiveDomainDynamicPo.getId()).get();
        assertEquals(resPo.getId(), connectiveDomainDynamicPo.getId());
        assertEquals(resPo.getProjectId(), connectiveDomainDynamicPo.getProjectId());
        assertEquals(resPo.getAnotation(), connectiveDomainDynamicPo.getAnotation());
    }

    @Test
    public void testUpdate() {
        connectiveDomainDynamicPo = data.save(connectiveDomainDynamicPo);
        connectiveDomainDynamicPo = new ConnectiveDomainDynamicPo(connectiveDomainDynamicPo.getId(), 4396L,
                "Mother fucker");
        connectiveDomainDynamicPo = data.save(connectiveDomainDynamicPo);
        ConnectiveDomainDynamicPo resPo = data.findById(connectiveDomainDynamicPo.getId()).get();
        assertEquals(resPo.getId(), connectiveDomainDynamicPo.getId());
        assertEquals(resPo.getProjectId(), connectiveDomainDynamicPo.getProjectId());
        assertEquals(resPo.getAnotation(), connectiveDomainDynamicPo.getAnotation());
    }

    @Test
    public void testRemove() {
        connectiveDomainDynamicPo = data.save(connectiveDomainDynamicPo);
        data.deleteById(connectiveDomainDynamicPo.getId());
        var res = data.findById(connectiveDomainDynamicPo.getId()).orElse(null);
        assertEquals(res, null);
    }

    @Test
    public void testFindByProjectId() {
        connectiveDomainDynamicPo = data.save(connectiveDomainDynamicPo);
        ConnectiveDomainDynamicPo connectiveDomainDynamicPo1 = data
                .save(new ConnectiveDomainDynamicPo(12345L, 1L, "SKTKhan"));
        ConnectiveDomainDynamicPo connectiveDomainDynamicPo2 = data
                .save(new ConnectiveDomainDynamicPo(12346L, 1L, "SKTCLid"));
        List<ConnectiveDomainDynamicPo> list = data.findByProjectId(1L);
        list.sort((a, b) -> (int) (a.getId() - b.getId()));
        assertEquals(list.get(0).getId(), connectiveDomainDynamicPo.getId());
        assertEquals(list.get(0).getProjectId(), connectiveDomainDynamicPo.getProjectId());
        assertEquals(list.get(0).getAnotation(), connectiveDomainDynamicPo.getAnotation());

        assertEquals(list.get(1).getId(), connectiveDomainDynamicPo1.getId());
        assertEquals(list.get(1).getProjectId(), connectiveDomainDynamicPo1.getProjectId());
        assertEquals(list.get(1).getAnotation(), connectiveDomainDynamicPo1.getAnotation());

        assertEquals(list.get(2).getId(), connectiveDomainDynamicPo2.getId());
        assertEquals(list.get(2).getProjectId(), connectiveDomainDynamicPo2.getProjectId());
        assertEquals(list.get(2).getAnotation(), connectiveDomainDynamicPo2.getAnotation());
    }

    @Test
    public void testDeleteByProjectId() {
        connectiveDomainDynamicPo = data.save(connectiveDomainDynamicPo);
        data.save(new ConnectiveDomainDynamicPo(12347L, 1L, "SKTKhan"));
        data.save(new ConnectiveDomainDynamicPo(12348L, 1L, "SKTCLid"));
        data.deleteByProjectId(connectiveDomainDynamicPo.getId());
        List<ConnectiveDomainDynamicPo> list = data.findByProjectId(connectiveDomainDynamicPo.getId());
        assertEquals(list, new ArrayList<ConnectiveDomainDynamicPo>());
    }

    @Test
    public void testCountByProjectId() {
        connectiveDomainDynamicPo = data.save(connectiveDomainDynamicPo);
        data.save(new ConnectiveDomainDynamicPo(12347L, 1L, "SKTKhan"));
        data.save(new ConnectiveDomainDynamicPo(12348L, 1L, "SKTCLid"));
        int count = data.countByProjectId(1L);
        assertEquals(3, count);
    }

}
