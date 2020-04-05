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

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.*;

@SpringBootTest
class SubgraphDataTest {

    @Autowired
    SubgraphData data;

    SubgraphPo po;
    SubgraphPo po1;
    @BeforeEach
    void setUp() {
        // data.deleteAll();
        // po = new SubgraphPo();
        // var v1 = new VertexPo(null, "cnmd1", "nmbd yuanma 1");
        // var v2 = new VertexPo(null, "cnmd2", "nmbd yuanma 2");
        // var v3 = new VertexPo(null, "cnmd3", "nmbd yuanma 3");
        // var v4 = new VertexPo(null, "cnmd4", "nmbd yuanma 4");
        // var vertices = new HashSet<VertexPo>();
        // vertices.add(v1);
        // vertices.add(v2);
        // vertices.add(v3);
        // vertices.add(v4);
        // var e1 = new EdgePo(null, v1, v2, 0.3d);
        // var e2 = new EdgePo(null, v2, v3, 0.1d);
        // var e3 = new EdgePo(null, v3, v4, 0.3d);
        // var edges = new HashSet<EdgePo>();
        // edges.add(e1);
        // edges.add(e2);
        // edges.add(e3);
        // var cd2vSet = new HashSet<VertexPo>();
        // cd2vSet.add(v1);
        // cd2vSet.add(v2);
        // var cd2eSet = new HashSet<EdgePo>();
        // cd2eSet.add(e1);
        // var cd3vSet = new HashSet<VertexPo>();
        // cd3vSet.add(v3);
        // cd3vSet.add(v4);
        // var cd3eSet = new HashSet<EdgePo>();
        // cd3eSet.add(e3);
        // var connectiveDomains1 = new HashSet<ConnectiveDomainPo>();
        // var connectiveDomains2 = new HashSet<ConnectiveDomainPo>();
        // po = new SubgraphPo(null, 1L, 0d, connectiveDomains1);
        // po1 = new SubgraphPo(null, 1L, 0.2d, connectiveDomains2);
    }
    @AfterEach
    void tearDown(){
        data.deleteAll();
    }
    @Test
    public void testInsert(){
        po=data.save(po);
        SubgraphPo resPo=data.findById(po.getId()).get();
        assertEquals(po.getId(),resPo.getId());
        assertEquals(po.getProjectId(),resPo.getProjectId());
        assertEquals(po.getThreshold(),resPo.getThreshold());
        assertEquals(po.getConnectiveDomains(),resPo.getConnectiveDomains());
    }
    @Test
    public void testRemove(){
        po=data.save(po);
        data.deleteById(po.getId());
        var res=data.findById(po.getId()).orElse(null);
        assertEquals(res,null);
    }
    @Test
    public void testFindByProjectId() {
        po=data.save(po);
        po1=data.save(po1);
        List<SubgraphPo> pos=data.findByProjectId(1L);
        pos.sort((a,b)->(int)(a.getId()-b.getId()));

        assertEquals(po.getId(),pos.get(0).getId());
        assertEquals(po.getProjectId(),pos.get(0).getProjectId());
        assertEquals(po.getThreshold(),pos.get(0).getThreshold());
        assertEquals(po.getConnectiveDomains(),pos.get(0).getConnectiveDomains());

        assertEquals(po1.getId(),pos.get(1).getId());
        assertEquals(po1.getProjectId(),pos.get(1).getProjectId());
        assertEquals(po1.getThreshold(),pos.get(1).getThreshold());
        assertEquals(po1.getConnectiveDomains(),pos.get(1).getConnectiveDomains());
    }
    @Test
    public void testDeleteByProjectId() {
        po=data.save(po);
        po1=data.save(po1);
        data.deleteByProjectId(1L);
        List<SubgraphPo> pos=data.findByProjectId(1L);
        assertEquals(pos, new ArrayList<SubgraphPo>());
    }
    @Test
    public void testCountByProjectId() {
        po=data.save(po);
        po1=data.save(po1);
        int count=data.countByProjectId(1L);
        assertEquals(count,2);
    }

}