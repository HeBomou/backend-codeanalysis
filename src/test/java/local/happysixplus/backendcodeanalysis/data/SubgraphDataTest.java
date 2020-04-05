package local.happysixplus.backendcodeanalysis.data;

import local.happysixplus.backendcodeanalysis.po.ConnectiveDomainPo;
import local.happysixplus.backendcodeanalysis.po.SubgraphPo;

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
        data.deleteAll();
        po = new SubgraphPo();
        List<Long> vids1=new ArrayList<>();
        for(int i=0;i<3;i++) vids1.add(Long.valueOf(i));
        List<Long> vids2=new ArrayList<>();
        for(int i=0;i<5;i++) vids2.add(Long.valueOf(i+10));
        List<Long> vids3=new ArrayList<>();
        for(int i=0;i<7;i++) vids3.add(Long.valueOf(i+100));
        List<Long> eids1=new ArrayList<>();
        for(int i=0;i<3;i++) eids1.add(Long.valueOf(i+2));
        List<Long> eids2=new ArrayList<>();
        for(int i=0;i<5;i++) eids2.add(Long.valueOf(i+20));
        List<Long> eids3=new ArrayList<>();
        for(int i=0;i<7;i++) eids3.add(Long.valueOf(i+200));
        //var connectiveDomain1=new ConnectiveDomainPo(id, vertexIds, edgeIds)
        var connectiveDomains1 = new HashSet<ConnectiveDomainPo>();
        connectiveDomains1.add(new ConnectiveDomainPo(null,vids1,eids1));
        connectiveDomains1.add(new ConnectiveDomainPo(null,vids2,eids2));
        var connectiveDomains2 = new HashSet<ConnectiveDomainPo>();
        connectiveDomains2.add(new ConnectiveDomainPo(null,vids2,eids2));
        po = new SubgraphPo(null, 1L, 0d, connectiveDomains1);
        po1 = new SubgraphPo(null, 1L, 0.2d, connectiveDomains2);
    }
    @AfterEach
    void tearDown(){
        data.deleteAll();
    }
    @Test
    public void testInsert(){
        po=data.save(po);
        SubgraphPo resPo=data.findById(po.getId()).get();
        for(ConnectiveDomainPo cdp:resPo.getConnectiveDomains()){
            sort(cdp);
        }
        assertEquals(po.getId(),resPo.getId());
        assertEquals(po.getProjectId(),resPo.getProjectId());
        assertEquals(po.getThreshold(),resPo.getThreshold());
        assertEquals(po.getConnectiveDomains().size(),resPo.getConnectiveDomains().size());
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

        for(int i=0;i<pos.size();i++){
            for(ConnectiveDomainPo cdp:pos.get(i).getConnectiveDomains()){
                sort(cdp);
            }
        }

        assertEquals(po.getId(),pos.get(0).getId());
        assertEquals(po.getProjectId(),pos.get(0).getProjectId());
        assertEquals(po.getThreshold(),pos.get(0).getThreshold());
        assertEquals(po.getConnectiveDomains().size(),pos.get(0).getConnectiveDomains().size());

        assertEquals(po1.getId(),pos.get(1).getId());
        assertEquals(po1.getProjectId(),pos.get(1).getProjectId());
        assertEquals(po1.getThreshold(),pos.get(1).getThreshold());
        assertEquals(po1.getConnectiveDomains().size(),pos.get(1).getConnectiveDomains().size());
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
    void sort(ConnectiveDomainPo cdp){
        cdp.getEdgeIds().sort((a,b)->(int)(a-b));
        cdp.getVertexIds().sort((a,b)->(int)(a-b));
    }
}