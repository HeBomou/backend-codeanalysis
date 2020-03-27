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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProjectDataTest {
    @Autowired
    ProjectData data;
    int a = 1;
    ProjectPo ppo = new ProjectPo();

    @BeforeEach
    public void setUp() {
        SubgraphPo po;
        po = new SubgraphPo();
        po.setName("NMSL");
        Set<ConnectiveDomainPo> cpos = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            ConnectiveDomainPo skt = new ConnectiveDomainPo();
            skt.setAnotation(i + ":ruaruarua");
            List<Long> listIds = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                listIds.add((long) (Math.pow(i * 3, j + 2)));
            }

            // skt.setEdgeIds(listIds);
            // skt.setVertexIds(listIds);
            cpos.add(skt);
        }
        po.setConnectiveDomains(cpos);
        po.setThreshold(0.4396);
        Set<SubgraphPo> ssp = new HashSet<>();
        ssp.add(po);
        // ppo.setSubgraphs(new HashSet<>());
        ppo.setEdges(new HashSet<>());
        ppo.setVertices(new HashSet<>());
        ppo.setSubgraphs(ssp);
        ppo.setUserId(1234L);
        ppo.setProjectName("motherfucker");

    }

    @Test
    public void insertData() {
        /*
         * ppo=data.findById(1L).get(); ppo.setProjectName("5345"); Set<SubgraphPo>
         * skt=ppo.getSubgraphs(); Iterator<SubgraphPo> is=skt.iterator();
         * if(is.hasNext()){ if(is.next().getId()==1){ is.remove(); } }
         */

        ppo = data.save(ppo);
        data.save(ppo);
    }

    @Test
    public void insertWholeData() {
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
        var connectiveDomain1 = new ConnectiveDomainPo(null, vertices, edges, "anotation cd1");
        var cd2vSet = new HashSet<VertexPo>();
        cd2vSet.add(v1);
        cd2vSet.add(v2);
        var cd2eSet = new HashSet<EdgePo>();
        cd2eSet.add(e1);
        var connectiveDomain2 = new ConnectiveDomainPo(null, cd2vSet, cd2eSet, "anotation cd2");
        var cd3vSet = new HashSet<VertexPo>();
        cd3vSet.add(v3);
        cd3vSet.add(v4);
        var cd3eSet = new HashSet<EdgePo>();
        cd3eSet.add(e3);
        var connectiveDomain3 = new ConnectiveDomainPo(null, cd3vSet, cd3eSet, "anotation cd3");
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
    }

    @AfterEach
    public void tearDown() {

    }
}