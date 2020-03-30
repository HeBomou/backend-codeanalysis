package local.happysixplus.backendcodeanalysis.integrate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import local.happysixplus.backendcodeanalysis.data.ProjectData;
import local.happysixplus.backendcodeanalysis.data.SubgraphData;
import local.happysixplus.backendcodeanalysis.po.ProjectPo;
import local.happysixplus.backendcodeanalysis.service.impl.ProjectServiceImpl;
import local.happysixplus.backendcodeanalysis.vo.ProjectAllVo;
import local.happysixplus.backendcodeanalysis.vo.ProjectDynamicVo;
import local.happysixplus.backendcodeanalysis.vo.SubgraphAllVo;
import lombok.var;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProjectTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProjectData projectData;

    @Autowired
    SubgraphData subgraphData;
    @BeforeEach
    public void setUp(){
    }
    @Test
    public void Test1() throws Exception {
        MvcResult resAdd = mockMvc
                .perform(MockMvcRequestBuilders.post("/project").param("projectName", "Linux")
                        .param("url", "https://gitee.com/forsakenspirit/Linux").param("userId", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();

        MvcResult resGet = mockMvc.perform(MockMvcRequestBuilders.get("/project").param("userId", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(resAdd);
        System.out.println(resGet);
    }

    @Test
    public void Test2() throws Exception {
        MvcResult resGet = mockMvc.perform(MockMvcRequestBuilders.get("/project").param("userId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println(resGet);
    }

    @Test
    public void Test3() throws Exception{
        MvcResult resGet1 = mockMvc.perform(MockMvcRequestBuilders.get("/project").param("userId", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        var v=resGet1.getResponse().getContentAsString();
        var vo= (ProjectAllVo)JSONObject.parseObject(JSONObject.parseObject(v,List.class).get(0).toString(),ProjectAllVo.class);
       // var vo=(ProjectAllVo)(JSONObject.parseObject(resGet1.getResponse().getContentAsString(), List.class).get(0));
        long projectId=vo.getId();
        vo.getDynamicVo().setProjectName("SKTelecomT1Faker");
        MvcResult resUpdate = mockMvc.perform(
                MockMvcRequestBuilders.put("/project/{projectId}",projectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONObject.toJSONString(vo.getDynamicVo())))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        MvcResult resGet2 = mockMvc.perform(MockMvcRequestBuilders.get("/project").param("userId", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        //System.out.println(resGet1);
        System.out.print(resUpdate);
        //System.out.println(resGet2);
    }


    @Test
    public void Test4() throws Exception{
        MvcResult resRemove = mockMvc.perform(MockMvcRequestBuilders.delete("/project/{id}",6))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.print(resRemove);
    }
    @Test
    public void Test5() throws Exception{
        MvcResult resAdd = mockMvc
                .perform(MockMvcRequestBuilders.post("/project").param("projectName", "Test5")
                        .param("url", "https://gitee.com/forsakenspirit/Linux").param("userId", "5"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        var v=resAdd.getResponse().getContentAsString();
        var vo= (ProjectAllVo)JSONObject.parseObject(v,ProjectAllVo.class);
        long projectId=vo.getId();
        MvcResult resAddSubgraph= mockMvc.perform(
                MockMvcRequestBuilders.post("/project/{projectId}/subgraph",projectId).param("threshold","0.5"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        var subgraphVo=JSONObject.parseObject(resAddSubgraph.getResponse().getContentAsString(), SubgraphAllVo.class);

        MvcResult resGetSubgraph= mockMvc.perform(
                MockMvcRequestBuilders.get("/project/{projectId}/subgraph",projectId))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        List<SubgraphAllVo> subgraphAllVos=new ArrayList<>();
        var list=JSONObject.parseObject(resGetSubgraph.getResponse().getContentAsString(),List.class);
        for(int i=0;i<list.size();i++){
            subgraphAllVos.add(JSONObject.parseObject(list.get(i).toString(),SubgraphAllVo.class));
        }
        vo.getDynamicVo().getSubgraphs().add(subgraphVo.getDynamicVo());
        MvcResult resRemoveSubgraph=mockMvc.perform(
                MockMvcRequestBuilders.delete("/project/{projectId}/subgraph/{id}",projectId,subgraphVo.getId()))
                        .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
    }

    @Test
    public void Test6() throws Exception{
         MvcResult resAdd = mockMvc
                .perform(MockMvcRequestBuilders.post("/project").param("projectName", "HappySixDemo")
                        .param("url", "https://gitee.com/forsakenspirit/Demo").param("userId", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
    }
}
