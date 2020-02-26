package local.happysixplus.backendcodeanalysis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import local.happysixplus.backendcodeanalysis.service.GraphService;
import local.happysixplus.backendcodeanalysis.vo.VertexVo;

@SpringBootTest
class DemoApplicationTests {
	@Autowired
	GraphService graphService;

	@Test
	void contextLoads() {
		graphService.loadCode("call_dependencies.txt");
	}

	@Test
	void getVertexNum() {
		graphService.loadCode("call_dependencies.txt");
		System.out.println(graphService.getVertexNum());
	}

	@Test
	void getEdgeNum() {
		graphService.loadCode("call_dependencies.txt");
		System.out.println(graphService.getEdgeNum());
	}

	@Test
	void getConnectiveDomainNum() {
		graphService.loadCode("call_dependencies.txt");
		System.out.println(graphService.getConnectiveDomainNum());
	}

	@Test
	void getConnectiveDomains() {
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
		graphService.loadCode("call_dependencies.txt");
		String from = "edu.ncsu.csc.itrust.risk.factors.AgeFactorTest:testRegularAge()";
		String to = "edu.ncsu.csc.itrust.risk.factors.AgeFactorTest:assertFalse(boolean)";
		graphService.getShortestPath(new VertexVo(from), new VertexVo(to));
	}
}
