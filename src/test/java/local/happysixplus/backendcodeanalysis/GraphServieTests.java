package local.happysixplus.backendcodeanalysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import local.happysixplus.backendcodeanalysis.service.GraphService;
import local.happysixplus.backendcodeanalysis.vo.PathVo;
import local.happysixplus.backendcodeanalysis.vo.VertexVo;
import local.happysixplus.backendcodeanalysis.vo.EdgeVo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import java.io.*;

@SpringBootTest
class GraphServieTests {
	@Autowired
	GraphService graphService;

	// 输出流
	static ByteArrayOutputStream outContent;
	// 系统原控制台输出流
	static PrintStream console = null;

	@BeforeAll
	public static void setUp() {

		console = System.out;
		// 把标准输出定向至ByteArrayOutputStream中
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

	}

	@AfterAll // 后处理
	public static void tearDown() {

		System.setOut(console);
	}

	@BeforeEach
	public void clearOutContent() {
		outContent.reset();
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
		assertEquals(1, graphService.getConnectiveDomains().size());
	}

	@Test
	void getConnectiveDomains() {
		graphService.loadCode("testcases/testcase1/test_case1.txt");
		assertEquals(1, graphService.getConnectiveDomains().size());
	}

	@Test
	void setClosenessMin() {
		graphService.loadCode("testcases/testcase1/test_case1.txt");
		graphService.setClosenessMin(1.1);
		assertEquals(0, graphService.getConnectiveDomainsWithClosenessMin().size());
		graphService.setClosenessMin(1);
		assertEquals(0, graphService.getConnectiveDomainsWithClosenessMin().size());
		graphService.setClosenessMin(0.5);
		assertEquals(1, graphService.getConnectiveDomainsWithClosenessMin().size());
	}

	@Test
	void getConnectiveDomainsWithClosenessMin() {
		graphService.loadCode("testcases/testcase1/test_case1.txt");
		graphService.setClosenessMin(0.05);
		graphService.getConnectiveDomainsWithClosenessMin();
	}

	@Test
	void getShortestPath() {

		graphService.loadCode("testcases/testcase11/test_case11.txt");
		String from = "edu.ncsu.csc.itrust.risk.factors.AgeFactorTest:testRegularAge()";
		String to = "edu.ncsu.csc.itrust.risk.factors.AgeFactorTest:assertFalse(boolean)";
		PathVo result = graphService.getShortestPath(new VertexVo(from), new VertexVo(to));
		try {
			printPath(result);
			testEqualFromFile("testcases/testcase11/expected11.txt", outContent.toString());
		} catch (Exception e) {
			e.printStackTrace();
			assert (false);
		}

	}

	@Test
	void getShortestPath2() {
		outContent.reset();
		graphService.loadCode("testcases/testcase12/test_data12.txt");
		String from = "a()";
		String to = "l()";
		PathVo result = graphService.getShortestPath(new VertexVo(from), new VertexVo(to));

		try {
			printPath(result);
			testEqualFromFile("testcases/testcase12/expected12.txt", outContent.toString());
		} catch (Exception e) {
			
			e.printStackTrace();
			assert(false);
		}
	}

	@Test
	void getShortestPath3() {
		outContent.reset();
		graphService.loadCode("testcases/testcase13/test_data13.txt");
		String from = "a()";
		String to = "l()";
		PathVo result = graphService.getShortestPath(new VertexVo(from), new VertexVo(to));
		try{
			printPath(result);
			testEqualFromFile("testcases/testcase13/expected13.txt", outContent.toString());
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	@Test
	void getShortestPath4(){
		outContent.reset();
		graphService.loadCode("testcases/testcase14/test_data14.txt");
		String from = "a()";
		String to = "f()";
		PathVo result = graphService.getShortestPath(new VertexVo(from), new VertexVo(to));
		try{
			printPath(result);
			testEqualFromFile("testcases/testcase14/expected14.txt", outContent.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	/**
	 * 从文件读取并判断是否相同
	 * @param FileName
	 * @param result
	 * @throws Exception
	 */
	void testEqualFromFile(String FileName, String result) throws Exception{
		try{
			InputStream is = new FileInputStream(new File(FileName));
			byte[] bytes = new byte[is.available()];
			is.read(bytes);
			is.close();
			String s = new String(bytes);
			assertEquals(new String(bytes), result);
		}catch(Exception e){
			throw e;
		}
	}

	/**
	 * 打印一个路径
	 */
	void printPath(PathVo paths) throws Exception{
		//assertEquals(false, true);
		System.out.println("path num: " + paths.getPaths().size());
		for(List<EdgeVo> path: paths.getPaths()){
			for(EdgeVo edge : path){
				System.out.printf(edge.getFrom().getFunctionName() + "--" + edge.getCloseness() + "-->");
			}
			System.out.println(path.get(path.size() - 1).getTo().getFunctionName());
			
		}

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
			throw e;
		}
		
	}

}
