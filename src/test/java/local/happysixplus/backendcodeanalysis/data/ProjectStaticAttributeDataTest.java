package local.happysixplus.backendcodeanalysis.data;

import local.happysixplus.backendcodeanalysis.po.ProjectStaticAttributePo;
import lombok.var;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

@SpringBootTest
class ProjectStaticAttributeDataTest {

    @Autowired
    ProjectStaticAttributeData data;

    ProjectStaticAttributePo projectStaticAttributePo = new ProjectStaticAttributePo();

    @BeforeEach
    void init() {
        data.deleteAll();
        projectStaticAttributePo = new ProjectStaticAttributePo(123L, 1L, 1, 2, 3, -1l);
    }

    @AfterEach
    void tearDown() {
        data.deleteAll();
    }

    @Test
    public void testInsert() {
        projectStaticAttributePo = data.save(projectStaticAttributePo);
        ProjectStaticAttributePo resPo = data.findById(projectStaticAttributePo.getId()).get();
        assertEquals(resPo.getId(), projectStaticAttributePo.getId());
        assertEquals(resPo.getUserId(), projectStaticAttributePo.getUserId());
        assertEquals(resPo.getVertexNum(), projectStaticAttributePo.getVertexNum());
        assertEquals(resPo.getEdgeNum(), projectStaticAttributePo.getEdgeNum());
        assertEquals(resPo.getConnectiveDomainNum(), projectStaticAttributePo.getConnectiveDomainNum());
    }

    @Test
    public void testUpdate() {
        projectStaticAttributePo = data.save(projectStaticAttributePo);
        projectStaticAttributePo = new ProjectStaticAttributePo(projectStaticAttributePo.getId(), 4396L, 4, 5, 6, -1l);
        projectStaticAttributePo = data.save(projectStaticAttributePo);
        ProjectStaticAttributePo resPo = data.findById(projectStaticAttributePo.getId()).get();
        assertEquals(resPo.getId(), projectStaticAttributePo.getId());
        assertEquals(resPo.getUserId(), projectStaticAttributePo.getUserId());
        assertEquals(resPo.getVertexNum(), projectStaticAttributePo.getVertexNum());
        assertEquals(resPo.getEdgeNum(), projectStaticAttributePo.getEdgeNum());
        assertEquals(resPo.getConnectiveDomainNum(), projectStaticAttributePo.getConnectiveDomainNum());
    }

    @Test
    public void testRemove() {
        projectStaticAttributePo = data.save(projectStaticAttributePo);
        data.deleteById(projectStaticAttributePo.getId());
        var res = data.findById(projectStaticAttributePo.getId()).orElse(null);
        assertEquals(res, null);
    }

    @Test
    public void testFindByUserId() {
        projectStaticAttributePo = data.save(projectStaticAttributePo);
        ProjectStaticAttributePo projectStaticAttributePo1 = data
                .save(new ProjectStaticAttributePo(124L, 1L, 7, 8, 9, -1l));
        ProjectStaticAttributePo projectStaticAttributePo2 = data
                .save(new ProjectStaticAttributePo(125L, 1L, 0, 1, 2, -1l));
        List<ProjectStaticAttributePo> list = data.findByUserId(1L);
        list.sort((a, b) -> (int) (a.getId() - b.getId()));
        assertEquals(list.get(0).getId(), projectStaticAttributePo.getId());
        assertEquals(list.get(0).getUserId(), projectStaticAttributePo.getUserId());
        assertEquals(list.get(0).getVertexNum(), projectStaticAttributePo.getVertexNum());
        assertEquals(list.get(0).getEdgeNum(), projectStaticAttributePo.getEdgeNum());
        assertEquals(list.get(0).getConnectiveDomainNum(), projectStaticAttributePo.getConnectiveDomainNum());

        assertEquals(list.get(1).getId(), projectStaticAttributePo1.getId());
        assertEquals(list.get(1).getUserId(), projectStaticAttributePo1.getUserId());
        assertEquals(list.get(1).getVertexNum(), projectStaticAttributePo1.getVertexNum());
        assertEquals(list.get(1).getEdgeNum(), projectStaticAttributePo1.getEdgeNum());
        assertEquals(list.get(1).getConnectiveDomainNum(), projectStaticAttributePo1.getConnectiveDomainNum());

        assertEquals(list.get(2).getId(), projectStaticAttributePo2.getId());
        assertEquals(list.get(2).getUserId(), projectStaticAttributePo2.getUserId());
        assertEquals(list.get(2).getVertexNum(), projectStaticAttributePo2.getVertexNum());
        assertEquals(list.get(2).getEdgeNum(), projectStaticAttributePo2.getEdgeNum());
        assertEquals(list.get(2).getConnectiveDomainNum(), projectStaticAttributePo2.getConnectiveDomainNum());

    }

}