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
    // @BeforeEach
    // void init() {
    //     projectData.deleteAll();
    //     data.deleteAll();
    //     var connectiveDomain1 = new ConnectiveDomainPo(null,new HashSet<VertexPo>(), new HashSet<EdgePo>(), "anotation cd1", "#CDBE70");
    //     var connectiveDomains1=new HashSet<ConnectiveDomainPo>();
    //     connectiveDomains1.add(connectiveDomain1);
    //     po = new SubgraphPo(null, 0d, "default subgraph", connectiveDomains1);
    // }

    // @AfterEach
    // void tearDown() {
    //     projectData.deleteAll();
    //     data.deleteAll();
    // }

    // @Test
    // public void insertData() {
    //     //po.setConnectiveDomains(null);
    //     po=data.save(po);
    //     SubgraphPo newPo=data.findById(po.getId()).orElse(null);
    //     assertEquals(po.getName(), newPo.getName());
    //     assertEquals(po.getThreshold(),newPo.getThreshold());
    //     assertEquals(po.getConnectiveDomains().size(),newPo.getConnectiveDomains().size());
    //     assertEquals(po.getId(),newPo.getId());
    // }

    // @Test
    // public void updateData(){
    //     po=data.save(po);
    //     po.setName("SKTFaker");
    //     po.setThreshold(0.6324d);
    //     po=data.save(po);
    //     var res=data.findById(po.getId()).orElse(null);
    //     assertEquals(res.getId(),po.getId());
    //     assertEquals(res.getThreshold(),po.getThreshold());
    //     assertEquals(res.getName(),po.getName());
    // }

    // @Test
    // public void removeData(){
    //     po=data.save(po);
    //     data.delete(po);
    //     assertEquals(Optional.empty(),data.findById(po.getId()));
    // }
}