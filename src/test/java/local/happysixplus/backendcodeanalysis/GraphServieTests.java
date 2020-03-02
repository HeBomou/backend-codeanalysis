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
import java.util.ArrayList;

import java.io.*;



@SpringBootTest
class GraphServieTests {
	@Autowired
	GraphService graphService;

	//输出流
	static ByteArrayOutputStream outContent;
	//系统原控制台输出流
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
		graphService.loadCode("testcases/testcase1/test_case1.txt");
	}

	@Test
	void contextLoads2() {
		graphService.loadCode("testcases/testcase2/test_case2.txt");
	}

	@Test
	void contextLoads3() {
		graphService.loadCode("hbmnb");
	}

	@Test
	void getVertexNum() {
		graphService.loadCode("testcases/testcase1/test_case1.txt");
		System.out.println(graphService.getVertexNum());
		assertEquals(3, graphService.getVertexNum());
	}

	@Test
	void getEdgeNum() {
		graphService.loadCode("testcases/testcase3/test_case3.txt");
		assertEquals(6, graphService.getEdgeNum());
	}

	@Test
	void getEdgeNum2() {
		graphService.loadCode("testcases/testcase2/test_case2.txt");
		assertEquals(0, graphService.getEdgeNum());
	}

	@Test
	void getConnectiveDomainNum() {
		graphService.loadCode("testcases/testcase1/test_case1.txt");
		assertEquals(1, graphService.getConnectiveDomainNum());
	}

	@Test
	void getConnectiveDomains() {
		graphService.loadCode("testcases/testcase1/test_case1.txt");
		assertEquals(1, graphService.getConnectiveDomains().size());
	}

	@Test
	void setClosenessMin() {
		graphService.loadCode("testcases/testcase1/test_case1.txt");
		graphService.setClosenessMin(1);
		assertEquals(3, graphService.getConnectiveDomainNum());
		graphService.setClosenessMin(0.5);
		assertEquals(1, graphService.getConnectiveDomainNum());
	}

	@Test
	void getConnectiveDomainsWithClosenessMin() {
		graphService.loadCode("testcases/testcase1/test_case1.txt");
		graphService.setClosenessMin(0.05);
		graphService.getConnectiveDomainsWithClosenessMin();
	}

	@Test
	void getShortestPath() {
		graphService.loadCode("testcases/testcase1/test_case1.txt");
		String from = "edu.ncsu.csc.itrust.risk.factors.AgeFactorTest:testRegularAge()";
		String to = "edu.ncsu.csc.itrust.risk.factors.AgeFactorTest:assertFalse(boolean)";
		PathVo result = graphService.getShortestPath(new VertexVo(from), new VertexVo(to));
		assertEquals(1, result.getPathNum());
		List<EdgeVo> path = result.getPath();

		//期待的路径顶点
		String[] expectedName = {
			"edu.ncsu.csc.itrust.risk.factors.AgeFactorTest:testRegularAge()", 
			"edu.ncsu.csc.itrust.risk.factors.AgeFactorTest:assertFalse(boolean)"
		};
		double[] exptectedClossness = {
			0.6666666666666666
		};
		List<EdgeVo> expected = new ArrayList<EdgeVo>();
		for(int i = 0; i < expectedName.length - 1; i++){
			expected.add(new EdgeVo(new VertexVo(expectedName[i]), new VertexVo(expectedName[i + 1]), exptectedClossness[i]));
		}
		assertEquals(expected, path);
		// assertEquals(expected.size(), path.size());
		// for(int i = 0; i < expected.size(); i++){
			// assertEquals(expected.get(i).getFrom(), path.get(i).getFrom());
			// assertEquals(expected.get(i).getTo(), path.get(i).getTo());
		// }
		
	}

	@Test
	void getShortestPath2(){
		outContent.reset();
		graphService.loadCode("testcases/testcase7/test_case7.txt");
		String from = "a()";
		String to = "l()";
		PathVo result = graphService.getShortestPath(new VertexVo(from), new VertexVo(to));
		printPath(result);
		 //       输出到文件
		//  try{
        // File file =new File("test_appendfile.txt");
 
        // if(!file.exists()){
        // 	file.createNewFile();
        // }
 
        // //使用true，即进行append file 
 
        // FileWriter fileWritter = new FileWriter(file.getName(), true);
 
 
        // fileWritter.write(outContent.toString());
 
		// fileWritter.close();
		// }catch(Exception e){
		// 	e.printStackTrace();
		// }
		assertEquals("path num: 7\na()--0.6666666666666666-->c()--0.5-->h()--1.0-->i()--0.6666666666666666-->l()\n", outContent.toString());

	}

	@Test
	void getShortestPath3(){
		outContent.reset();
		graphService.loadCode("testcases/testcase8/test_case8.txt");
		String from = "a()";
		String to = "l()";
		PathVo result = graphService.getShortestPath(new VertexVo(from), new VertexVo(to));
		printPath(result);
		 //       输出到文件
		 try{
        File file =new File("test_appendfile.txt");
 
        if(!file.exists()){
        	file.createNewFile();
        }
 
        //使用true，即进行append file 
 
        FileWriter fileWritter = new FileWriter(file.getName(), false);
 
 
        fileWritter.write(outContent.toString());
 
		fileWritter.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		assertEquals("path num: 10\na()--0.6666666666666666-->c()--0.5-->e()--0.6666666666666666-->g()--0.5-->k()--0.5-->l()\n", outContent.toString());

	}

	@Test
	void CLICommandExecutorBasicAttribute() {

		outContent.reset();
		graphService.loadCode("testcases/testcase1/test_case1.txt");
		CLICommandExecutor cli = new CLICommandExecutorBasicAttribute();
		cli.execute(null, graphService);

		assertEquals("Edge: 2\nVertex: 3\nConnectiveDomain: 1\n", outContent.toString());
	}

	/**
	 * 打印一个路径
	 */
	void printPath(PathVo path){
		System.out.println("path num: " + path.getPathNum());
		for(EdgeVo edge: path.getPath()){
			System.out.printf(edge.getFrom().getFunctionName() + "--" + edge.getCloseness() + "-->");
		}
		System.out.println(path.getPath().get(path.getPath().size() - 1).getTo().getFunctionName());


	}

}
