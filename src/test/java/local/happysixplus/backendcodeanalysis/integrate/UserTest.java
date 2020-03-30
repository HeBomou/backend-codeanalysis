
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
    public void addUser1() throws Exception {
        data.deleteAll();
        var userVo = new UserVo(null, "hello", "XXX");
        mockMvc.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(userVo))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        var sessionVo = new SessionVo("hello", "xxx");
        mockMvc.perform(MockMvcRequestBuilders.post("/session").contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(sessionVo)))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError()).andReturn();
        sessionVo.setPwdMd5("XXX");
        mockMvc.perform(MockMvcRequestBuilders.post("/session").contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(sessionVo))).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void addUser2() throws Exception {
        data.deleteAll();
        var userVo = new UserVo(null, "hello", "XXX");
        mockMvc.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(userVo))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        // 不允许用户重名
        mockMvc.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(userVo))).andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andReturn();
    }

    @Test
    public void deleteUser1() throws Exception {
        data.deleteAll();
        // 注册
        var userVo = new UserVo(null, "hello", "XXX");
        mockMvc.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(userVo))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        // 登陆
        var sessionVo = new SessionVo("hello", "XXX");
        var res = mockMvc
                .perform(MockMvcRequestBuilders.post("/session").contentType(MediaType.APPLICATION_JSON)
                        .content(JSONObject.toJSONString(sessionVo)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Long userId = Long.valueOf(res.getResponse().getContentAsString());
        // 删除用户
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/{id}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        // 禁止重复删除
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/{id}", userId))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError()).andReturn();
        // 禁止登陆
        mockMvc.perform(MockMvcRequestBuilders.post("/session").contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(sessionVo)))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError()).andReturn();
    }

    @Test
    public void updateUser1() throws Exception {
        data.deleteAll();
        // 注册
        var userVo = new UserVo(null, "hello", "XXX");
        mockMvc.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(userVo))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        // 登陆
        var sessionVo = new SessionVo("hello", "XXX");
        var res = mockMvc
                .perform(MockMvcRequestBuilders.post("/session").contentType(MediaType.APPLICATION_JSON)
                        .content(JSONObject.toJSONString(sessionVo)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Long userId = Long.valueOf(res.getResponse().getContentAsString());
        // 更新用户密码
        userVo.setId(userId);
        userVo.setPwdMd5("AAA");
        mockMvc.perform(MockMvcRequestBuilders.put("/user/{id}", userId).contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(userVo))).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        // 原密码无法登陆
        mockMvc.perform(MockMvcRequestBuilders.post("/session").contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(sessionVo)))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError()).andReturn();
        // 新密码成功登陆
        sessionVo.setPwdMd5("AAA");
        mockMvc.perform(MockMvcRequestBuilders.post("/session").contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(sessionVo))).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void updateUser2() throws Exception {
        data.deleteAll();
        // 注册
        var userVo = new UserVo(null, "hello", "XXX");
        mockMvc.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(userVo))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        // 登陆
        var sessionVo = new SessionVo("hello", "XXX");
        var res = mockMvc
                .perform(MockMvcRequestBuilders.post("/session").contentType(MediaType.APPLICATION_JSON)
                        .content(JSONObject.toJSONString(sessionVo)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Long userId = Long.valueOf(res.getResponse().getContentAsString());
        // 更新用户名
        userVo.setId(userId);
        userVo.setUsername("hhhh");
        mockMvc.perform(MockMvcRequestBuilders.put("/user/{id}", userId).contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(userVo))).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        // 原用户名无法登陆
        mockMvc.perform(MockMvcRequestBuilders.post("/session").contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(sessionVo)))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError()).andReturn();
        // 新用户名成功登陆
        sessionVo.setUsername("hhhh");
        mockMvc.perform(MockMvcRequestBuilders.post("/session").contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(sessionVo))).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
}