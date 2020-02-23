package local.happysixplus.backendcodeanalysis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import local.happysixplus.backendcodeanalysis.service.GraphService;

@SpringBootTest
class DemoApplicationTests {
	@Autowired
	GraphService graphService;

	@Test
	void contextLoads() {
		graphService.loadCode("call_dependencies.txt");
	}
	@Test
	void getVertexNum(){
		graphService.loadCode("call_dependencies.txt");
		System.out.println(graphService.getVertexNum());
	}

	@Test
	void getEdgeNum(){
		graphService.loadCode("call_dependencies.txt");
		System.out.println(graphService.getEdgeNum());
	}
}
