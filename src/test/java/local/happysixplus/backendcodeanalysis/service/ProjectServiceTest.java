package local.happysixplus.backendcodeanalysis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import local.happysixplus.backendcodeanalysis.data.ProjectData;
import local.happysixplus.backendcodeanalysis.data.SubgraphData;
import local.happysixplus.backendcodeanalysis.po.ConnectiveDomainPo;
import local.happysixplus.backendcodeanalysis.po.EdgePo;
import local.happysixplus.backendcodeanalysis.po.ProjectPo;
import local.happysixplus.backendcodeanalysis.po.SubgraphPo;
import local.happysixplus.backendcodeanalysis.po.VertexPo;
import local.happysixplus.backendcodeanalysis.vo.ConnectiveDomainDynamicVo;
import local.happysixplus.backendcodeanalysis.vo.ConnectiveDomainStaticVo;
import local.happysixplus.backendcodeanalysis.vo.EdgeDynamicVo;
import local.happysixplus.backendcodeanalysis.vo.EdgeStaticVo;
import local.happysixplus.backendcodeanalysis.vo.PathVo;
import local.happysixplus.backendcodeanalysis.vo.ProjectAllVo;
import local.happysixplus.backendcodeanalysis.vo.ProjectDynamicVo;
import local.happysixplus.backendcodeanalysis.vo.ProjectStaticVo;
import local.happysixplus.backendcodeanalysis.vo.SubgraphAllVo;
import local.happysixplus.backendcodeanalysis.vo.SubgraphDynamicVo;
import local.happysixplus.backendcodeanalysis.vo.SubgraphStaticVo;
import local.happysixplus.backendcodeanalysis.vo.VertexDynamicVo;
import local.happysixplus.backendcodeanalysis.vo.VertexStaticVo;
import lombok.var;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectServiceTest {

    @MockBean
    ProjectData projectData;

    @MockBean
    SubgraphData subgraphData;

    @Autowired
    ProjectService service;

    @Test
    public void testAddProject1() {
        // TODO: 确定项目结构，然后仿照get写打桩
        // Mockito.when(projectData.save(new ProjectPo())).thenReturn(new ProjectPo());
        // Mockito.verify(projectData).save(entity);
        var vo = service.addProject("Linux", "https://gitee.com/forsakenspirit/Linux", 1L);
    }

    @Test
    public void testGetProject1() {

        // 打桩数据生成
        var projectPo = new ProjectPo(2L, 5L, "projC", null, null, null);
        // Vertices
        var v1 = new VertexPo(3L, "v1", "dian1", "a1", 0f, 0f);
        var v2 = new VertexPo(4L, "v2", "dian2", "a2", 0f, 0f);
        var vertices = new HashSet<VertexPo>();
        vertices.add(v1);
        vertices.add(v2);
        projectPo.setVertices(vertices);
        // Edges
        var e1 = new EdgePo(3L, v1, v2, 0.3d, "bian1");
        var edges = new HashSet<EdgePo>();
        edges.add(e1);
        projectPo.setEdges(edges);
        // Subgraphs
        var connectiveDomain1 = new ConnectiveDomainPo(2L, vertices, edges, "acd1", "#CDBE70");
        var connectiveDomains1 = new HashSet<ConnectiveDomainPo>();
        connectiveDomains1.add(connectiveDomain1);
        var defaultSubgraph = new SubgraphPo(4L, 0d, "default subgraph", connectiveDomains1);
        var subgraphs = new HashSet<SubgraphPo>();
        subgraphs.add(defaultSubgraph);
        projectPo.setSubgraphs(subgraphs);
        List<ProjectPo> dataRes = new ArrayList<>();
        dataRes.add(projectPo);

        // 打桩
        Mockito.when(projectData.findByUserId(5L)).thenReturn(dataRes);

        // 调用
        var resVo = service.getProjectAllByUserId(5L);

        // 验证数据生成
        var vs1 = new VertexStaticVo(3L, "v1", "dian1");
        var vs2 = new VertexStaticVo(4L, "v2", "dian2");
        var vss = new ArrayList<VertexStaticVo>();
        vss.add(vs1);
        vss.add(vs2);
        var vd1 = new VertexDynamicVo(3L, "a1", 0f, 0f);
        var vd2 = new VertexDynamicVo(4L, "a2", 0f, 0f);
        var vds = new ArrayList<VertexDynamicVo>();
        vds.add(vd1);
        vds.add(vd2);
        var es1 = new EdgeStaticVo(3L, 3L, 4L, 0.3d);
        var ess = new ArrayList<EdgeStaticVo>();
        ess.add(es1);
        var ed1 = new EdgeDynamicVo(3L, "bian1");
        var eds = new ArrayList<EdgeDynamicVo>();
        eds.add(ed1);
        var cs1vids = new ArrayList<Long>();
        cs1vids.add(3L);
        cs1vids.add(4L);
        var cs1eids = new ArrayList<Long>();
        cs1eids.add(3L);
        var cs1 = new ConnectiveDomainStaticVo(2L, cs1vids, cs1eids);
        var css = new ArrayList<ConnectiveDomainStaticVo>();
        css.add(cs1);
        var cd1 = new ConnectiveDomainDynamicVo(2L, "acd1", "#CDBE70");
        var cds = new ArrayList<ConnectiveDomainDynamicVo>();
        cds.add(cd1);
        var subgs1 = new SubgraphStaticVo(4L, 0d, css);
        var subgss = new ArrayList<SubgraphStaticVo>();
        subgss.add(subgs1);
        var subgd1 = new SubgraphDynamicVo(4L, "default subgraph", cds);
        var subgds = new ArrayList<SubgraphDynamicVo>();
        subgds.add(subgd1);
        var projectDynamicVo = new ProjectDynamicVo(2L, "projC", vds, eds, subgds);
        var projectStaticVo = new ProjectStaticVo(2L, vss, ess, subgss);
        var projectAllVo = new ProjectAllVo(2L, projectStaticVo, projectDynamicVo);

        // 验证
        assertEquals(resVo, projectAllVo);
    }

    // @Test
    // public void testProject() {
    // ProjectAllVo vo = service.addProject("Linux",
    // "https://gitee.com/forsakenspirit/Linux", 1);

    // vo.getDynamicVo().setProjectName("SKTFaker's Linux");
    // service.updateProject(vo.getDynamicVo());

    // List<ProjectAllVo> projectAllVos = service.getProjectAllByUserId(1L);
    // ProjectAllVo projectAllVo = projectAllVos.get(0);

    // List<String> funcNames = service.getSimilarFunction(projectAllVo.getId(),
    // "B");

    // service.addSubgraph(projectAllVo.getId(), 0.5);

    // List<SubgraphAllVo> subgraphAllByProjectId =
    // service.getSubgraphAllByProjectId(projectAllVo.getId());

    // SubgraphAllVo subgraphAllVo = subgraphAllByProjectId.get(0);
    // subgraphAllVo.getDynamicVo().setName("?????????");

    // service.updateSubGraph(projectAllVo.getId(), subgraphAllVo.getDynamicVo());

    // service.removeSubgraph(subgraphAllVo.getId());

    // PathVo pathVo = service.getOriginalGraphShortestPath(projectAllVo.getId(),
    // 0L, 3L);

    // service.removeProject(projectAllVo.getId());

    // }
}