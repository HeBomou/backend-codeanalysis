package local.happysixplus.backendcodeanalysis.data;

import local.happysixplus.backendcodeanalysis.po.ConnectiveDomainPo;
import local.happysixplus.backendcodeanalysis.po.ProjectPo;
import local.happysixplus.backendcodeanalysis.po.SubgraphPo;
import local.happysixplus.backendcodeanalysis.po.VertexPo;
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

import static org.assertj.core.util.IterableUtil.iterator;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
class ProjecthDataTest {
    @Autowired
    ProjectData data;
    int a=1;
    ProjectPo ppo=new ProjectPo();
    @BeforeEach
    public void setUp(){
        SubgraphPo po;
        po=new SubgraphPo();
        po.setName("NMSL");
        Set<ConnectiveDomainPo> cpos=new HashSet<>();
        for(int i=0;i<5;i++){
            ConnectiveDomainPo skt=new ConnectiveDomainPo();
            skt.setAnotation(i+":ruaruarua");
            List<Long> listIds=new ArrayList<>();
            for(int j=0;j<3;j++){
                listIds.add((long)(Math.pow(i*3,j+2)));
            }

            // skt.setEdgeIds(listIds);
            // skt.setVertexIds(listIds);
            cpos.add(skt);
        }
        po.setConnectiveDomains(cpos);
        po.setThreshold(0.4396);
        Set<SubgraphPo> ssp=new HashSet<>();
        ssp.add(po);
        //ppo.setSubgraphs(new HashSet<>());
        ppo.setEdges(new HashSet<>());
        ppo.setVertices(new HashSet<>());
        ppo.setSubgraphs(ssp);
        ppo.setUserId(1234L);
        ppo.setProjectName("motherfucker");

    }
    @Test
    public void insertData(){
        /*ppo=data.findById(1L).get();
        ppo.setProjectName("5345");
        Set<SubgraphPo> skt=ppo.getSubgraphs();
        Iterator<SubgraphPo> is=skt.iterator();
        if(is.hasNext()){
            if(is.next().getId()==1){
                is.remove();
            }
        }*/

        ppo=data.save(ppo);
        data.save(ppo);
    }
    @AfterEach
    public void tearDown(){

    }
}