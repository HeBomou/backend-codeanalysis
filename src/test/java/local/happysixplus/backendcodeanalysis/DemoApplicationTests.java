package local.happysixplus.backendcodeanalysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import local.happysixplus.backendcodeanalysis.service.GraphService;
import local.happysixplus.backendcodeanalysis.vo.PathVo;
import local.happysixplus.backendcodeanalysis.vo.VertexVo;
import local.happysixplus.backendcodeanalysis.vo.EdgeVo;
import local.happysixplus.backendcodeanalysis.cli.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;
import java.io.*;



@SpringBootTest
class DemoApplicationTests {
	@Autowired
	GraphService graphService;

	static ByteArrayOutputStream outContent;
	static PrintStream console = null;

	@BeforeAll
	public static void setUp(){

		console = System.out;
		//把标准输出定向至ByteArrayOutputStream中
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
   
	}

	@AfterAll              // 后处理
	public static void tearDown() {
   
	   System.setOut(console);
	}

	@Test
	void contextLoads() {
		graphService.loadCode("testcases/test_case3.txt");
		graphService.loadCode("testcases/test_case1.txt");
	}

	@Test
	void contextLoads2() {
		graphService.loadCode("testcases/test_case2.txt");
	}

	@Test
	void getVertexNum() {
		graphService.loadCode("testcases/test_case1.txt");
		System.out.println(graphService.getVertexNum());
		assertEquals(3, graphService.getVertexNum());
	}

	@Test
	void getEdgeNum() {
		graphService.loadCode("testcases/test_case3.txt");
		assertEquals(6, graphService.getEdgeNum());
	}

	@Test
	void getEdgeNum2() {
		graphService.loadCode("testcases/test_case2.txt");
		assertEquals(0, graphService.getEdgeNum());
	}

	@Test
	void getConnectiveDomainNum() {
		graphService.loadCode("testcases.test_case1.txt");
		int a = graphService.getConnectiveDomainNum();
		assertEquals(1, graphService.getConnectiveDomainNum());
	}

	@Test
	void getConnectiveDomains() {
		//TODO:列表的结果怎么测试？
		graphService.loadCode("call_dependencies.txt");
		System.out.println(graphService.getConnectiveDomains().size());
	}

	@Test
	void setClosenessMin() {
		graphService.loadCode("call_dependencies.txt");
		graphService.setClosenessMin(0.5);

	}

	@Test
	void getConnectiveDomainsWithClosenessMin() {
		graphService.loadCode("call_dependencies.txt");
		graphService.setClosenessMin(0.05);
		graphService.getConnectiveDomainsWithClosenessMin();
	}

	@Test
	void getShortestPath() {
		//TODO: 如何访问私有变量？
		graphService.loadCode("testcases/test_case1.txt");
		String from = "edu.ncsu.csc.itrust.risk.factors.AgeFactorTest:testRegularAge()";
		String to = "edu.ncsu.csc.itrust.risk.factors.AgeFactorTest:assertFalse(boolean)";
		PathVo result = graphService.getShortestPath(new VertexVo(from), new VertexVo(to));
	}

	@Test
	void CLICommandExecutorBasicAttribute() {

		outContent.reset();
		graphService.loadCode("testcases/test_case1.txt");
		CLICommandExecutor cli = new CLICommandExecutorBasicAttribute();
		cli.execute(null, graphService);

		assertEquals("Edge: 2\nVertex: 3\nConnectiveDomain: 1\n", outContent.toString());
	}

}
