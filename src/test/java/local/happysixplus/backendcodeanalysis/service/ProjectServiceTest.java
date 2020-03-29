package local.happysixplus.backendcodeanalysis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import local.happysixplus.backendcodeanalysis.vo.PathVo;
import local.happysixplus.backendcodeanalysis.vo.ProjectAllVo;
import local.happysixplus.backendcodeanalysis.vo.SubgraphAllVo;
import lombok.var;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectServiceTest {

    @Autowired
    ProjectService service;

    @Test
    public void testAddProject() {
        var vo = service.addProject("Linux", "https://gitee.com/forsakenspirit/Linux", 1L);
        var newVo = service.getProjectAllByUserId(1L).get(0);
        // assertEquals(vo.getId(), newVo.getId());
        // assertEquals(vo.getDynamicVo(), newVo.getDynamicVo());
    }

    @Test
    public void testProjectService() {
        ProjectAllVo vo = service.addProject("Linux", "https://gitee.com/forsakenspirit/Linux", 1);

        vo.getDynamicVo().setProjectName("SKTFaker's Linux");
        service.updateProject(vo.getDynamicVo());

        List<ProjectAllVo> projectAllVos = service.getProjectAllByUserId(1L);
        ProjectAllVo projectAllVo = projectAllVos.get(0);

        List<String> funcNames = service.getSimilarFunction(projectAllVo.getId(), "B");

        service.addSubgraph(projectAllVo.getId(), 0.5);

        List<SubgraphAllVo> subgraphAllByProjectId = service.getSubgraphAllByProjectId(projectAllVo.getId());

        SubgraphAllVo subgraphAllVo = subgraphAllByProjectId.get(0);
        subgraphAllVo.getDynamicVo().setName("?????????");

        service.updateSubGraph(projectAllVo.getId(), subgraphAllVo.getDynamicVo());

        service.removeSubgraph(subgraphAllVo.getId());

        PathVo pathVo = service.getOriginalGraphShortestPath(projectAllVo.getId(), 0L, 3L);

        service.removeProject(projectAllVo.getId());

    }
}