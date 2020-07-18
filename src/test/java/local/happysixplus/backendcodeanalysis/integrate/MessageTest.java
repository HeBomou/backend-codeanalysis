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



import local.happysixplus.backendcodeanalysis.data.MessageData;
import local.happysixplus.backendcodeanalysis.vo.MessageVo;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import lombok.var;

@SpringBootTest(webEnvironment =SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class MessageTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MessageData mData;
    @BeforeEach
    public void init(){
        mData.deleteAll();
    }

    @Test
    public void testMessage() throws Exception{
        var messageVo0=new MessageVo(null,1L,2L,"mybeloveddaughter","19990315");
        var messageVo1=new MessageVo(null,2L,1L,"forsakensoul","19990626");
        var messageVo2=new MessageVo(null,1L,2L,"love and peace","19991117");
        var messageVo3=new MessageVo(null,2L,1L,"verniercaliper","20200109");
        mockMvc.perform(MockMvcRequestBuilders.post("/message").contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(messageVo0))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/message").contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(messageVo1))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/message").contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(messageVo2))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/message").contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(messageVo3))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        
        MvcResult get=mockMvc.perform(MockMvcRequestBuilders.get("/message").param("senderId","1").param("receiverId","2")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        var list=JSONObject.parseArray(get.getResponse().getContentAsString());
        assertEquals(list.size(),4);
        List<MessageVo> resVos=new ArrayList<>();
        for(int i=0;i<4;i++){
            resVos.add(JSONObject.parseObject(list.get(i).toString(),MessageVo.class));
        }
        messageVo0.setId(resVos.get(0).getId());    assertEquals(messageVo0,resVos.get(0));
        messageVo1.setId(resVos.get(1).getId());    assertEquals(messageVo1,resVos.get(1));
        messageVo2.setId(resVos.get(2).getId());    assertEquals(messageVo2,resVos.get(2));
        messageVo3.setId(resVos.get(3).getId());    assertEquals(messageVo3,resVos.get(3));

        mockMvc.perform(MockMvcRequestBuilders.delete("/message/{id}",resVos.get(0).getId())).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        mockMvc.perform(MockMvcRequestBuilders.delete("/message/{id}",resVos.get(2).getId())).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        get=mockMvc.perform(MockMvcRequestBuilders.get("/message").param("senderId","2").param("receiverId","1")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        list=JSONObject.parseArray(get.getResponse().getContentAsString());
        assertEquals(list.size(),2);
        assertEquals(JSONObject.parseObject(list.get(0).toString(),MessageVo.class),messageVo1);
        assertEquals(JSONObject.parseObject(list.get(1).toString(),MessageVo.class),messageVo3);

    }

}