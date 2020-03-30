
package local.happysixplus.backendcodeanalysis.integrate;

import com.alibaba.fastjson.JSONObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import local.happysixplus.backendcodeanalysis.data.UserData;
import local.happysixplus.backendcodeanalysis.vo.SessionVo;
import local.happysixplus.backendcodeanalysis.vo.UserVo;
import lombok.var;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserData data;

    @Test
    public void addUser() throws Exception {
        data.deleteAll();
        var userVo = new UserVo(null, "hello", "XXX");
        mockMvc.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(userVo))).andExpect(MockMvcResultMatchers.status().isOk());
        var sessionVo = new SessionVo("hello", "xxx");
        mockMvc.perform(MockMvcRequestBuilders.post("/session").contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(sessionVo)))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
        sessionVo.setPwdMd5("XXX");
        mockMvc.perform(MockMvcRequestBuilders.post("/session").contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(sessionVo))).andExpect(MockMvcResultMatchers.status().isOk());
    }
}