package local.happysixplus.backendcodeanalysis.data;

import local.happysixplus.backendcodeanalysis.po.VertexDynamicPo;
import lombok.var;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

@SpringBootTest
class VertexDynamicDataTest {

    @Autowired
    VertexDynamicData data;

    VertexDynamicPo vertexDynamicPo = new VertexDynamicPo();

    @BeforeEach
    void init() {
        data.deleteAll();
        vertexDynamicPo = new VertexDynamicPo(null,1L,"SKTFaker",0f,0f);
    }

    @AfterEach
    void tearDown() {
        data.deleteAll();
    }

    @Test
    public void testInsert(){
        vertexDynamicPo=data.save(vertexDynamicPo);
        VertexDynamicPo resPo=data.findById(vertexDynamicPo.getId()).get();
        assertEquals(resPo.getId(),vertexDynamicPo.getId());
        assertEquals(resPo.getProjectId(),vertexDynamicPo.getProjectId());
        assertEquals(resPo.getAnotation(),vertexDynamicPo.getAnotation());
        assertEquals(resPo.getX(),vertexDynamicPo.getX());
        assertEquals(resPo.getY(),vertexDynamicPo.getY());
    }
    @Test
    public void testUpdate(){
        vertexDynamicPo=data.save(vertexDynamicPo);
        vertexDynamicPo=new VertexDynamicPo(vertexDynamicPo.getId(),4396L,"Mother fucker",0.1f,0.1f); 
        vertexDynamicPo=data.save(vertexDynamicPo);
        VertexDynamicPo resPo=data.findById(vertexDynamicPo.getId()).get();
        assertEquals(resPo.getId(),vertexDynamicPo.getId());
        assertEquals(resPo.getProjectId(),vertexDynamicPo.getProjectId());
        assertEquals(resPo.getAnotation(),vertexDynamicPo.getAnotation());
        assertEquals(resPo.getX(),vertexDynamicPo.getX());
        assertEquals(resPo.getY(),vertexDynamicPo.getY());

    }
    @Test
    public void testRemove(){
        vertexDynamicPo=data.save(vertexDynamicPo);
        data.deleteById(vertexDynamicPo.getId());
        var res=data.findById(vertexDynamicPo.getId()).orElse(null);
        assertEquals(res, null);
    }
    @Test
    public void testFindByProjectId(){
        vertexDynamicPo=data.save(vertexDynamicPo);
        VertexDynamicPo vertexDynamicPo1=data.save(new VertexDynamicPo(null,1L,"SKTKhan",0.2f,0.2f));
        VertexDynamicPo vertexDynamicPo2=data.save(new VertexDynamicPo(null,1L,"SKTCLid",0.3f,0.3f));
        List<VertexDynamicPo> list=data.findByProjectId(1L);
        list.sort((a,b)->(int)(a.getId()-b.getId()));
        assertEquals(list.get(0).getId(),vertexDynamicPo.getId());
        assertEquals(list.get(0).getProjectId(),vertexDynamicPo.getProjectId());
        assertEquals(list.get(0).getAnotation(),vertexDynamicPo.getAnotation());
        assertEquals(list.get(0).getX(),vertexDynamicPo.getX());
        assertEquals(list.get(0).getY(),vertexDynamicPo.getY());

        assertEquals(list.get(1).getId(),vertexDynamicPo1.getId());
        assertEquals(list.get(1).getProjectId(),vertexDynamicPo1.getProjectId());
        assertEquals(list.get(1).getAnotation(),vertexDynamicPo1.getAnotation());
        assertEquals(list.get(1).getX(),vertexDynamicPo1.getX());
        assertEquals(list.get(1).getY(),vertexDynamicPo1.getY());
        
        assertEquals(list.get(2).getId(),vertexDynamicPo2.getId());
        assertEquals(list.get(2).getProjectId(),vertexDynamicPo2.getProjectId());
        assertEquals(list.get(2).getAnotation(),vertexDynamicPo2.getAnotation());
        assertEquals(list.get(2).getX(),vertexDynamicPo2.getX());
        assertEquals(list.get(2).getY(),vertexDynamicPo2.getY());
    }

    @Test
    public void testDeleteByProjectId(){
        vertexDynamicPo=data.save(vertexDynamicPo);
        data.save(new VertexDynamicPo(null,1L,"SKTKhan",0.2f,0.2f));
        data.save(new VertexDynamicPo(null,1L,"SKTCLid",0.4f,0.3f));
        data.deleteByProjectId(vertexDynamicPo.getId());
        List<VertexDynamicPo> list=data.findByProjectId(vertexDynamicPo.getId());
        assertEquals(list, new ArrayList<VertexDynamicPo>());
    }
    
}
