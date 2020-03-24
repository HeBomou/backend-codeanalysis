package local.happysixplus.backendcodeanalysis.data;

import local.happysixplus.backendcodeanalysis.po.ConnectiveDomainPo;
import local.happysixplus.backendcodeanalysis.po.SubgraphPo;
import org.apiguardian.api.API;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
class SubgraphDataTest {
    @Autowired
    SubgraphData data;
    int a=1;
    SubgraphPo po;
    @BeforeEach
    public void setUp(){
        po=new SubgraphPo();
        po.setId(1L);
        po.setName("NMSL");
        Set<ConnectiveDomainPo> cpos=new HashSet<>();
        for(int i=0;i<5;i++){
            ConnectiveDomainPo skt=new ConnectiveDomainPo();
            skt.setId(Long.valueOf(i));
            skt.setAnotation(i+":ruaruarua");
            List<Long> listIds=new ArrayList<>();
            for(int j=0;j<3;j++){
                listIds.add((long)(Math.pow(i*3,j+2)));
            }
            skt.setEdgeIds(listIds);
            skt.setVertexIds(listIds);
            skt.setSubgraphId(1234L);
            cpos.add(skt);
        }
        po.setConnectiveDomains(cpos);
        po.setProjectId(12345L);
        po.setThreshold(0.4396);

    }
    @Test
    public void insertData(){
        data.save(po);
        assertEquals(1,data.findAll().size());
    }
    @Test
    public void updateData(){
        //Set<ConnectiveDomainPo> cdpo=po.getConnectiveDomains();
    }
    @AfterEach
    public void tearDown(){

    }
}