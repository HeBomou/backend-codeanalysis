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

import local.happysixplus.backendcodeanalysis.vo.GroupMemberVo;
import local.happysixplus.backendcodeanalysis.vo.GroupNoticeVo;
import local.happysixplus.backendcodeanalysis.vo.GroupVo;
import local.happysixplus.backendcodeanalysis.vo.UserVo;
import local.happysixplus.backendcodeanalysis.vo.SessionVo;
import local.happysixplus.backendcodeanalysis.data.UserData;
import local.happysixplus.backendcodeanalysis.data.GroupData;
import local.happysixplus.backendcodeanalysis.data.GroupUserRelData;
import local.happysixplus.backendcodeanalysis.data.GroupNoticeData;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import lombok.var;

@SpringBootTest(webEnvironment =SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class GroupTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserData uData;

    @Autowired
    GroupData gData;

    @Autowired
    GroupUserRelData gRData;

    @Autowired
    GroupNoticeData nData;
    
    @BeforeEach
    public void init(){
        uData.deleteAll();
        gData.deleteAll();
        gRData.deleteAll();
        nData.deleteAll();
    }
    @Test
    public void testGroup() throws Exception{
        var userVo = new UserVo(null, "SKTFaker", "123123");
        mockMvc.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(userVo))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        
            var sessionVo=new SessionVo("SKTFaker","123123");
        MvcResult sResult=mockMvc.perform(MockMvcRequestBuilders.post("/session").contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(sessionVo))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Long userId= JSONObject.parseObject(sResult.getResponse().getContentAsString(), Long.class);

        var groupVo=new GroupVo(null,userId,"SKTelecom","xxx");
        mockMvc.perform(MockMvcRequestBuilders.post("/group").contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(groupVo))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        
        MvcResult resGet1=mockMvc.perform(MockMvcRequestBuilders.get("/group/getgroup/{userId}",userId)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        var list1=JSONObject.parseArray(resGet1.getResponse().getContentAsString());
        assertEquals(list1.size(),1);
        var resvo1=JSONObject.parseObject(list1.get(0).toString(),GroupVo.class);
        assertTrue(assertGroupVo(groupVo,resvo1));
        var groupId=resvo1.getId();
        groupVo.setName("SKTELECOM");
        mockMvc.perform(MockMvcRequestBuilders.put("/group/{groupId}",groupId).contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(groupVo))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        resGet1=mockMvc.perform(MockMvcRequestBuilders.get("/group/getgroup/{userId}",userId)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        var list2=JSONObject.parseArray(resGet1.getResponse().getContentAsString());
        assertEquals(list1.size(),1);
        var resvo2=JSONObject.parseObject(list2.get(0).toString(),GroupVo.class);
        assertTrue(assertGroupVo(groupVo,resvo2));

        mockMvc.perform(MockMvcRequestBuilders.delete("/group/{id}",groupId)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        resGet1=mockMvc.perform(MockMvcRequestBuilders.get("/group/getgroup/{userId}",userId)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        var list3=JSONObject.parseArray(resGet1.getResponse().getContentAsString());
        assertEquals(list3.size(),0);

    }

    @Test 
    public void TestMember() throws Exception{
        var userVo = new UserVo(null, "SKTFaker", "123123");
        mockMvc.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(userVo))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        
        var sessionVo=new SessionVo("SKTFaker","123123");
        MvcResult sResult=mockMvc.perform(MockMvcRequestBuilders.post("/session").contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(sessionVo))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Long userId= JSONObject.parseObject(sResult.getResponse().getContentAsString(), Long.class);

        var userVo1 = new UserVo(null, "SKTFake", "1231231");
        mockMvc.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(userVo1))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        
        var sessionVo1=new SessionVo("SKTFake","1231231");
        MvcResult sResult1=mockMvc.perform(MockMvcRequestBuilders.post("/session").contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(sessionVo1))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Long userId1= JSONObject.parseObject(sResult1.getResponse().getContentAsString(), Long.class);

        var groupVo=new GroupVo(null,userId,"SKTelecom","xxx");
        mockMvc.perform(MockMvcRequestBuilders.post("/group").contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(groupVo))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        
        MvcResult resGet1=mockMvc.perform(MockMvcRequestBuilders.get("/group/getgroup/{userId}",userId)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        var list1=JSONObject.parseArray(resGet1.getResponse().getContentAsString());
        assertEquals(list1.size(),1);
        var resvo1=JSONObject.parseObject(list1.get(0).toString(),GroupVo.class);
        assertTrue(assertGroupVo(groupVo,resvo1));
        var groupId=resvo1.getId();
        mockMvc.perform(MockMvcRequestBuilders.post("/group/{groupId}/add/{userId}",groupId,userId1).param("inviteCode", resvo1.getInviteCode())).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        MvcResult sResult2=mockMvc.perform(MockMvcRequestBuilders.get("/group/getuser/{groupId}",groupId)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        var list2=JSONObject.parseArray(sResult2.getResponse().getContentAsString());
        assertEquals(list2.size(),2);
        var resvo20=JSONObject.parseObject(list2.get(0).toString(),GroupMemberVo.class);
        var resvo21=JSONObject.parseObject(list2.get(1).toString(),GroupMemberVo.class);
        assertTrue(assertUserVo(userVo,resvo20));
        assertTrue(assertUserVo(userVo1,resvo21));

        mockMvc.perform(MockMvcRequestBuilders.put("/group/{groupId}/authority/{userId}",groupId,userId1).param("level","MotherFucker")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        MvcResult sResult3=mockMvc.perform(MockMvcRequestBuilders.get("/group/{groupId}/authority/{userId}",groupId,userId1)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        var level=sResult3.getResponse().getContentAsString();
        assertEquals(level,"MotherFucker");

        mockMvc.perform(MockMvcRequestBuilders.delete("/group/{groupId}/remove/{userId}",groupId,userId1)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        sResult2=mockMvc.perform(MockMvcRequestBuilders.get("/group/getuser/{groupId}",groupId)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        list2=JSONObject.parseArray(sResult2.getResponse().getContentAsString());
        assertEquals(list1.size(),1);
        
    }
    @Test
    public void TestNotice() throws Exception{
        var userVo = new UserVo(null, "SKTFaker", "123123");
        mockMvc.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(userVo))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        
            var sessionVo=new SessionVo("SKTFaker","123123");
        MvcResult sResult=mockMvc.perform(MockMvcRequestBuilders.post("/session").contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(sessionVo))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Long userId= JSONObject.parseObject(sResult.getResponse().getContentAsString(), Long.class);

        var groupVo=new GroupVo(null,userId,"SKTelecom","xxx");
        mockMvc.perform(MockMvcRequestBuilders.post("/group").contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(groupVo))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        
        MvcResult resGet1=mockMvc.perform(MockMvcRequestBuilders.get("/group/getgroup/{userId}",userId)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        var list1=JSONObject.parseArray(resGet1.getResponse().getContentAsString());
        assertEquals(list1.size(),1);
        var resvo1=JSONObject.parseObject(list1.get(0).toString(),GroupVo.class);
        assertTrue(assertGroupVo(groupVo,resvo1));
        var groupId=resvo1.getId();

        var noticeVo=new GroupNoticeVo(null,groupId,"NoticeTitle","Content","123123");
        mockMvc.perform(MockMvcRequestBuilders.post("/group/notice").contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(noticeVo))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        MvcResult resGet2=mockMvc.perform(MockMvcRequestBuilders.get("/group/notice/{groupId}",groupId)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        var ra=JSONObject.parseArray(resGet2.getResponse().getContentAsString());     
        assertEquals(ra.size(),1);                      
        var noticeRes=JSONObject.parseObject(ra.get(0).toString(),GroupNoticeVo.class);
        assertTrue(assertNotice(noticeRes,noticeVo));
        var noticeId=noticeRes.getId();
        
        noticeVo.setTitle("Royal Never Give Up");
        noticeVo.setTime("2020");
        noticeVo.setContent("UziGod");
        mockMvc.perform(MockMvcRequestBuilders.put("/group/notice/{id}",noticeId).contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(noticeVo))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        
        resGet2=mockMvc.perform(MockMvcRequestBuilders.get("/group/notice/{groupId}",groupId)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        ra=JSONObject.parseArray(resGet2.getResponse().getContentAsString()); 
        assertEquals(ra.size(),1);                          
        noticeRes=JSONObject.parseObject(ra.get(0).toString(),GroupNoticeVo.class);
        assertTrue(assertNotice(noticeRes,noticeVo));

        mockMvc.perform(MockMvcRequestBuilders.delete("/group/notice/{id}",noticeId)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        resGet2=mockMvc.perform(MockMvcRequestBuilders.get("/group/notice/{groupId}",groupId)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        ra=JSONObject.parseArray(resGet2.getResponse().getContentAsString()); 
        assertEquals(ra.size(),0);      

    }


    boolean assertGroupVo(GroupVo v1,GroupVo v2){
        return v1.getName().equals(v2.getName()) && v1.getLeaderId().equals(v2.getLeaderId());
    }
    boolean assertUserVo(UserVo v1,GroupMemberVo v2){
        return v1.getUsername().equals(v2.getUsername());
    }
    boolean assertNotice(GroupNoticeVo v1,GroupNoticeVo v2){
        return v1.getGroupId().equals(v2.getGroupId()) && v1.getTitle().equals(v2.getTitle()) &&
            v1.getContent().equals(v2.getContent());
            

    }

}