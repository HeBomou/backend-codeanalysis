package local.happysixplus.backendcodeanalysis.data;

import local.happysixplus.backendcodeanalysis.po.VertexPositionDynamicPo;
import lombok.var;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

@SpringBootTest
class VertexPositionDynamicDataTest {

    @Autowired
    VertexPositionDynamicData data;

    VertexPositionDynamicPo vertexPositionDynamicPo = new VertexPositionDynamicPo();

    @BeforeEach
    void init() {
        data.deleteAll();
        vertexPositionDynamicPo = new VertexPositionDynamicPo(4444L, 1L, 0.1f, 0.1f);
    }

    @AfterEach
    void tearDown() {
        data.deleteAll();
    }

    @Test
    public void testInsert() {
        vertexPositionDynamicPo = data.save(vertexPositionDynamicPo);
        VertexPositionDynamicPo resPo = data.findById(vertexPositionDynamicPo.getId()).get();
        assertEquals(resPo.getId(), vertexPositionDynamicPo.getId());
        assertEquals(resPo.getProjectId(), vertexPositionDynamicPo.getProjectId());
        assertEquals(resPo.getX(), vertexPositionDynamicPo.getX());
        assertEquals(resPo.getY(), vertexPositionDynamicPo.getY());
    }

    @Test
    public void testUpdate() {
        vertexPositionDynamicPo = data.save(vertexPositionDynamicPo);
        vertexPositionDynamicPo = new VertexPositionDynamicPo(vertexPositionDynamicPo.getId(), 4396L, 0.2f, 0.2f);
        vertexPositionDynamicPo = data.save(vertexPositionDynamicPo);
        VertexPositionDynamicPo resPo = data.findById(vertexPositionDynamicPo.getId()).get();
        assertEquals(resPo.getId(), vertexPositionDynamicPo.getId());
        assertEquals(resPo.getProjectId(), vertexPositionDynamicPo.getProjectId());
        assertEquals(resPo.getX(), vertexPositionDynamicPo.getX());
        assertEquals(resPo.getY(), vertexPositionDynamicPo.getY());

    }

    @Test
    public void testRemove() {
        vertexPositionDynamicPo = data.save(vertexPositionDynamicPo);
        data.deleteById(vertexPositionDynamicPo.getId());
        var res = data.findById(vertexPositionDynamicPo.getId()).orElse(null);
        assertEquals(res, null);
    }

    @Test
    public void testFindByProjectId() {
        vertexPositionDynamicPo = data.save(vertexPositionDynamicPo);
        VertexPositionDynamicPo vertexPositionDynamicPo1 = data
                .save(new VertexPositionDynamicPo(4657L, 1L, 0.3f, 0.3f));
        VertexPositionDynamicPo vertexPositionDynamicPo2 = data
                .save(new VertexPositionDynamicPo(4658L, 1L, 0.4f, 0.4f));
        List<VertexPositionDynamicPo> list = data.findByProjectId(1L);
        list.sort((a, b) -> (int) (a.getId() - b.getId()));
        assertEquals(list.get(0).getId(), vertexPositionDynamicPo.getId());
        assertEquals(list.get(0).getProjectId(), vertexPositionDynamicPo.getProjectId());
        assertEquals(list.get(0).getX(), vertexPositionDynamicPo.getX());
        assertEquals(list.get(0).getY(), vertexPositionDynamicPo.getY());

        assertEquals(list.get(1).getId(), vertexPositionDynamicPo1.getId());
        assertEquals(list.get(1).getProjectId(), vertexPositionDynamicPo1.getProjectId());
        assertEquals(list.get(1).getX(), vertexPositionDynamicPo1.getX());
        assertEquals(list.get(1).getY(), vertexPositionDynamicPo1.getY());

        assertEquals(list.get(2).getId(), vertexPositionDynamicPo2.getId());
        assertEquals(list.get(2).getProjectId(), vertexPositionDynamicPo2.getProjectId());
        assertEquals(list.get(2).getX(), vertexPositionDynamicPo2.getX());
        assertEquals(list.get(2).getY(), vertexPositionDynamicPo2.getY());
    }

    @Test
    public void testDeleteByProjectId() {
        vertexPositionDynamicPo = data.save(vertexPositionDynamicPo);
        data.save(new VertexPositionDynamicPo(4657L, 1L, 0.5f, 0.5f));
        data.save(new VertexPositionDynamicPo(4658L, 1L, 0.7f, 0.7f));
        data.deleteByProjectId(vertexPositionDynamicPo.getId());
        List<VertexPositionDynamicPo> list = data.findByProjectId(vertexPositionDynamicPo.getId());
        assertEquals(list, new ArrayList<VertexPositionDynamicPo>());
    }

    @Test
    public void testCountByProjectId() {
        vertexPositionDynamicPo = data.save(vertexPositionDynamicPo);
        data.save(new VertexPositionDynamicPo(4657L, 1L, 0.5f, 0.5f));
        data.save(new VertexPositionDynamicPo(4658L, 1L, 0.7f, 0.7f));
        int count = data.countByProjectId(1L);
        assertEquals(3, count);
    }
}
