package local.happysixplus.backendcodeanalysis.integrate;

import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.xdevapi.JsonArray;

import local.happysixplus.backendcodeanalysis.data.ProjectData;
import local.happysixplus.backendcodeanalysis.data.ProjectDynamicData;
import local.happysixplus.backendcodeanalysis.data.SubgraphData;
import local.happysixplus.backendcodeanalysis.data.SubgraphDynamicData;
import local.happysixplus.backendcodeanalysis.po.ProjectDynamicPo;
import local.happysixplus.backendcodeanalysis.po.SubgraphDynamicPo;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		MvcResult resAdd=
		mockMvc.perform(MockMvcRequestBuilders.post("/project").param("projectName", "Linux2")
				.param("url", "https://gitee.com/forsakenspirit/Linux").param("userId", "3"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		MvcResult resGet = mockMvc.perform(MockMvcRequestBuilders.get("/project").param("userId", "3"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var list = JSONObject.parseArray(resGet.getResponse().getContentAsString());
		var resa = JSONObject.parseObject(list.get(0).toString(), ProjectDynamicVo.class);
		assertEquals(resa.getProjectName(), "Linux2");
		var allVo = JSONObject.parseObject(resAdd.getResponse().getContentAsString(), ProjectAllVo.class);
		assertEquals(allVo.getId(),allVo.getDynamicVo().getId());
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

	@Test //子图相关
	public void Test5() throws Exception{
		MvcResult resAdd = mockMvc
				.perform(MockMvcRequestBuilders.post("/project").param("projectName", "Test5")
						.param("url", "https://gitee.com/forsakenspirit/Linux").param("userId", "55"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var allVo = JSONObject.parseObject(resAdd.getResponse().getContentAsString(), ProjectAllVo.class);
		long projectId = allVo.getId();
	}
	@Test
	public void Test6() throws Exception {
		MvcResult resAdd = mockMvc
				.perform(MockMvcRequestBuilders.post("/project").param("projectName", "Test6")
						.param("url", "https://gitee.com/forsakenspirit/Linux").param("userId", "66"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var v = resAdd.getResponse().getContentAsString();
		var vo = (ProjectAllVo) JSONObject.parseObject(v, ProjectAllVo.class);
		long projectId = vo.getId();
		MvcResult resGetFunction = mockMvc
				.perform(MockMvcRequestBuilders.get("/project/{projectId}/similarFunction", projectId).param("funcName",
						"C"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		List<String> strs = JSONObject.parseArray(resGetFunction.getResponse().getContentAsString(), String.class);
		Set<String> s=new HashSet<>(strs);
		Set<String> expected=new HashSet<>();
		expected.add("temp.C:Cmake()");
		expected.add("temp.C:<init>()");
		expected.add("temp.C:Cprint(int,java.lang.Integer)");
		assertEquals(expected, s);
	}

	@Test
	public void Test7() throws Exception {
		MvcResult resAdd = mockMvc
				.perform(MockMvcRequestBuilders.post("/project").param("projectName", "TestSeven")
						.param("url", "https://gitee.com/forsakenspirit/Linux").param("userId", "77"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var v = resAdd.getResponse().getContentAsString();
		var vo = (ProjectAllVo) JSONObject.parseObject(v, ProjectAllVo.class);
		long projectId = vo.getId();
		List<VertexAllVo> vdvs = vo.getVertices();
		long id1 = vdvs.get(0).getId();
		long id2 = vdvs.get(vdvs.size() - 1).getId();
		MvcResult resGetFunction = mockMvc
				.perform(MockMvcRequestBuilders.get("/project/{projectId}/originalGraphShortestPath", projectId)
						.param("startVertexId", Long.toString(id1)).param("endVertexId", Long.toString(id2)))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var res = resGetFunction.getResponse().toString();
		System.out.println(res);

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
