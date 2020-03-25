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
        var v1 = new VertexPo(null, "cnmd1", "nmbd yuanma 1", "anotation", -1f, -1f);
        var v2 = new VertexPo(null, "cnmd2", "nmbd yuanma 2", "anotation", -1f, -1f);
        var v3 = new VertexPo(null, "cnmd3", "nmbd yuanma 3", "anotation", -1f, -1f);
        var vertices = new HashSet<VertexPo>();
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);
        projectPo.setVertices(vertices);
        // Edges
        var e1 = new EdgePo(null, v1, v2, 0.1d, "edge 1");
        var e2 = new EdgePo(null, v2, v3, 0.2d, "edge 2");
        var edges = new HashSet<EdgePo>();
        edges.add(e1);
        edges.add(e2);
        projectPo.setEdges(edges);
        // Subgraphs
        var connectiveDomain1 = new ConnectiveDomainPo(null, vertices, edges, "anotation");
        var connectiveDomains1 = new HashSet<ConnectiveDomainPo>();
        connectiveDomains1.add(connectiveDomain1);
        var subgraph = new SubgraphPo(null, 0d, "default subgraph", connectiveDomains1);
        var subgraphs = new HashSet<SubgraphPo>();
        subgraphs.add(subgraph);
        projectPo.setSubgraphs(subgraphs);
        data.save(projectPo);
    }

    @AfterEach
    public void tearDown() {

    }
}