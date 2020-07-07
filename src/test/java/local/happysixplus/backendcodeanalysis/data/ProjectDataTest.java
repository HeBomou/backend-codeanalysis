package local.happysixplus.backendcodeanalysis.data;

import local.happysixplus.backendcodeanalysis.po.ProjectPo;
import lombok.var;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ProjectDataTest {

    @Autowired
    ProjectData data;

    ProjectPo projectPo = new ProjectPo();

    @BeforeEach
    void init() {
        projectPo = new ProjectPo(null, 4396L, "eihei", -1l);
    }

    @AfterEach
    void tearDown() {
        data.deleteAll();
    }

    @Test
    public void testInsert() {
        projectPo = data.save(projectPo);
        ProjectPo resPo = data.findById(projectPo.getId()).get();
        assertEquals(resPo.getUserId(), projectPo.getUserId());
        assertEquals(resPo.getId(), projectPo.getId());
        assertEquals(resPo.getPackageStructure(), projectPo.getPackageStructure());
    }

    @Test
    public void testRemove() {
        projectPo = data.save(projectPo);
        data.deleteById(projectPo.getId());
        var resPo = data.findById(projectPo.getId()).orElse(null);
        assertEquals(resPo, null);
    }

    @Test
    public void testUpdate() {
        projectPo = data.save(projectPo);
        projectPo = data.save(new ProjectPo(projectPo.getId(), 4397L, "123345", -1l));
        ProjectPo resPo = data.findById(projectPo.getId()).get();
        assertEquals(resPo.getUserId(), projectPo.getUserId());
        assertEquals(resPo.getId(), projectPo.getId());
        assertEquals(resPo.getPackageStructure(), projectPo.getPackageStructure());
    }

    // @Test
    // public void insertTest1() {
    // projectPo=data.save(projectPo);
    // var newPo = data.findById(projectPo.getId()).orElse(null);
    // assertEquals(projectPo.getUserId(), newPo.getUserId());
    // assertEquals(projectPo.getProjectName(), newPo.getProjectName());
    // assertEquals(projectPo.getEdges().size(), newPo.getEdges().size());
    // assertEquals(projectPo.getVertices().size(), newPo.getVertices().size());
    // assertEquals(projectPo.getSubgraphs().size(), newPo.getSubgraphs().size());
    // }

    // @Test
    // public void insertTest2() {
    // var projectPo = new ProjectPo(null, 1L, "projC", null, null, null);
    // // Vertices
    // var v1 = new VertexPo(null, "v1", "dian1", "a1", 0f, 0f);
    // var v2 = new VertexPo(null, "v2", "dian2", "a2", 0f, 0f);
    // var vertices = new HashSet<VertexPo>();
    // vertices.add(v1);
    // vertices.add(v2);
    // projectPo.setVertices(vertices);
    // // //Edges
    // var e1 = new EdgePo(null, v1, v2, 0.3d, "e1");
    // var edges = new HashSet<EdgePo>();
    // edges.add(e1);
    // projectPo.setEdges(edges);
    // // Subgraphs
    // var connectiveDomain1 = new ConnectiveDomainPo(null, vertices, edges, "acd1",
    // "#CDBE70");
    // var connectiveDomains1 = new HashSet<ConnectiveDomainPo>();
    // connectiveDomains1.add(connectiveDomain1);
    // var defaultSubgraph = new SubgraphPo(null, 0d, "default subgraph",
    // connectiveDomains1);
    // var subgraphs = new HashSet<SubgraphPo>();
    // subgraphs.add(defaultSubgraph);
    // projectPo.setSubgraphs(subgraphs);
    // projectPo=data.save(projectPo);

    // var newPo = data.findById(projectPo.getId()).orElse(null);

    // assertEquals(projectPo.getUserId(), newPo.getUserId());
    // assertEquals(projectPo.getProjectName(), newPo.getProjectName());
    // assertEquals(projectPo.getEdges().size(), newPo.getEdges().size());
    // assertEquals(projectPo.getVertices().size(), newPo.getVertices().size());
    // assertEquals(projectPo.getSubgraphs().size(), newPo.getSubgraphs().size());
    // }

    // @Test
    // public void updateTest(){
    // projectPo=data.save(projectPo);
    // projectPo.setProjectName("SKTFaker");
    // var v1 = new VertexPo(null, "sktfaker1", "nmbd yuanma 1", "anotation", 0f,
    // 0f);
    // var v2 = new VertexPo(null, "sktfaker2", "nmbd yuanma 2", "anotation", 0f,
    // 0f);
    // var v5 = new VertexPo(null, "cnmd1", "nmbd yuanma 1", "anotation", 0f, 0f);
    // var v6 = new VertexPo(null, "cnmd2", "nmbd yuanma 2", "anotation", 0f, 0f);
    // var v3 = new VertexPo(null, "cnmd3", "nmbd yuanma 3", "anotation", 0f, 0f);
    // var v4 = new VertexPo(null, "cnmd4", "nmbd yuanma 4", "anotation", 0f, 0f);
    // var vertices = new HashSet<VertexPo>();
    // vertices.add(v1);
    // vertices.add(v2);
    // vertices.add(v3);
    // vertices.add(v4);
    // vertices.add(v5);
    // vertices.add(v6);
    // projectPo.setVertices(vertices);
    // var e1 = new EdgePo(null, v1, v2, 0.3d, "edge 1");
    // var e2 = new EdgePo(null, v2, v3, 0.1d, "edge 2");
    // var e3 = new EdgePo(null, v3, v4, 0.3d, "edge 3");
    // var e4 = new EdgePo(null,v5,v6,0.2d,"faker1");
    // var e5 = new EdgePo(null,v3,v6,0.2d,"faker2");
    // var e6 = new EdgePo(null,v1,v6,0.2d,"faker3");
    // var edges = new HashSet<EdgePo>();
    // edges.add(e1);
    // edges.add(e2);
    // edges.add(e3);
    // edges.add(e4);
    // edges.add(e5);
    // edges.add(e6);
    // var connectiveDomain1 = new ConnectiveDomainPo(null, vertices, edges,
    // "anotation cd1", "#CDBE70");
    // var connectiveDomains1=new HashSet<ConnectiveDomainPo>();
    // connectiveDomains1.add(connectiveDomain1);
    // var defaultSubgraph = new SubgraphPo(null, 0d, "default subgraph",
    // connectiveDomains1);
    // projectPo.setEdges(edges);
    // var subgraphs=new HashSet<SubgraphPo>();
    // subgraphs.add(defaultSubgraph);
    // projectPo.setSubgraphs(subgraphs);
    // projectPo=data.save(projectPo);
    // var newPo = data.findById(projectPo.getId()).orElse(null);
    // assertEquals(projectPo.getUserId(), newPo.getUserId());
    // assertEquals(projectPo.getProjectName(), newPo.getProjectName());
    // assertEquals(projectPo.getEdges().size(), newPo.getEdges().size());
    // assertEquals(projectPo.getVertices().size(), newPo.getVertices().size());
    // assertEquals(projectPo.getSubgraphs().size(), newPo.getSubgraphs().size());
    // }
    // @Test
    // public void removeTest(){
    // projectPo=data.save(projectPo);
    // data.delete(projectPo);
    // assertEquals(data.findById(projectPo.getId()).orElse(null),null);
    // }

    // @Test
    // public void testFindByUserId() {
    // projectPo.setUserId(4L);
    // projectPo=data.save(projectPo);
    // var projectPo2 = new ProjectPo(null, 4L, "projC", null, null, null);
    // // Vertices
    // var v1 = new VertexPo(null, "v1", "dian1", "a1", 0f, 0f);
    // var v2 = new VertexPo(null, "v2", "dian2", "a2", 0f, 0f);
    // var vertices = new HashSet<VertexPo>();
    // vertices.add(v1);
    // vertices.add(v2);
    // projectPo2.setVertices(vertices);
    // // //Edges
    // var e1 = new EdgePo(null, v1, v2, 0.3d, "e1");
    // var edges = new HashSet<EdgePo>();
    // edges.add(e1);
    // projectPo2.setEdges(edges);
    // // Subgraphs
    // var connectiveDomain1 = new ConnectiveDomainPo(null, vertices, edges, "acd1",
    // "#CDBE70");
    // var connectiveDomains1 = new HashSet<ConnectiveDomainPo>();
    // connectiveDomains1.add(connectiveDomain1);
    // var defaultSubgraph = new SubgraphPo(null, 0d, "default subgraph",
    // connectiveDomains1);
    // var subgraphs = new HashSet<SubgraphPo>();
    // subgraphs.add(defaultSubgraph);
    // projectPo2.setSubgraphs(subgraphs);
    // projectPo2=data.save(projectPo2);

    // var resList=data.findByUserId(4L);
    // resList.sort((a,b)->(int)(a.getUserId()-b.getUserId()));
    // assertEquals(projectPo.getUserId(), resList.get(0).getUserId());
    // assertEquals(projectPo.getProjectName(), resList.get(0).getProjectName());
    // assertEquals(projectPo.getEdges().size(), resList.get(0).getEdges().size());
    // assertEquals(projectPo.getVertices().size(),
    // resList.get(0).getVertices().size());
    // assertEquals(projectPo.getSubgraphs().size(),
    // resList.get(0).getSubgraphs().size());
    // assertEquals(projectPo2.getUserId(), resList.get(1).getUserId());
    // assertEquals(projectPo2.getProjectName(), resList.get(1).getProjectName());
    // assertEquals(projectPo2.getEdges().size(), resList.get(1).getEdges().size());
    // assertEquals(projectPo2.getVertices().size(),
    // resList.get(1).getVertices().size());
    // assertEquals(projectPo2.getSubgraphs().size(),
    // resList.get(1).getSubgraphs().size());

    // }

}
