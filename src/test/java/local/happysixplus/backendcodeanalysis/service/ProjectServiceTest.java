package local.happysixplus.backendcodeanalysis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import local.happysixplus.backendcodeanalysis.data.ConnectiveDomainData;
import local.happysixplus.backendcodeanalysis.vo.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import local.happysixplus.backendcodeanalysis.data.ProjectData;
import local.happysixplus.backendcodeanalysis.data.SubgraphData;
import local.happysixplus.backendcodeanalysis.po.ConnectiveDomainPo;
import local.happysixplus.backendcodeanalysis.po.EdgePo;
import local.happysixplus.backendcodeanalysis.po.ProjectPo;
import local.happysixplus.backendcodeanalysis.po.SubgraphPo;
import local.happysixplus.backendcodeanalysis.po.VertexPo;
import lombok.var;

@SpringBootTest
public class ProjectServiceTest {

    @MockBean
    ProjectData projectData;

    @MockBean
    SubgraphData subgraphData;

    @MockBean
    ConnectiveDomainData connectiveDomainData;

    @Autowired
    ProjectService service;

    static long getHashCodeForSubgraphStaticVo(SubgraphStaticVo vo) {
        return (int) vo.getId().hashCode() + (long) vo.getThreshold().hashCode()
                + (long) new HashSet<>(vo.getConnectiveDomains()).hashCode();
    }

    static long getHashCodeForSubgraphDynamicVo(SubgraphDynamicVo vo) {
        return vo.getId() + (long) vo.getName().hashCode() + (long) new HashSet<>(vo.getConnectiveDomains()).hashCode();
    }

    static long getHashCodeForSubgraphAllVo(SubgraphAllVo vo) {
        return getHashCodeForSubgraphStaticVo(vo.getStaticVo()) + getHashCodeForSubgraphDynamicVo(vo.getDynamicVo());
    }

    static long getHashCodeForProjectStaticVo(ProjectStaticVo vo) {
        long res = vo.getId().hashCode();
        res += new HashSet<>(vo.getEdges()).hashCode();
        res += new HashSet<>(vo.getVertices()).hashCode();
        res += vo.getSubgraphs().stream().map(ProjectServiceTest::getHashCodeForSubgraphStaticVo)
                .collect(Collectors.toSet()).hashCode();
        return res;
    }

    static long getHashCodeForProjectDynamicVo(ProjectDynamicVo vo) {
        long res = vo.getId().hashCode();
        res += new HashSet<>(vo.getEdges()).hashCode();
        res += new HashSet<>(vo.getVertices()).hashCode();
        res += vo.getSubgraphs().stream().map(ProjectServiceTest::getHashCodeForSubgraphDynamicVo)
                .collect(Collectors.toSet()).hashCode();
        return res;
    }

    static long getHashCodeForProjectAllVo(ProjectAllVo vo) {
        return getHashCodeForProjectStaticVo(vo.getStaticVo()) + getHashCodeForProjectDynamicVo(vo.getDynamicVo());
    }

    @Test
    public void testAddProject1() {
        // ProjectAllVo resVo = service.addProject("Faker",
        // "https://gitee.com/forsakenspirit/Linux", 2L);
        // assertEquals(resVo.getStaticVo().getVertices().size(), 9);
        // assertEquals(resVo.getStaticVo().getEdges().size(), 10);
        // assertEquals(resVo.getStaticVo().getSubgraphs().size(), 1);
        // assertEquals(resVo.getStaticVo().getSubgraphs().get(0).getConnectiveDomains().size(),
        // 1);
        // assertEquals(resVo.getStaticVo().getSubgraphs().get(0).getThreshold(), 0);
        // assertEquals(resVo.getDynamicVo().getProjectName(), "Faker");
        // assertEquals(resVo.getDynamicVo().getVertices().size(), 9);
        // assertEquals(resVo.getDynamicVo().getEdges().size(), 10);
        // assertEquals(resVo.getDynamicVo().getSubgraphs().size(), 1);
    }

    @Test
    public void testGetProject1() {
        // 打桩数据生成
        var projectPo = new ProjectPo(2L, 5L, "projC", null, null, null);
        // Vertices
        var v1 = new VertexPo(3L, "v1", "dian1", "a1", 0f, 0f);
        var v2 = new VertexPo(4L, "v2", "dian2", "a2", 0f, 0f);
        var vertices = new HashSet<VertexPo>(Arrays.asList(v1, v2));
        projectPo.setVertices(vertices);
        // Edges
        var e1 = new EdgePo(3L, v1, v2, 0.3d, "bian1");
        var edges = new HashSet<EdgePo>(Arrays.asList(e1));
        projectPo.setEdges(edges);
        // Subgraphs
        var connectiveDomain1 = new ConnectiveDomainPo(2L, vertices, edges, "acd1", "#CDBE70");
        var connectiveDomains1 = new HashSet<ConnectiveDomainPo>(Arrays.asList(connectiveDomain1));
        var defaultSubgraph = new SubgraphPo(4L, 0d, "default subgraph", connectiveDomains1);
        var subgraphs = new HashSet<SubgraphPo>(Arrays.asList(defaultSubgraph));
        projectPo.setSubgraphs(subgraphs);
        List<ProjectPo> dataRes = Arrays.asList(projectPo);

        // 打桩
        Mockito.when(projectData.findByUserId(5L)).thenReturn(dataRes);

        // 调用
        var resVo = service.getProjectAllByUserId(5L).get(0);

        // 验证数据生成
        var vs1 = new VertexStaticVo(3L, "v1", "dian1");
        var vs2 = new VertexStaticVo(4L, "v2", "dian2");
        var vss = Arrays.asList(vs1, vs2);
        var vd1 = new VertexDynamicVo(3L, "a1", 0f, 0f);
        var vd2 = new VertexDynamicVo(4L, "a2", 0f, 0f);
        var vds = Arrays.asList(vd1, vd2);
        var es1 = new EdgeStaticVo(3L, 3L, 4L, 0.3d);
        var ess = Arrays.asList(es1);
        var ed1 = new EdgeDynamicVo(3L, "bian1");
        var eds = Arrays.asList(ed1);
        var cs1vids = Arrays.asList(3L, 4L);
        var cs1eids = Arrays.asList(3L);
        var cs1 = new ConnectiveDomainStaticVo(2L, cs1vids, cs1eids);
        var css = Arrays.asList(cs1);
        var cd1 = new ConnectiveDomainDynamicVo(2L, "acd1", "#CDBE70");
        var cds = Arrays.asList(cd1);
        var subgs1 = new SubgraphStaticVo(4L, 0d, css);
        var subgss = Arrays.asList(subgs1);
        var subgd1 = new SubgraphDynamicVo(4L, "default subgraph", cds);
        var subgds = Arrays.asList(subgd1);
        var expectedDynamicVo = new ProjectDynamicVo(2L, "projC", vds, eds, subgds);
        var expectedStaticVo = new ProjectStaticVo(2L, vss, ess, subgss);
        var expectedAllVo = new ProjectAllVo(2L, expectedStaticVo, expectedDynamicVo);

        // 验证
        assertEquals(getHashCodeForProjectAllVo(expectedAllVo), getHashCodeForProjectAllVo(resVo));
    }

    @Test
    public void testRemoveProject1() {
        service.removeProject(2L);
        Mockito.verify(projectData).deleteById(2L);
    }

    @Test
    public void testUpdateProject1() {

        // 打桩数据生成
        var projectPo = new ProjectPo(2L, 5L, "projC", null, null, null);
        // Vertices
        var v1 = new VertexPo(3L, "v1", "dian1", "a1", 0f, 0f);
        var v2 = new VertexPo(4L, "v2", "dian2", "a2", 0f, 0f);
        var vertices = new HashSet<VertexPo>(Arrays.asList(v1, v2));
        projectPo.setVertices(vertices);
        // Edges
        var e1 = new EdgePo(3L, v1, v2, 0.3d, "bian1");
        var edges = new HashSet<EdgePo>(Arrays.asList(e1));
        projectPo.setEdges(edges);
        // Subgraphs
        var connectiveDomain1 = new ConnectiveDomainPo(2L, vertices, edges, "acd1", "#CDBE70");
        var connectiveDomains1 = new HashSet<ConnectiveDomainPo>(Arrays.asList(connectiveDomain1));
        var defaultSubgraph = new SubgraphPo(4L, 0d, "default subgraph", connectiveDomains1);
        var subgraphs = new HashSet<SubgraphPo>(Arrays.asList(defaultSubgraph));
        projectPo.setSubgraphs(subgraphs);

        // 打桩
        Mockito.when(projectData.findById(2L)).thenReturn(Optional.of(projectPo));

        // 输入数据
        var vd1 = new VertexDynamicVo(3L, "a11", 2f, 0f);
        var vd2 = new VertexDynamicVo(4L, "a22", 0f, 3f);
        var vds = Arrays.asList(vd1, vd2);
        var ed1 = new EdgeDynamicVo(3L, "bian11");
        var eds = Arrays.asList(ed1);
        var cd1 = new ConnectiveDomainDynamicVo(2L, "acd11", "#CDBE70");
        var cds = Arrays.asList(cd1);
        var subgd1 = new SubgraphDynamicVo(4L, "default subgraph", cds);
        var subgds = Arrays.asList(subgd1);
        var projectDynamicVo = new ProjectDynamicVo(2L, "SKTFaker", vds, eds, subgds);

        // 执行
        service.updateProject(projectDynamicVo);

        // 验证数据
        var expectedProjectPo = new ProjectPo(2L, 5L, "SKTFaker", null, null, null);
        var v11 = new VertexPo(3L, "v1", "dian1", "a11", 2f, 0f);
        var v21 = new VertexPo(4L, "v2", "dian2", "a22", 0f, 3f);
        var vertices1 = new HashSet<VertexPo>(Arrays.asList(v11, v21));
        expectedProjectPo.setVertices(vertices1);
        // Edges
        var e11 = new EdgePo(3L, v11, v21, 0.3d, "bian11");
        var edges1 = new HashSet<EdgePo>(Arrays.asList(e11));
        expectedProjectPo.setEdges(edges1);
        // Subgraphs
        var connectiveDomain11 = new ConnectiveDomainPo(2L, vertices1, edges1, "acd11", "#CDBE70");
        var connectiveDomains11 = new HashSet<ConnectiveDomainPo>(Arrays.asList(connectiveDomain11));
        var defaultSubgraph1 = new SubgraphPo(4L, 0d, "default subgraph", connectiveDomains11);
        var subgraphs1 = new HashSet<SubgraphPo>(Arrays.asList(defaultSubgraph1));
        expectedProjectPo.setSubgraphs(subgraphs1);

        Mockito.verify(projectData).findById(2L);
        Mockito.verify(projectData).save(expectedProjectPo);
    }

    @Test
    public void testGetSubgraph1() {
        // 打桩数据生成
        var projectPo = new ProjectPo(2L, 5L, "projC", null, null, null);
        // Vertices
        var v1 = new VertexPo(3L, "v1", "dian1", "a1", 0f, 0f);
        var v2 = new VertexPo(4L, "v2", "dian2", "a2", 0f, 0f);
        var vertices = new HashSet<VertexPo>(Arrays.asList(v1, v2));
        projectPo.setVertices(vertices);
        // Edges
        var e1 = new EdgePo(3L, v1, v2, 0.3d, "bian1");
        var edges = new HashSet<EdgePo>(Arrays.asList(e1));
        projectPo.setEdges(edges);
        // Subgraphs
        var connectiveDomain1 = new ConnectiveDomainPo(2L, vertices, edges, "acd1", "#CDBE70");
        var connectiveDomains1 = new HashSet<ConnectiveDomainPo>(Arrays.asList(connectiveDomain1));
        var defaultSubgraph = new SubgraphPo(4L, 0d, "default subgraph", connectiveDomains1);
        var subgraphs = new HashSet<SubgraphPo>(Arrays.asList(defaultSubgraph));
        projectPo.setSubgraphs(subgraphs);

        // 打桩
        Mockito.when(projectData.findById(2L)).thenReturn(Optional.of(projectPo));

        // 调用
        var resVos = service.getSubgraphAllByProjectId(2L);
        assertEquals(resVos.size(), 1);
        var resVo = resVos.get(0);
        // 验证数据生成
        var cs1vids = Arrays.asList(3L, 4L);
        var cs1eids = Arrays.asList(3L);
        var cs1 = new ConnectiveDomainStaticVo(2L, cs1vids, cs1eids);
        var css = Arrays.asList(cs1);
        var cd1 = new ConnectiveDomainDynamicVo(2L, "acd1", "#CDBE70");
        var cds = Arrays.asList(cd1);
        var subgs1 = new SubgraphStaticVo(4L, 0d, css);
        var subgd1 = new SubgraphDynamicVo(4L, "default subgraph", cds);
        var subgraphAllVo = new SubgraphAllVo(0L, subgs1, subgd1);

        // 验证
        assertEquals(getHashCodeForSubgraphAllVo(subgraphAllVo), getHashCodeForSubgraphAllVo(resVo));
    }

    @Test
    public void testAddSubgraph1() {
        // 数据生成
        // 原本的Project
        var projectPo = new ProjectPo(2L, 5L, "projC", null, null, null);
        var v1 = new VertexPo(3L, "v1", "dian1", "a1", 0f, 0f);
        var v2 = new VertexPo(4L, "v2", "dian2", "a2", 0f, 0f);
        var vertices = new HashSet<VertexPo>(Arrays.asList(v1, v2));
        projectPo.setVertices(vertices);
        var e1 = new EdgePo(3L, v1, v2, 0.3d, "bian1");
        var edges = new HashSet<EdgePo>(Arrays.asList(e1));
        projectPo.setEdges(edges);
        var connectiveDomain1 = new ConnectiveDomainPo(2L, vertices, edges, "acd1", "#CDBE70");
        var connectiveDomains1 = new HashSet<ConnectiveDomainPo>(Arrays.asList(connectiveDomain1));
        var defaultSubgraph = new SubgraphPo(10L, 0.3d, "default subgraph", connectiveDomains1);
        var subgraphs = new HashSet<SubgraphPo>(Arrays.asList(defaultSubgraph));
        projectPo.setSubgraphs(subgraphs);
        // 添加了子图的Project
        var connectiveDomains2 = new HashSet<ConnectiveDomainPo>(
                Arrays.asList(new ConnectiveDomainPo(15L, vertices, edges, "", "#000000")));
        var newSubgraph = new SubgraphPo(15L, 0.5d, "", connectiveDomains2);
        var newProjectPo = new ProjectPo(2L, 5L, "projC", vertices, edges,
                new HashSet<>(Arrays.asList(defaultSubgraph, newSubgraph)));

        // 打桩
        Mockito.when(projectData.findById(2L)).thenReturn(Optional.of(projectPo));
        Mockito.when(subgraphData.save(new SubgraphPo(null, 0.5d, "", new HashSet<>())))
                .thenReturn(new SubgraphPo(15L, 0.5d, "", new HashSet<>()));
        Mockito.when(projectData.save(Mockito.isA(ProjectPo.class))).thenReturn(newProjectPo);

        // 调用
        service.addSubgraph(2L, 0.5d);

        // 验证
        Mockito.verify(subgraphData).save(new SubgraphPo(null, 0.5d, "", new HashSet<>()));
        Mockito.verify(projectData).save(Mockito.isA(ProjectPo.class));
    }

    @Test
    public void testRemoveSubgraph1() {
        service.removeSubgraph(3L);
        Mockito.verify(subgraphData).deleteById(3L);
    }

    @Test
    public void testGetSubgraphAllByProjectId() {
        // 打桩数据生成
        var projectPo = new ProjectPo(2L, 5L, "projC", null, null, null);
        // Vertices
        var v1 = new VertexPo(3L, "v1", "dian1", "a1", 0f, 0f);
        var v2 = new VertexPo(4L, "v2", "dian2", "a2", 0f, 0f);
        var vertices = new HashSet<VertexPo>(Arrays.asList(v1, v2));
        projectPo.setVertices(vertices);
        // Edges
        var e1 = new EdgePo(3L, v1, v2, 0.3d, "bian1");
        var edges = new HashSet<EdgePo>(Arrays.asList(e1));
        projectPo.setEdges(edges);
        // Subgraphs
        var connectiveDomain1 = new ConnectiveDomainPo(2L, vertices, edges, "acd1", "#CDBE70");
        var connectiveDomains1 = new HashSet<ConnectiveDomainPo>(Arrays.asList(connectiveDomain1));
        var defaultSubgraph = new SubgraphPo(4L, 0d, "default subgraph", connectiveDomains1);
        var subgraphs = new HashSet<SubgraphPo>(Arrays.asList(defaultSubgraph));
        projectPo.setSubgraphs(subgraphs);
        // 打桩
        Mockito.when(projectData.findById(2L)).thenReturn(Optional.ofNullable(projectPo));

        // 调用
        var resVo = service.getSubgraphAllByProjectId(2L);
        assertEquals(resVo.size(), 1);
        var svo = resVo.get(0);
        // 验证数据生成
        var cs1vids = Arrays.asList(3L, 4L);
        var cs1eids = Arrays.asList(3L);
        var cs1 = new ConnectiveDomainStaticVo(2L, cs1vids, cs1eids);
        var css = Arrays.asList(cs1);
        var cd1 = new ConnectiveDomainDynamicVo(2L, "acd1", "#CDBE70");
        var cds = Arrays.asList(cd1);
        var subgs1 = new SubgraphStaticVo(4L, 0d, css);
        var subgd1 = new SubgraphDynamicVo(4L, "default subgraph", cds);
        var subga1 = new SubgraphAllVo(4L, subgs1, subgd1);

        // 验证
        assertEquals(getHashCodeForSubgraphAllVo(svo), getHashCodeForSubgraphAllVo(subga1));
    }

    @Test
    public void testGetOriginalGraphShortestPath() {
        // 打桩数据生成
        var projectPo = new ProjectPo(2L, 5L, "projC", null, null, null);
        // Vertices
        var v1 = new VertexPo(3L, "v1", "dian1", "a1", 0f, 0f);
        var v2 = new VertexPo(4L, "v2", "dian2", "a2", 0f, 0f);
        var vertices = new HashSet<VertexPo>(Arrays.asList(v1, v2));
        projectPo.setVertices(vertices);
        // Edges
        var e1 = new EdgePo(3L, v1, v2, 0.3d, "bian1");
        var edges = new HashSet<EdgePo>(Arrays.asList(e1));
        projectPo.setEdges(edges);
        // Subgraphs
        var connectiveDomain1 = new ConnectiveDomainPo(2L, vertices, edges, "acd1", "#CDBE70");
        var connectiveDomains1 = new HashSet<ConnectiveDomainPo>(Arrays.asList(connectiveDomain1));
        var defaultSubgraph = new SubgraphPo(4L, 0d, "default subgraph", connectiveDomains1);
        var subgraphs = new HashSet<SubgraphPo>(Arrays.asList(defaultSubgraph));
        projectPo.setSubgraphs(subgraphs);

        // 打桩
        Mockito.when(projectData.findById(2L)).thenReturn(Optional.ofNullable(projectPo));

        // 调用
        var resVo = service.getOriginalGraphShortestPath(2L, 3L, 4L);

        ArrayList<List<Long>> paths = new ArrayList<>();
        ArrayList<Long> path = new ArrayList<>();
        path.add(3L);
        paths.add(path);
        var pathVo = new PathVo(paths);
        // 验证
        assertEquals(pathVo, resVo);

    }

    @Test
    public void testGetSimilarFunction() {
        // 打桩数据生成
        var projectPo = new ProjectPo(2L, 5L, "projC", null, null, null);
        // Vertices
        var v1 = new VertexPo(3L, "v1", "dian1", "a1", 0f, 0f);
        var v2 = new VertexPo(4L, "v2", "dian2", "a2", 0f, 0f);
        var vertices = new HashSet<VertexPo>(Arrays.asList(v1, v2));
        projectPo.setVertices(vertices);
        // Edges
        var e1 = new EdgePo(3L, v1, v2, 0.3d, "bian1");
        var edges = new HashSet<EdgePo>(Arrays.asList(e1));
        projectPo.setEdges(edges);
        // Subgraphs
        var connectiveDomain1 = new ConnectiveDomainPo(2L, vertices, edges, "acd1", "#CDBE70");
        var connectiveDomains1 = new HashSet<ConnectiveDomainPo>(Arrays.asList(connectiveDomain1));
        var defaultSubgraph = new SubgraphPo(4L, 0d, "default subgraph", connectiveDomains1);
        var subgraphs = new HashSet<SubgraphPo>(Arrays.asList(defaultSubgraph));
        projectPo.setSubgraphs(subgraphs);

        // 打桩
        Mockito.when(projectData.findById(2L)).thenReturn(Optional.ofNullable(projectPo));

        List<String> strs = new ArrayList<>();
        strs.add("v1");
        strs.add("v2");

        var res = service.getSimilarFunction(2L, "v");
        assertEquals(new HashSet<>(res), new HashSet<>(strs));
    }
}