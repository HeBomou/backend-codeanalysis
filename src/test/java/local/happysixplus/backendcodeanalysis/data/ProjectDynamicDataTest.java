package local.happysixplus.backendcodeanalysis.data;

import local.happysixplus.backendcodeanalysis.po.ProjectDynamicPo;
import lombok.var;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

@SpringBootTest
class ProjectDynamicDataTest {

    @Autowired
    ProjectDynamicData data;

    ProjectDynamicPo projectDynamicPo = new ProjectDynamicPo();

    @BeforeEach
    void init() {
        data.deleteAll();
        projectDynamicPo = new ProjectDynamicPo(123L, 1L, "SKTelecomT1Faker");
    }

    @AfterEach
    void tearDown() {
        data.deleteAll();
    }

    @Test
    public void testInsert(){
        projectDynamicPo=data.save(projectDynamicPo);
        ProjectDynamicPo resPo=data.findById(projectDynamicPo.getId()).get();
        assertEquals(resPo.getId(),projectDynamicPo.getId());
        assertEquals(resPo.getUserId(),projectDynamicPo.getUserId());
        assertEquals(resPo.getProjectName(),projectDynamicPo.getProjectName());
    }
    @Test
    public void testUpdate(){
        projectDynamicPo=data.save(projectDynamicPo);
        projectDynamicPo=new ProjectDynamicPo(projectDynamicPo.getId(),4396L,"Mother fucker"); 
        projectDynamicPo=data.save(projectDynamicPo);
        ProjectDynamicPo resPo=data.findById(projectDynamicPo.getId()).get();
        assertEquals(resPo.getId(),projectDynamicPo.getId());
        assertEquals(resPo.getUserId(),projectDynamicPo.getUserId());
        assertEquals(resPo.getProjectName(),projectDynamicPo.getProjectName());
    }
    @Test
    public void testRemove(){
        projectDynamicPo=data.save(projectDynamicPo);
        data.deleteById(projectDynamicPo.getId());
        var res=data.findById(projectDynamicPo.getId()).orElse(null);
        assertEquals(res, null);
    }
    @Test
    public void testFindByUserId(){
        projectDynamicPo=data.save(projectDynamicPo);
        ProjectDynamicPo projectDynamicPo1=data.save(new ProjectDynamicPo(124L,1L,"SKTKhan"));
        ProjectDynamicPo projectDynamicPo2=data.save(new ProjectDynamicPo(125L,1L,"SKTCLid"));
        List<ProjectDynamicPo> list=data.findByUserId(1L);
        list.sort((a,b)->(int)(a.getId()-b.getId()));
        assertEquals(list.get(0).getId(),projectDynamicPo.getId());
        assertEquals(list.get(0).getUserId(),projectDynamicPo.getUserId());
        assertEquals(list.get(0).getProjectName(),projectDynamicPo.getProjectName());
        assertEquals(list.get(1).getId(),projectDynamicPo1.getId());
        assertEquals(list.get(1).getUserId(),projectDynamicPo1.getUserId());
        assertEquals(list.get(1).getProjectName(),projectDynamicPo1.getProjectName());
        assertEquals(list.get(2).getId(),projectDynamicPo2.getId());
        assertEquals(list.get(2).getUserId(),projectDynamicPo2.getUserId());
        assertEquals(list.get(2).getProjectName(),projectDynamicPo2.getProjectName());
    }
    
}
