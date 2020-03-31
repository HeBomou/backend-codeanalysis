package local.happysixplus.backendcodeanalysis.data;

import local.happysixplus.backendcodeanalysis.po.ConnectiveDomainPo;
import local.happysixplus.backendcodeanalysis.po.EdgePo;
import local.happysixplus.backendcodeanalysis.po.SubgraphPo;

import local.happysixplus.backendcodeanalysis.po.VertexPo;
import lombok.var;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SubgraphDataTest {

    @Autowired
    SubgraphData data;

    @Autowired
    ProjectData projectData;

    SubgraphPo po;
    @BeforeEach
    void init() {
        projectData.deleteAll();
        data.deleteAll();
        var v1 = new VertexPo(null, "sktfaker1", "nmbd yuanma 1", "anotation", 0f, 0f);
        var v2 = new VertexPo(null, "sktfaker2", "nmbd yuanma 2", "anotation", 0f, 0f);
        var v5 = new VertexPo(null, "cnmd1", "nmbd yuanma 1", "anotation", 0f, 0f);
        var v6 = new VertexPo(null, "cnmd2", "nmbd yuanma 2", "anotation", 0f, 0f);
        var v3 = new VertexPo(null, "cnmd3", "nmbd yuanma 3", "anotation", 0f, 0f);
        var v4 = new VertexPo(null, "cnmd4", "nmbd yuanma 4", "anotation", 0f, 0f);
        var vertices = new HashSet<VertexPo>();
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);
        vertices.add(v4);
        vertices.add(v5);
        vertices.add(v6);
        var e1 = new EdgePo(null, v1, v2, 0.3d, "edge 1");
        var e2 = new EdgePo(null, v2, v3, 0.1d, "edge 2");
        var e3 = new EdgePo(null, v3, v4, 0.3d, "edge 3");
        var e4 = new EdgePo(null,v5,v6,0.2d,"faker1");
        var e5 = new EdgePo(null,v3,v6,0.2d,"faker2");
        var e6 = new EdgePo(null,v1,v6,0.2d,"faker3");
        var edges = new HashSet<EdgePo>();
        edges.add(e1);
        edges.add(e2);
        edges.add(e3);
        edges.add(e4);
        edges.add(e5);
        edges.add(e6);
        var connectiveDomain1 = new ConnectiveDomainPo(null, vertices, edges, "anotation cd1", "#CDBE70");
        var connectiveDomains1=new HashSet<ConnectiveDomainPo>();
        connectiveDomains1.add(connectiveDomain1);
        po = new SubgraphPo(null, 0d, "default subgraph", connectiveDomains1);
    }

    @AfterEach
    void tearDown() {
        projectData.deleteAll();
        data.deleteAll();
    }

    @Test
    public void insertData() {
        //po.setConnectiveDomains(null);
        data.save(po);
        SubgraphPo newPo=data.findById(po.getId()).orElse(null);
        assertEquals(po.getName(), newPo.getName());
        assertEquals(po.getThreshold(),newPo.getThreshold());
        assertEquals(po.getConnectiveDomains().size(),newPo.getConnectiveDomains().size());
        assertEquals(po.getId(),newPo.getId());
    }

    @Test
    public void updateData(){
        data.save(po);
        po.setName("SKTFaker");
        po.setThreshold(0.6324d);




    }
}