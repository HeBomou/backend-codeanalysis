package local.happysixplus.backendcodeanalysis.integrate;

import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.xdevapi.JsonArray;

import local.happysixplus.backendcodeanalysis.data.ProjectData;
import local.happysixplus.backendcodeanalysis.data.SubgraphData;
import local.happysixplus.backendcodeanalysis.vo.*;
import lombok.var;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

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
	public void init() {
	}

	@Test // 新建项目，根据ID获取信息
	public void Test1() throws Exception {
		MvcResult resAdd = mockMvc
				.perform(MockMvcRequestBuilders.post("/project").param("projectName", "Linux")
						.param("url", "https://gitee.com/forsakenspirit/Linux").param("userId", "2"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var allVo = JSONObject.parseObject(resAdd.getResponse().getContentAsString(), ProjectAllVo.class);
		long projectId = allVo.getId();
		MvcResult resGet = mockMvc.perform(MockMvcRequestBuilders.get("/project/{id}", projectId))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var resa = JSONObject.parseObject(resGet.getResponse().getContentAsString(), ProjectAllVo.class);
		assertEquals(resa.getDynamicVo().getProjectName(), "Linux");
		assertEquals(resa.getEdges().size(), 10);
		assertEquals(resa.getVertices().size(), 9);
	}

	@Test // 新建项目，根据UserID获取项目概要（动态信息）
	public void Test2() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/project").param("projectName", "Linux2")
				.param("url", "https://gitee.com/forsakenspirit/Linux").param("userId", "3"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		MvcResult resGet = mockMvc.perform(MockMvcRequestBuilders.get("/project").param("userId", "3"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var list = JSONObject.parseArray(resGet.getResponse().getContentAsString());
		var resa = JSONObject.parseObject(list.get(0).toString(), ProjectDynamicVo.class);
		assertEquals(resa.getProjectName(), "Linux2");
	}

	@Test // 新建项目，更新
	public void Test3() throws Exception {
		MvcResult resAdd = mockMvc
				.perform(MockMvcRequestBuilders.post("/project").param("projectName", "Linux3")
						.param("url", "https://gitee.com/forsakenspirit/Linux").param("userId", "4"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var allVo = JSONObject.parseObject(resAdd.getResponse().getContentAsString(), ProjectAllVo.class);
		long projectId = allVo.getId();
		allVo.getDynamicVo().setProjectName("Test3WYM");
		mockMvc.perform(MockMvcRequestBuilders.put("/project/{projectId}/dynamic", projectId)
				.contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(allVo.getDynamicVo())))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		MvcResult resGet = mockMvc.perform(MockMvcRequestBuilders.get("/project/{id}", projectId))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var resa = JSONObject.parseObject(resGet.getResponse().getContentAsString(), ProjectAllVo.class);
		assertEquals(resa.getDynamicVo().getProjectName(), "Test3WYM");
	}

	@Test//新建项目 删除
	public void Test4() throws Exception{
		MvcResult resAdd = mockMvc
				.perform(MockMvcRequestBuilders.post("/project").param("projectName", "Linux4")
						.param("url", "https://gitee.com/forsakenspirit/Linux").param("userId", "5"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var allVo = JSONObject.parseObject(resAdd.getResponse().getContentAsString(), ProjectAllVo.class);
		long projectId = allVo.getId();
		mockMvc.perform(
			MockMvcRequestBuilders.delete("/project/{id}",projectId)
		).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		MvcResult resGet = mockMvc.perform(MockMvcRequestBuilders.get("/project").param("userId", "5"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var list = JSONObject.parseArray(resGet.getResponse().getContentAsString());
		assertEquals(list.size(),0);

	}
	// @Test // 测试新建项目
	// public void Test8() throws Exception {
	// 	mockMvc.perform(MockMvcRequestBuilders.post("/project").param("projectName", "iTrust")
	// 			.param("url", "https://gitee.com/forsakenspirit/Demo").param("userId", "6324"))
	// 			.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

	// }

	// @Test // 测试新建项目
	// public void Test9() throws Exception {
	// 	mockMvc.perform(MockMvcRequestBuilders.post("/project").param("projectName", "TestEightDemo")
	// 			.param("url", "https://gitee.com/HeBomou/funnylayer.git").param("userId", "4396"))
	// 			.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

	// }

	// @Test // 测试新建项目
	// public void Test10() throws Exception {
	// 	mockMvc.perform(MockMvcRequestBuilders.post("/project").param("projectName", "TestTDemo")
	// 			.param("url", "https://gitee.com/HeBomou/itrust.git").param("userId", "4396"))
	// 			.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

	// }

}
