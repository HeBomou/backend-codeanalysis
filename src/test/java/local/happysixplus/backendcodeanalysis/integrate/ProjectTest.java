package local.happysixplus.backendcodeanalysis.integrate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProjectTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void Test1() throws Exception {
        MvcResult resAdd = mockMvc
                .perform(MockMvcRequestBuilders.post("/project").param("projectName", "Linux")
                        .param("url", "https://gitee.com/forsakenspirit/Linux").param("userId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();

        MvcResult resGet = mockMvc.perform(MockMvcRequestBuilders.get("/project").param("userId", "1"))
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
}
