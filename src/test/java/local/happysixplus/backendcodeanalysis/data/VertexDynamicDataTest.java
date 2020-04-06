package local.happysixplus.backendcodeanalysis.data;

import local.happysixplus.backendcodeanalysis.po.VertexDynamicPo;
import lombok.var;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

@SpringBootTest
class VertexDynamicDataTest {

    @Autowired
    VertexDynamicData data;

    VertexDynamicPo vertexDynamicPo = new VertexDynamicPo();

    @BeforeEach
    void init() {
        data.deleteAll();
        vertexDynamicPo = new VertexDynamicPo(4444L, 1L, "SKTFaker");
    }

    @AfterEach
    void tearDown() {
        data.deleteAll();
    }

    @Test
    public void testInsert() {
        vertexDynamicPo = data.save(vertexDynamicPo);
        VertexDynamicPo resPo = data.findById(vertexDynamicPo.getId()).get();
        assertEquals(resPo.getId(), vertexDynamicPo.getId());
        assertEquals(resPo.getProjectId(), vertexDynamicPo.getProjectId());
        assertEquals(resPo.getAnotation(), vertexDynamicPo.getAnotation());
    }

    @Test
    public void testUpdate() {
        vertexDynamicPo = data.save(vertexDynamicPo);
        vertexDynamicPo = new VertexDynamicPo(vertexDynamicPo.getId(), 4396L, "Mother fucker");
        vertexDynamicPo = data.save(vertexDynamicPo);
        VertexDynamicPo resPo = data.findById(vertexDynamicPo.getId()).get();
        assertEquals(resPo.getId(), vertexDynamicPo.getId());
        assertEquals(resPo.getProjectId(), vertexDynamicPo.getProjectId());
        assertEquals(resPo.getAnotation(), vertexDynamicPo.getAnotation());

    }

    @Test
    public void testRemove() {
        vertexDynamicPo = data.save(vertexDynamicPo);
        data.deleteById(vertexDynamicPo.getId());
        var res = data.findById(vertexDynamicPo.getId()).orElse(null);
        assertEquals(res, null);
    }

    @Test
    public void testFindByProjectId() {
        vertexDynamicPo = data.save(vertexDynamicPo);
        VertexDynamicPo vertexDynamicPo1 = data.save(new VertexDynamicPo(4657L, 1L, "SKTKhan"));
        VertexDynamicPo vertexDynamicPo2 = data.save(new VertexDynamicPo(4658L, 1L, "SKTCLid"));
        List<VertexDynamicPo> list = data.findByProjectId(1L);
        list.sort((a, b) -> (int) (a.getId() - b.getId()));
        assertEquals(list.get(0).getId(), vertexDynamicPo.getId());
        assertEquals(list.get(0).getProjectId(), vertexDynamicPo.getProjectId());
        assertEquals(list.get(0).getAnotation(), vertexDynamicPo.getAnotation());

        assertEquals(list.get(1).getId(), vertexDynamicPo1.getId());
        assertEquals(list.get(1).getProjectId(), vertexDynamicPo1.getProjectId());
        assertEquals(list.get(1).getAnotation(), vertexDynamicPo1.getAnotation());

        assertEquals(list.get(2).getId(), vertexDynamicPo2.getId());
        assertEquals(list.get(2).getProjectId(), vertexDynamicPo2.getProjectId());
        assertEquals(list.get(2).getAnotation(), vertexDynamicPo2.getAnotation());
    }

    @Test
    public void testDeleteByProjectId() {
        vertexDynamicPo = data.save(vertexDynamicPo);
        data.save(new VertexDynamicPo(4657L, 1L, "SKTKhan"));
        data.save(new VertexDynamicPo(4658L, 1L, "SKTCLid"));
        data.deleteByProjectId(vertexDynamicPo.getId());
        List<VertexDynamicPo> list = data.findByProjectId(vertexDynamicPo.getId());
        assertEquals(list, new ArrayList<VertexDynamicPo>());
    }

    @Test
    public void testCountByProjectId() {
        vertexDynamicPo = data.save(vertexDynamicPo);
        data.save(new VertexDynamicPo(4657L, 1L, "SKTKhan"));
        data.save(new VertexDynamicPo(4658L, 1L, "SKTCLid"));
        int count = data.countByProjectId(1L);
        assertEquals(3, count);
    }
}
