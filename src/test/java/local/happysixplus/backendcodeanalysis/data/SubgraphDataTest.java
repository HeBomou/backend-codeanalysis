package local.happysixplus.backendcodeanalysis.data;

import local.happysixplus.backendcodeanalysis.po.ConnectiveDomainPo;
import local.happysixplus.backendcodeanalysis.po.SubgraphPo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class SubgraphDataTest {

    @Autowired
    SubgraphData data;

    @Test
    public void insertData() {
        data.deleteAll();
        SubgraphPo po = new SubgraphPo();
        po.setId(1L);
        po.setName("NMSL");
        Set<ConnectiveDomainPo> cpos = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            ConnectiveDomainPo skt = new ConnectiveDomainPo();
            skt.setId(Long.valueOf(i));
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
        // po.setProjectId(12345L);
        po.setThreshold(0.4396);

        int sz = data.findAll().size();
        data.save(po);
        assertEquals(sz + 1, data.findAll().size());
    }
}