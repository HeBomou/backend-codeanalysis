package local.happysixplus.backendcodeanalysis.data;

import local.happysixplus.backendcodeanalysis.po.EdgeDynamicPo;
import lombok.var;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

@SpringBootTest
class EdgeDynamicDataTest {

    @Autowired
    EdgeDynamicData data;

    EdgeDynamicPo edgeDynamicPo = new EdgeDynamicPo();

    @BeforeEach
    void init() {
        data.deleteAll();
        edgeDynamicPo = new EdgeDynamicPo(23456L,1L,"SKTFaker");
    }

    @AfterEach
    void tearDown() {
        data.deleteAll();
    }

    @Test
    public void testInsert(){
        edgeDynamicPo=data.save(edgeDynamicPo);
        EdgeDynamicPo resPo=data.findById(edgeDynamicPo.getId()).get();
        assertEquals(resPo.getId(),edgeDynamicPo.getId());
        assertEquals(resPo.getProjectId(),edgeDynamicPo.getProjectId());
        assertEquals(resPo.getAnotation(),edgeDynamicPo.getAnotation());
    }
    @Test
    public void testUpdate(){
        edgeDynamicPo=data.save(edgeDynamicPo);
        edgeDynamicPo=new EdgeDynamicPo(edgeDynamicPo.getId(),4396L,"Mother fucker"); 
        edgeDynamicPo=data.save(edgeDynamicPo);
        EdgeDynamicPo resPo=data.findById(edgeDynamicPo.getId()).get();
        assertEquals(resPo.getId(),edgeDynamicPo.getId());
        assertEquals(resPo.getProjectId(),edgeDynamicPo.getProjectId());
        assertEquals(resPo.getAnotation(),edgeDynamicPo.getAnotation());

    }
    @Test
    public void testRemove(){
        edgeDynamicPo=data.save(edgeDynamicPo);
        data.deleteById(edgeDynamicPo.getId());
        var res=data.findById(edgeDynamicPo.getId()).orElse(null);
        assertEquals(res, null);
    }
    @Test
    public void testFindByProjectId(){
        edgeDynamicPo=data.save(edgeDynamicPo);
        EdgeDynamicPo edgeDynamicPo1=data.save(new EdgeDynamicPo(23457L,1L,"SKTKhan"));
        EdgeDynamicPo edgeDynamicPo2=data.save(new EdgeDynamicPo(23458L,1L,"SKTCLid"));
        List<EdgeDynamicPo> list=data.findByProjectId(1L);
        list.sort((a,b)->(int)(a.getId()-b.getId()));
        assertEquals(list.get(0).getId(),edgeDynamicPo.getId());
        assertEquals(list.get(0).getProjectId(),edgeDynamicPo.getProjectId());
        assertEquals(list.get(0).getAnotation(),edgeDynamicPo.getAnotation());

        assertEquals(list.get(1).getId(),edgeDynamicPo1.getId());
        assertEquals(list.get(1).getProjectId(),edgeDynamicPo1.getProjectId());
        assertEquals(list.get(1).getAnotation(),edgeDynamicPo1.getAnotation());
        
        assertEquals(list.get(2).getId(),edgeDynamicPo2.getId());
        assertEquals(list.get(2).getProjectId(),edgeDynamicPo2.getProjectId());
        assertEquals(list.get(2).getAnotation(),edgeDynamicPo2.getAnotation());
    }

    @Test
    public void testDeleteByProjectId(){
        edgeDynamicPo=data.save(edgeDynamicPo);
        data.save(new EdgeDynamicPo(23458L,1L,"SKTKhan"));
        data.save(new EdgeDynamicPo(23459L,1L,"SKTCLid"));
        data.deleteByProjectId(edgeDynamicPo.getId());
        List<EdgeDynamicPo> list=data.findByProjectId(edgeDynamicPo.getId());
        assertEquals(list, new ArrayList<EdgeDynamicPo>());
    }

    @Test
    public void testCountByProjectId() {
        edgeDynamicPo=data.save(edgeDynamicPo);
        data.save(new EdgeDynamicPo(23458L,1L,"SKTKhan"));
        data.save(new EdgeDynamicPo(23459L,1L,"SKTCLid"));
        int count=data.countByProjectId(1L);
        assertEquals(3,count);
    }
    
}
