package local.happysixplus.backendcodeanalysis.integrate;

import com.alibaba.fastjson.JSONObject;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
		MvcResult resAdd = mockMvc.perform(MockMvcRequestBuilders.post("/project").param("projectName", "Linux")
				.param("url", "https://gitee.com/forsakenspirit/Linux").param("userId", "2").param("groupId", "-1"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var allVo = JSONObject.parseObject(resAdd.getResponse().getContentAsString(), ProjectAllVo.class);
		long projectId = allVo.getId();
		MvcResult resGet;
		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(1000 * 10);
				System.out.println(1);
				resGet = mockMvc.perform(MockMvcRequestBuilders.get("/project/{id}", projectId))
						.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
				break;
			} catch (Throwable T) {
				continue;
			}
		}
		resGet = mockMvc.perform(MockMvcRequestBuilders.get("/project/{id}", projectId))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var resa = JSONObject.parseObject(resGet.getResponse().getContentAsString(), ProjectAllVo.class);
		assertEquals(resa.getDynamicVo().getProjectName(), "Linux");
		assertEquals(resa.getEdges().size(), 10);
		assertEquals(resa.getVertices().size(), 9);
	}

	@Test // 新建项目，根据UserID获取项目概要（动态信息）
	public void Test2() throws Exception {
		MvcResult resAdd = mockMvc.perform(MockMvcRequestBuilders.post("/project").param("projectName", "Linux2")
				.param("url", "https://gitee.com/forsakenspirit/Linux").param("userId", "3").param("groupId", "-1"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		MvcResult resGet;
		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(1000 * 10);
				System.out.println(2);
				resGet = mockMvc.perform(MockMvcRequestBuilders.get("/project").param("userId", "3"))
						.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
				break;
			} catch (Throwable T) {
				continue;
			}
		}
		resGet = mockMvc.perform(MockMvcRequestBuilders.get("/project").param("userId", "3"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var list = JSONObject.parseArray(resGet.getResponse().getContentAsString());
		var resa = JSONObject.parseObject(list.get(0).toString(), ProjectDynamicVo.class);
		assertEquals(resa.getProjectName(), "Linux2");
		var allVo = JSONObject.parseObject(resAdd.getResponse().getContentAsString(), ProjectAllVo.class);
		assertEquals(allVo.getId(), allVo.getDynamicVo().getId());
	}

	@Test // 新建项目，更新
	public void Test3() throws Exception {
		MvcResult resAdd = mockMvc.perform(MockMvcRequestBuilders.post("/project").param("projectName", "Linux3")
				.param("url", "https://gitee.com/forsakenspirit/Linux").param("userId", "4").param("groupId", "-1"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var allVo = JSONObject.parseObject(resAdd.getResponse().getContentAsString(), ProjectAllVo.class);
		long projectId = allVo.getId();
		allVo.getDynamicVo().setProjectName("Test3WYM");
		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(1000 * 10);
				System.out.println(3);
				mockMvc.perform(MockMvcRequestBuilders.get("/project/{id}", projectId))
						.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
				break;
			} catch (Throwable T) {
				continue;
			}
		}
		mockMvc.perform(MockMvcRequestBuilders.put("/project/{projectId}/dynamic", projectId)
				.contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(allVo.getDynamicVo())))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		MvcResult resGet = mockMvc.perform(MockMvcRequestBuilders.get("/project/{id}", projectId))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var resa = JSONObject.parseObject(resGet.getResponse().getContentAsString(), ProjectAllVo.class);
		assertEquals(resa.getDynamicVo().getProjectName(), "Test3WYM");
	}

	@Test // 新建项目 删除
	public void Test4() throws Exception {
		MvcResult resAdd = mockMvc.perform(MockMvcRequestBuilders.post("/project").param("projectName", "Linux4")
				.param("url", "https://gitee.com/forsakenspirit/Linux").param("userId", "5").param("groupId", "-1"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var allVo = JSONObject.parseObject(resAdd.getResponse().getContentAsString(), ProjectAllVo.class);
		long projectId = allVo.getId();
		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(1000 * 10);
				System.out.println(4);
				mockMvc.perform(MockMvcRequestBuilders.get("/project/{id}", projectId))
						.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
				break;
			} catch (Throwable T) {
				continue;
			}
		}
		mockMvc.perform(MockMvcRequestBuilders.delete("/project/{id}", projectId))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		boolean check = false;
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/project/{id}", projectId))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
			check = true;
		} catch (Throwable T) {
			return;
		}
		assertEquals(check, true);
	}

	@Test // 子图相关
	public void Test5() throws Exception {
		MvcResult resAdd = mockMvc.perform(MockMvcRequestBuilders.post("/project").param("projectName", "Test5")
				.param("url", "https://gitee.com/forsakenspirit/Linux").param("userId", "55").param("groupId", "-1"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var allVo = JSONObject.parseObject(resAdd.getResponse().getContentAsString(), ProjectAllVo.class);
		long projectId = allVo.getId();
		MvcResult resGet;
		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(1000 * 10);
				System.out.println(5);
				resGet = mockMvc.perform(MockMvcRequestBuilders.get("/project/{id}", projectId))
						.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
				break;
			} catch (Throwable T) {
				continue;
			}
		}
		resGet = mockMvc.perform(MockMvcRequestBuilders.get("/project/{id}", projectId))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		// 添加子图
		mockMvc.perform(MockMvcRequestBuilders.post("/project/{projectId}/subgraph", projectId)
				.param("threshold", "0.3").param("name", "testSubgraphzero"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		// 验证结果
		resGet = mockMvc.perform(MockMvcRequestBuilders.get("/project/{id}", projectId))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var allVo2 = JSONObject.parseObject(resGet.getResponse().getContentAsString(), ProjectAllVo.class);
		var sgs = allVo2.getSubgraphs();
		assertEquals(sgs.size(), 2);
		sgs.sort((a, b) -> (int) (a.getId() - b.getId()));
		assertEquals(sgs.get(0).getThreshold(), 0d);
		assertEquals(sgs.get(1).getThreshold(), 0.3d);
		assertEquals(sgs.get(1).getDynamicVo().getName(), "testSubgraphzero");
		// 删除子图
		mockMvc.perform(
				MockMvcRequestBuilders.delete("/project/{projectId}/subgraph/{id}", projectId, sgs.get(1).getId()))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		// 验证结果
		resGet = mockMvc.perform(MockMvcRequestBuilders.get("/project/{id}", projectId))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var allVo3 = JSONObject.parseObject(resGet.getResponse().getContentAsString(), ProjectAllVo.class);
		var sgs1 = allVo3.getSubgraphs();
		assertEquals(sgs1.size(), 1);
		// 获取子图
		MvcResult resGet1 = mockMvc.perform(MockMvcRequestBuilders.get("/project/{id}", projectId))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var resa = JSONObject.parseObject(resGet1.getResponse().getContentAsString(), ProjectAllVo.class);
		// 更新子图
		SubgraphDynamicVo vo = resa.getSubgraphs().get(0).getDynamicVo();
		vo.setName("SKTelecomT1Faker");
		mockMvc.perform(
				MockMvcRequestBuilders.put("/project/{projectId}/subgraph/{id}/dynamic", projectId, sgs.get(1).getId())
						.contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(vo)))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		// 验证结果
		resGet = mockMvc.perform(MockMvcRequestBuilders.get("/project/{id}", projectId))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var allVo4 = JSONObject.parseObject(resGet.getResponse().getContentAsString(), ProjectAllVo.class);
		var sgs2 = allVo4.getSubgraphs();
		assertEquals(sgs2.size(), 1);
		assertEquals(sgs2.get(0).getDynamicVo().getName(), "SKTelecomT1Faker");

	}

	@Test // 测试获取函数
	public void Test6() throws Exception {
		MvcResult resAdd = mockMvc.perform(MockMvcRequestBuilders.post("/project").param("projectName", "Test6")
				.param("url", "https://gitee.com/forsakenspirit/Linux").param("userId", "66").param("groupId", "-1"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var v = resAdd.getResponse().getContentAsString();
		var vo = (ProjectAllVo) JSONObject.parseObject(v, ProjectAllVo.class);
		long projectId = vo.getId();
		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(1000 * 10);
				System.out.println(6);
				mockMvc.perform(MockMvcRequestBuilders.get("/project/{id}", projectId))
						.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
				break;
			} catch (Throwable T) {
				continue;
			}
		}
		MvcResult resGetFunction = mockMvc.perform(
				MockMvcRequestBuilders.get("/project/{projectId}/similarFunction", projectId).param("funcName", "C"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		List<String> strs = JSONObject.parseArray(resGetFunction.getResponse().getContentAsString(), String.class);
		Set<String> s = new HashSet<>(strs);
		Set<String> expected = new HashSet<>();
		expected.add("temp.C:Cmake()");
		expected.add("temp.C:<init>()");
		expected.add("temp.C:Cprint(int,java.lang.Integer)");
		assertEquals(expected, s);
	}

	@Test // 测试获取路径
	public void Test7() throws Exception {

		MvcResult resAdd = mockMvc.perform(MockMvcRequestBuilders.post("/project").param("projectName", "TestSeven")
				.param("url", "https://gitee.com/forsakenspirit/Linux").param("userId", "77").param("groupId", "-1"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var v = resAdd.getResponse().getContentAsString();
		var vo = (ProjectAllVo) JSONObject.parseObject(v, ProjectAllVo.class);
		long projectId = vo.getId();
		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(1000 * 10);
				System.out.println(7);
				mockMvc.perform(MockMvcRequestBuilders.get("/project/{id}", projectId))
						.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
				break;
			} catch (Throwable T) {
				continue;
			}
		}
		MvcResult resGet1 = mockMvc.perform(MockMvcRequestBuilders.get("/project/{id}", projectId))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var resa = JSONObject.parseObject(resGet1.getResponse().getContentAsString(), ProjectAllVo.class);
		List<VertexAllVo> vdvs = resa.getVertices();
		long id1 = -1L;
		long id2 = -1L;
		for (VertexAllVo vvv : vdvs) {
			if (vvv.getFunctionName().contains("main"))
				id1 = vvv.getId();
			if (vvv.getFunctionName().contains("Baker"))
				id2 = vvv.getId();

		}
		assertNotEquals(id1, -1L);
		assertNotEquals(id2, -1L);
		MvcResult resGetFunction = mockMvc
				.perform(MockMvcRequestBuilders.get("/project/{projectId}/originalGraphPath", projectId)
						.param("startVertexId", Long.toString(id1)).param("endVertexId", Long.toString(id2)))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var res = resGetFunction.getResponse().getContentAsString();
		var path = JSONObject.parseObject(res, PathVo.class);
		assertEquals(path.getNum(), 1);
		// System.out.println(res);

	}

	@Test // 测试新建项目
	public void Test8() throws Exception {
		// var res =
		mockMvc.perform(MockMvcRequestBuilders.post("/project").param("projectName", "iTrust")
				.param("url", "https://gitee.com/forsakenspirit/Demo").param("userId", "6324").param("groupId", "-1"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test // 测试新建项目
	public void Test9() throws Exception {
		// var res =
		mockMvc.perform(MockMvcRequestBuilders.post("/project").param("projectName", "TestEightDemo")
				.param("url", "https://gitee.com/HeBomou/funnylayer.git").param("userId", "4396")
				.param("groupId", "-1")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		// var vo = JSONObject.parseObject(res.getResponse().getContentAsString(),
		// ProjectAllVo.class);
		// assertNotEquals(vo.getId(), null);
		// assertEquals(vo.getDynamicVo().getProjectName(), "TestEightDemo");
		// assertNotEquals(vo.getVertices().size(), 0);
		// assertNotEquals(vo.getEdges().size(), 0);
		// assertEquals(vo.getSubgraphs().size(), 1);
	}

	@Test // 测试新建项目
	public void Test10() throws Exception {
		// var res =
		mockMvc.perform(MockMvcRequestBuilders.post("/project").param("projectName", "TestT Demo")
				.param("url", "https://gitee.com/HeBomou/itrust.git").param("userId", "4396").param("groupId", "-1"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		// var vo = JSONObject.parseObject(res.getResponse().getContentAsString(),
		// ProjectAllVo.class);
		// assertNotEquals(vo.getId(), null);
		// assertEquals(vo.getDynamicVo().getProjectName(), "TestT Demo");
		// assertNotEquals(vo.getVertices().size(), 0);
		// assertNotEquals(vo.getEdges().size(), 0);
		// assertEquals(vo.getSubgraphs().size(), 1);
	}

	@Test // 测试项目简介
	public void Test11() throws Exception {
		MvcResult resAdd = mockMvc.perform(MockMvcRequestBuilders.post("/project").param("projectName", "TestSeven")
				.param("url", "https://gitee.com/forsakenspirit/Linux").param("userId", "99").param("groupId", "-1"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var v = resAdd.getResponse().getContentAsString();
		var vo = (ProjectAllVo) JSONObject.parseObject(v, ProjectAllVo.class);
		long projectId = vo.getId();
		for (int i = 0; i < 8; i++) {
			try {
				Thread.sleep(1000 * 10);
				System.out.println(11);
				mockMvc.perform(MockMvcRequestBuilders.get("/project/{id}", projectId))
						.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
				break;
			} catch (Throwable T) {
				continue;
			}
		}
		MvcResult resGet = mockMvc.perform(MockMvcRequestBuilders.get("/project/{id}/profile", projectId))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var profileVo = JSONObject.parseObject(resGet.getResponse().getContentAsString(), ProjectProfileVo.class);
		assertEquals(9, profileVo.getVertexNum());
		assertEquals(10, profileVo.getEdgeNum());
		assertEquals(1, profileVo.getSubgraphNum());
		assertEquals(0, profileVo.getConnectiveDomainAnotationNum());
		assertEquals(0, profileVo.getVertexAnotationNum());
		assertEquals(0, profileVo.getEdgeAnotationNum());

	}

	@Test // 测试basicattribute
	public void Test12() throws Exception {
		MvcResult result0 = mockMvc.perform(MockMvcRequestBuilders.get("/project"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var list0 = JSONObject.parseArray(result0.getResponse().getContentAsString());
		MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders.get("/project").param("userId", "4396"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var list1 = JSONObject.parseArray(result1.getResponse().getContentAsString());

		MvcResult resAdd = mockMvc.perform(MockMvcRequestBuilders.post("/project").param("projectName", "LastOrder")
				.param("url", "https://gitee.com/forsakenspirit/Linux").param("userId", "4396").param("groupId", "-1"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var v = resAdd.getResponse().getContentAsString();
		var vo = (ProjectAllVo) JSONObject.parseObject(v, ProjectAllVo.class);
		long projectId = vo.getId();
		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(1000 * 10);
				System.out.println(12);
				mockMvc.perform(MockMvcRequestBuilders.get("/project/{id}", projectId))
						.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
				break;
			} catch (Throwable T) {
				continue;
			}
		}
		MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.get("/project"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var list2 = JSONObject.parseArray(result2.getResponse().getContentAsString());
		MvcResult result3 = mockMvc.perform(MockMvcRequestBuilders.get("/project").param("userId", "4396"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var list3 = JSONObject.parseArray(result3.getResponse().getContentAsString());
		assertEquals(list0.size() + 1, list2.size());
		assertEquals(list1.size() + 1, list3.size());
	}

	@Test // 测试依赖图有环
	public void Test13() throws Exception {
		var res = mockMvc.perform(MockMvcRequestBuilders.post("/project").param("projectName", "Test Simple")
				.param("url", "https://gitee.com/HeBomou/simple.git").param("userId", "4396").param("groupId", "-1"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var vo1 = JSONObject.parseObject(res.getResponse().getContentAsString(), ProjectAllVo.class);
		long projectId = vo1.getId();
		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(1000 * 10);
				System.out.println(13);
				mockMvc.perform(MockMvcRequestBuilders.get("/project/{id}", projectId))
						.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
				break;
			} catch (Throwable T) {
				continue;
			}
		}
		MvcResult resGet1 = mockMvc.perform(MockMvcRequestBuilders.get("/project/{id}", vo1.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		var vo = JSONObject.parseObject(resGet1.getResponse().getContentAsString(), ProjectAllVo.class);
		assertNotEquals(vo.getId(), null);
		assertEquals(vo.getDynamicVo().getProjectName(), "Test Simple");
		assertEquals(vo.getVertices().size(), 9);
		assertEquals(vo.getEdges().size(), 8);
		assertEquals(vo.getSubgraphs().size(), 1);
		assertEquals(vo.getSubgraphs().get(0).getConnectiveDomains().size(), 2);

		for (int i = 0; i < 20; i++) {
			Long id1 = vo.getVertices().get((int) (Math.random() * vo.getVertices().size())).getId();
			Long id2 = vo.getVertices().get((int) (Math.random() * vo.getVertices().size())).getId();
			MvcResult resGetFunction = mockMvc
					.perform(MockMvcRequestBuilders.get("/project/{projectId}/originalGraphPath", vo.getId())
							.param("startVertexId", Long.toString(id1)).param("endVertexId", Long.toString(id2)))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
			var pathVo = JSONObject.parseObject(resGetFunction.getResponse().getContentAsString(), PathVo.class);
			// System.out.print(pathVo.getNum());
			for (var path : pathVo.getPaths())
				assertEquals(path.size(), new HashSet<>(path).size());
		}
	}

}
