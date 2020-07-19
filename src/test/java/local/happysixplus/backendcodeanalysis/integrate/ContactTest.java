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



import local.happysixplus.backendcodeanalysis.data.ContactData;
import local.happysixplus.backendcodeanalysis.data.UserData;
import local.happysixplus.backendcodeanalysis.vo.UserVo;
import local.happysixplus.backendcodeanalysis.vo.ContactVo;
import local.happysixplus.backendcodeanalysis.vo.SessionVo;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import lombok.var;

@SpringBootTest(webEnvironment =SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class ContactTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ContactData cData;

    @Autowired
    UserData uData;
    @BeforeEach
    public void init(){
        cData.deleteAll();
        uData.deleteAll();
    }

    @Test
    public void testContact() throws Exception{
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

        var userVo2 = new UserVo(null, "SKTOtto", "1231231123");
        mockMvc.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(userVo2))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        
        var sessionVo2=new SessionVo("SKTOtto","1231231123");
        MvcResult sResult2=mockMvc.perform(MockMvcRequestBuilders.post("/session").contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(sessionVo2))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Long userId2= JSONObject.parseObject(sResult2.getResponse().getContentAsString(), Long.class);

        // ContactVo contactvo1=new ContactVo(null,userId,"SKTFaker",0);
        ContactVo contactvo2=new ContactVo(null,userId1,"SKTFake",0);
        ContactVo contactvo3=new ContactVo(null,userId2,"SKTOtto",2);
        mockMvc.perform(MockMvcRequestBuilders.post("/contact/{userId}/{contactUserId}",userId,userId1).param("read","0")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/contact/{userId}/{contactUserId}",userId,userId2).param("read","2")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        MvcResult res1=mockMvc.perform(MockMvcRequestBuilders.get("/contact/{userId}/{contactUserId}",userId,userId1)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        var resVo1=JSONObject.parseObject(res1.getResponse().getContentAsString(),ContactVo.class);
        contactvo2.setId(resVo1.getId());
        assertEquals(contactvo2,resVo1);

        MvcResult res2=mockMvc.perform(MockMvcRequestBuilders.get("/contact/{userId}/{contactUserId}",userId,userId2)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        var resVo2=JSONObject.parseObject(res2.getResponse().getContentAsString(),ContactVo.class);
        contactvo3.setId(resVo2.getId());
        assertEquals(contactvo3,resVo2);

        MvcResult res3=mockMvc.perform(MockMvcRequestBuilders.get("/contact/{userId}",userId)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        var list1=JSONObject.parseArray(res3.getResponse().getContentAsString());
        assertEquals(list1.size(),2);
        assertEquals(JSONObject.parseObject(list1.get(0).toString(),ContactVo.class),contactvo2);
        assertEquals(JSONObject.parseObject(list1.get(1).toString(),ContactVo.class),contactvo3);

        MvcResult res4=mockMvc.perform(MockMvcRequestBuilders.get("/contact/exist/{userId}/{contactUserId}",userId,userId1)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        var bool=JSONObject.parseObject(res4.getResponse().getContentAsString(),Boolean.class);
        assertTrue(bool);

        MvcResult res5=mockMvc.perform(MockMvcRequestBuilders.get("/contact/{userId}/new",userId)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        bool=JSONObject.parseObject(res5.getResponse().getContentAsString(),Boolean.class);
        assertTrue(bool);

        mockMvc.perform(MockMvcRequestBuilders.put("/contact/{userId}/{contactUserId}",userId,userId1).param("read", "1")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        res5=mockMvc.perform(MockMvcRequestBuilders.get("/contact/{userId}/new",userId)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        bool=JSONObject.parseObject(res5.getResponse().getContentAsString(),Boolean.class);
        assertTrue(!bool);
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/contact/{userId}/{contactUserId}",userId,userId1)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        res4=mockMvc.perform(MockMvcRequestBuilders.get("/contact/exist/{userId}/{contactUserId}",userId,userId1)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        bool=JSONObject.parseObject(res4.getResponse().getContentAsString(),Boolean.class);
        assertTrue(!bool);
    }

}