package local.happysixplus.backendcodeanalysis.data;

import local.happysixplus.backendcodeanalysis.po.ConnectiveDomainPo;
import local.happysixplus.backendcodeanalysis.po.EdgePo;
import local.happysixplus.backendcodeanalysis.po.ProjectPo;
import local.happysixplus.backendcodeanalysis.po.SubgraphPo;
import local.happysixplus.backendcodeanalysis.po.VertexPo;
import lombok.var;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

@SpringBootTest
class ProjectDataTest {

    @Autowired
    ProjectData data;

    @Autowired
    SubgraphData subgraphData;

    @BeforeEach
    void init() {
        data.deleteAll();
        subgraphData.deleteAll();
    }

    @AfterEach
    void tearDown() {
        data.deleteAll();
        subgraphData.deleteAll();
    }

    @Test
    public void insertTest1() {
        var projectPo = new ProjectPo(null, 1L, "projectName 1", null, null, null);
        // Vertices
        var v1 = new VertexPo(null, "cnmd1", "nmbd yuanma 1", "anotation", 0f, 0f);
        var v2 = new VertexPo(null, "cnmd2", "nmbd yuanma 2", "anotation", 0f, 0f);
        var v3 = new VertexPo(null, "cnmd3", "nmbd yuanma 3", "anotation", 0f, 0f);
        var v4 = new VertexPo(null, "cnmd4", "nmbd yuanma 4", "anotation", 0f, 0f);
        var vertices = new HashSet<VertexPo>();
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);
        vertices.add(v4);
        projectPo.setVertices(vertices);
        // Edges
        var e1 = new EdgePo(null, v1, v2, 0.3d, "edge 1");
        var e2 = new EdgePo(null, v2, v3, 0.1d, "edge 2");
        var e3 = new EdgePo(null, v3, v4, 0.3d, "edge 3");
        var edges = new HashSet<EdgePo>();
        edges.add(e1);
        edges.add(e2);
        edges.add(e3);
        projectPo.setEdges(edges);
        // Subgraphs
        var connectiveDomain1 = new ConnectiveDomainPo(null, vertices, edges, "anotation cd1", "#CDBE70");
        var cd2vSet = new HashSet<VertexPo>();
        cd2vSet.add(v1);
        cd2vSet.add(v2);
        var cd2eSet = new HashSet<EdgePo>();
        cd2eSet.add(e1);
        var connectiveDomain2 = new ConnectiveDomainPo(null, cd2vSet, cd2eSet, "anotation cd2", "#CDCDB4");
        var cd3vSet = new HashSet<VertexPo>();
        cd3vSet.add(v3);
        cd3vSet.add(v4);
        var cd3eSet = new HashSet<EdgePo>();
        cd3eSet.add(e3);
        var connectiveDomain3 = new ConnectiveDomainPo(null, cd3vSet, cd3eSet, "anotation cd3", "#CDB5CD");
        var connectiveDomains1 = new HashSet<ConnectiveDomainPo>();
        connectiveDomains1.add(connectiveDomain1);
        var connectiveDomains2 = new HashSet<ConnectiveDomainPo>();
        connectiveDomains2.add(connectiveDomain2);
        connectiveDomains2.add(connectiveDomain3);
        var defaultSubgraph = new SubgraphPo(null, 0d, "default subgraph", connectiveDomains1);
        var subgraph1 = new SubgraphPo(null, 0.2d, "Subgraph 1 cnmd", connectiveDomains2);
        var subgraphs = new HashSet<SubgraphPo>();
        subgraphs.add(defaultSubgraph);
        subgraphs.add(subgraph1);
        projectPo.setSubgraphs(subgraphs);
        data.save(projectPo);

        var newPo = data.findById(projectPo.getId()).orElse(null);

        assertEquals(projectPo.getUserId(), newPo.getUserId());
        assertEquals(projectPo.getProjectName(), newPo.getProjectName());
        assertEquals(projectPo.getEdges().size(), newPo.getEdges().size());
        assertEquals(projectPo.getVertices().size(), newPo.getVertices().size());
        assertEquals(projectPo.getSubgraphs().size(), newPo.getSubgraphs().size());
    }

    @Test
    public void insertTest2() {
        var projectPo = new ProjectPo(null, 1L, "projC", null, null, null);
        // Vertices
        var v1 = new VertexPo(null, "v1", "dian1", "a1", 0f, 0f);
        var v2 = new VertexPo(null, "v2", "dian2", "a2", 0f, 0f);
        var vertices = new HashSet<VertexPo>();
        vertices.add(v1);
        vertices.add(v2);
        projectPo.setVertices(vertices);
        // Edges
        var e1 = new EdgePo(null, v1, v2, 0.3d, "e1");
        var edges = new HashSet<EdgePo>();
        edges.add(e1);
        projectPo.setEdges(edges);
        // Subgraphs
        var connectiveDomain1 = new ConnectiveDomainPo(null, vertices, edges, "acd1", "#CDBE70");
        var connectiveDomains1 = new HashSet<ConnectiveDomainPo>();
        connectiveDomains1.add(connectiveDomain1);
        var defaultSubgraph = new SubgraphPo(null, 0d, "default subgraph", connectiveDomains1);
        var subgraphs = new HashSet<SubgraphPo>();
        subgraphs.add(defaultSubgraph);
        projectPo.setSubgraphs(subgraphs);
        data.save(projectPo);

        var newPo = data.findById(projectPo.getId()).orElse(null);

        assertEquals(projectPo.getUserId(), newPo.getUserId());
        assertEquals(projectPo.getProjectName(), newPo.getProjectName());
        assertEquals(projectPo.getEdges().size(), newPo.getEdges().size());
        assertEquals(projectPo.getVertices().size(), newPo.getVertices().size());
        assertEquals(projectPo.getSubgraphs().size(), newPo.getSubgraphs().size());
    }
}