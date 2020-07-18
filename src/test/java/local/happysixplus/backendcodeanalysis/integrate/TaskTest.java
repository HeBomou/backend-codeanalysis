package local.happysixplus.backendcodeanalysis.integrate;


import com.alibaba.fastjson.JSONObject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



import local.happysixplus.backendcodeanalysis.vo.GroupTaskVo;
import local.happysixplus.backendcodeanalysis.vo.GroupVo;
import local.happysixplus.backendcodeanalysis.data.GroupData;
import local.happysixplus.backendcodeanalysis.data.GroupTaskData;
import local.happysixplus.backendcodeanalysis.data.GroupUserRelData;
import local.happysixplus.backendcodeanalysis.data.TaskUserRelData;
import local.happysixplus.backendcodeanalysis.data.GroupNoticeData;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import lombok.var;

@SpringBootTest(webEnvironment =SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class TaskTest {

    @Autowired
    MockMvc mockMvc;

    

    @Autowired
    GroupData gData;

    @Autowired
    GroupUserRelData gRData;

    @Autowired
    GroupNoticeData nData;

    @Autowired
    TaskUserRelData tRData;
    
    @Autowired
    GroupTaskData tData;

    @BeforeEach
    public void init(){
        gData.deleteAll();
        gRData.deleteAll();
        nData.deleteAll();
        tRData.deleteAll();
        tData.deleteAll();
    }
    @Test
    public void testGroup() throws Exception{
        var groupVo=new GroupVo(null,3L,"SKTelecom","xxx");
        mockMvc.perform(MockMvcRequestBuilders.post("/group").contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(groupVo))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        
        MvcResult resGet1=mockMvc.perform(MockMvcRequestBuilders.get("/group/getgroup/{userId}",3L)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        var list1=JSONObject.parseArray(resGet1.getResponse().getContentAsString());
        assertEquals(list1.size(),1);
        var resvo1=JSONObject.parseObject(list1.get(0).toString(),GroupVo.class);
        var groupId=resvo1.getId();

        var taskVo=new GroupTaskVo(null,groupId,"task1","???","yesterday",0);
        mockMvc.perform(MockMvcRequestBuilders.post("/task").contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(taskVo))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        MvcResult getAllTask=mockMvc.perform(MockMvcRequestBuilders.get("/task/{groupId}",groupId)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        var list2=JSONObject.parseArray(getAllTask.getResponse().getContentAsString());
        assertEquals(list2.size(),1);
        var taskVoRes1=JSONObject.parseObject(list2.get(0).toString(),GroupTaskVo.class);
        taskVo.setId(taskVoRes1.getId());
        assertEquals(taskVo,taskVoRes1);
        
        mockMvc.perform(MockMvcRequestBuilders.put("/task/{groupId}/{taskId}/assign/{userId}",groupId,taskVo.getId(),3L)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        MvcResult getAllTask1=mockMvc.perform(MockMvcRequestBuilders.get("/task/{groupId}/user/{userId}",groupId,3L)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        var list3=JSONObject.parseArray(getAllTask1.getResponse().getContentAsString());
        assertEquals(list3.size(),1);
        var taskVoRes2=JSONObject.parseObject(list2.get(0).toString(),GroupTaskVo.class);
        assertEquals(taskVo,taskVoRes2);
    
        taskVo.setName("task2"); taskVo.setInfo("1234"); taskVo.setDeadline("tomorrow"); taskVo.setIsFinished(4396);
        mockMvc.perform(MockMvcRequestBuilders.put("/task").contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(taskVo))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        getAllTask=mockMvc.perform(MockMvcRequestBuilders.get("/task/{groupId}",groupId)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        list2=JSONObject.parseArray(getAllTask.getResponse().getContentAsString());
        assertEquals(list2.size(),1);
        taskVoRes1=JSONObject.parseObject(list2.get(0).toString(),GroupTaskVo.class);
        assertEquals(taskVo,taskVoRes1);

        Long uid1=4L; Long uid2=5L; Long uid3=6L;
        var uids=Arrays.asList(3L,uid1,uid2,uid3);
        mockMvc.perform(MockMvcRequestBuilders.post("/task/{groupId}/{taskId}",groupId,taskVo.getId()).contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(uids)))
            .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        MvcResult getExecuter=mockMvc.perform(MockMvcRequestBuilders.get("/task/executor/{taskId}",taskVo.getId())).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        var list4=JSONObject.parseArray(getExecuter.getResponse().getContentAsString());
        assertEquals(list4.size(),4);
        List<Long> u=new ArrayList<>();
        for(int i=0;i<list4.size();i++){
            Long tmp=JSONObject.parseObject(list4.get(i).toString(),Long.class);
            u.add(tmp);
        }
        u.sort((a,b)->(int)(a-b));
        assertEquals(u.get(0),3L);  assertEquals(u.get(1),4L);  assertEquals(u.get(2),5L);  assertEquals(u.get(3),6L);
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/task/{id}",taskVo.getId())).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        getAllTask=mockMvc.perform(MockMvcRequestBuilders.get("/task/{groupId}",groupId)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        list2=JSONObject.parseArray(getAllTask.getResponse().getContentAsString());
        assertEquals(list2.size(),0);
    }
   

}