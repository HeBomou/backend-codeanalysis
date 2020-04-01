package local.happysixplus.backendcodeanalysis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import local.happysixplus.backendcodeanalysis.data.ConnectiveDomainData;
import local.happysixplus.backendcodeanalysis.data.ConnectiveDomainDynamicData;
import local.happysixplus.backendcodeanalysis.data.EdgeDynamicData;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import local.happysixplus.backendcodeanalysis.data.ProjectData;
import local.happysixplus.backendcodeanalysis.data.ProjectDynamicData;
import local.happysixplus.backendcodeanalysis.data.SubgraphData;
import local.happysixplus.backendcodeanalysis.data.SubgraphDynamicData;
import local.happysixplus.backendcodeanalysis.data.VertexDynamicData;
import local.happysixplus.backendcodeanalysis.po.ConnectiveDomainDynamicPo;
import local.happysixplus.backendcodeanalysis.po.ConnectiveDomainPo;
import local.happysixplus.backendcodeanalysis.po.EdgeDynamicPo;
import local.happysixplus.backendcodeanalysis.po.EdgePo;
import local.happysixplus.backendcodeanalysis.po.ProjectDynamicPo;
import local.happysixplus.backendcodeanalysis.po.ProjectPo;
import local.happysixplus.backendcodeanalysis.po.SubgraphDynamicPo;
import local.happysixplus.backendcodeanalysis.po.SubgraphPo;
import local.happysixplus.backendcodeanalysis.po.VertexDynamicPo;
import local.happysixplus.backendcodeanalysis.po.VertexPo;
import local.happysixplus.backendcodeanalysis.vo.ConnectiveDomainAllVo;
import local.happysixplus.backendcodeanalysis.vo.ConnectiveDomainDynamicVo;
import local.happysixplus.backendcodeanalysis.vo.EdgeAllVo;
import local.happysixplus.backendcodeanalysis.vo.EdgeDynamicVo;
import local.happysixplus.backendcodeanalysis.vo.ProjectAllVo;
import local.happysixplus.backendcodeanalysis.vo.ProjectDynamicVo;
import local.happysixplus.backendcodeanalysis.vo.SubgraphAllVo;
import local.happysixplus.backendcodeanalysis.vo.SubgraphDynamicVo;
import local.happysixplus.backendcodeanalysis.vo.VertexAllVo;
import local.happysixplus.backendcodeanalysis.vo.VertexDynamicVo;
import lombok.var;

@SpringBootTest
public class ProjectServiceTest {

    @MockBean
    ProjectData projectData;

    @MockBean
    SubgraphData subgraphData;

    @MockBean
    ConnectiveDomainData connectiveDomainData;

    @MockBean
    ProjectDynamicData projectDynamicData;

    @MockBean
    SubgraphDynamicData subgraphDynamicData;

    @MockBean
    ConnectiveDomainDynamicData connectiveDomainDynamicData;

    @MockBean
    EdgeDynamicData edgeDynamicData;

    @MockBean
    VertexDynamicData vertexDynamicData;

    @Autowired
    ProjectService service;

    static long getHashCodeForSubgraphAllVo(SubgraphAllVo vo) {
        return vo.getId().hashCode() + vo.getThreshold().hashCode()
                + getHashCodeForSubgraphDynamicVo(vo.getDynamicVo());
    }

    static long getHashCodeForSubgraphDynamicVo(SubgraphDynamicVo vo) {
        return vo.getId() + (long) vo.getName().hashCode();
    }

    static long getHashCodeForProjectAllVo(ProjectAllVo vo) {
        long res = vo.getId().hashCode();
        res += new HashSet<>(vo.getEdges()).hashCode();
        res += new HashSet<>(vo.getVertices()).hashCode();
        res += vo.getSubgraphs().stream().map(ProjectServiceTest::getHashCodeForSubgraphAllVo)
                .collect(Collectors.toSet()).hashCode();
        res += getHashCodeForProjectDynamicVo(vo.getDynamicVo());
        return res;
    }

    static long getHashCodeForProjectDynamicVo(ProjectDynamicVo vo) {
        return vo.getId().hashCode() + vo.getProjectName().hashCode();
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
        var po = new ProjectPo(2L, 233L, null, null);
        // Vertices
        var v1 = new VertexPo(3L, "v1", "dian1()");
        var v2 = new VertexPo(4L, "v2", "dian2()");
        var vertices = new HashSet<VertexPo>(Arrays.asList(v1, v2));
        po.setVertices(vertices);
        // Edges
        var e1 = new EdgePo(3L, v1, v2, 0.3d);
        var edges = new HashSet<EdgePo>(Arrays.asList(e1));
        po.setEdges(edges);
        // Subgraphs
        var connectiveDomain1 = new ConnectiveDomainPo(2L, Arrays.asList(3L, 4L), Arrays.asList(3L));
        var connectiveDomains1 = new HashSet<ConnectiveDomainPo>(Arrays.asList(connectiveDomain1));
        var defaultSubgraph = new SubgraphPo(4L, 2L, 0d, connectiveDomains1);
        // Dynamic Pos
        var dV1 = new VertexDynamicPo(3L, 2L, "a1", 0f, 0f);
        var dV2 = new VertexDynamicPo(4L, 2L, "a2", 0f, 0f);
        var dE1 = new EdgeDynamicPo(3L, 2L, "abian 1");
        var dConnectiveDomain1 = new ConnectiveDomainDynamicPo(2L, 2L, "acd 1", "#CDBE70");
        var dDefaultSubgraph = new SubgraphDynamicPo(4L, 2L, "Default subgraph");
        var dPo = new ProjectDynamicPo(2L, 5L, "projC");

        // 打桩
        Mockito.when(projectData.findById(2L)).thenReturn(Optional.of(po));
        Mockito.when(subgraphData.findByProjectId(2L)).thenReturn(Arrays.asList(defaultSubgraph));
        Mockito.when(projectDynamicData.findById(2L)).thenReturn(Optional.of(dPo));
        Mockito.when(subgraphDynamicData.findByProjectId(2L)).thenReturn(Arrays.asList(dDefaultSubgraph));
        Mockito.when(connectiveDomainDynamicData.findByProjectId(2L)).thenReturn(Arrays.asList(dConnectiveDomain1));
        Mockito.when(edgeDynamicData.findByProjectId(2L)).thenReturn(Arrays.asList(dE1));
        Mockito.when(vertexDynamicData.findByProjectId(2L)).thenReturn(Arrays.asList(dV1, dV2));

        // 调用
        var resVo = service.getProjectAllById(2L);

        // 验证数据生成
        var expV1 = new VertexAllVo(3L, "v1", "dian1()", new VertexDynamicVo(3L, "a1", 0f, 0f));
        var expV2 = new VertexAllVo(4L, "v2", "dian2()", new VertexDynamicVo(4L, "a2", 0f, 0f));
        var expE1 = new EdgeAllVo(3L, 3L, 4L, 0.3d, new EdgeDynamicVo(3L, "abian 1"));
        var expC1 = new ConnectiveDomainAllVo(2L, Arrays.asList(3L, 4L), Arrays.asList(3L),
                new ConnectiveDomainDynamicVo(2L, "acd1", "#CDBE70"));
        var expS1 = new SubgraphAllVo(4L, 0d, Arrays.asList(expC1), new SubgraphDynamicVo(4L, "Default subgraph"));
        var expP = new ProjectAllVo(2L, Arrays.asList(expV1, expV2), Arrays.asList(expE1), Arrays.asList(expS1),
                new ProjectDynamicVo(2L, "projC"));

        // 验证
        assertEquals(getHashCodeForProjectAllVo(expP), getHashCodeForProjectAllVo(resVo));
    }

    @Test
    public void testRemoveProject1() {
        service.removeProject(2L);
        Mockito.verify(projectData).deleteById(2L);
        Mockito.verify(projectDynamicData).deleteById(2L);
    }

    @Test
    public void testUpdateProjectDynamic1() {

        // 打桩数据生成
        var originDPo = new ProjectDynamicPo(256L, 15L, "Hello");
        var dPoToSave = new ProjectDynamicPo(256L, 15L, "Bye");

        // 打桩
        Mockito.when(projectDynamicData.findById(256L)).thenReturn(Optional.of(originDPo));
        Mockito.when(projectDynamicData.save(dPoToSave)).thenReturn(dPoToSave);

        // 输入数据
        var vo = new ProjectDynamicVo(256L, "Bye");

        // 执行
        service.updateProjectDynamic(256L, vo);

        // 验证
        Mockito.verify(projectDynamicData).findById(256L);
        Mockito.verify(projectDynamicData).save(dPoToSave);
    }

    // @Test
    // public void testAddSubgraph1() {
    // // 数据生成
    // // 原本的Project
    // var projectPo = new ProjectPo(2L, 5L, "projC", null, null, null);
    // var v1 = new VertexPo(3L, "v1", "dian1", "a1", 0f, 0f);
    // var v2 = new VertexPo(4L, "v2", "dian2", "a2", 0f, 0f);
    // var vertices = new HashSet<VertexPo>(Arrays.asList(v1, v2));
    // projectPo.setVertices(vertices);
    // var e1 = new EdgePo(3L, v1, v2, 0.3d, "bian1");
    // var edges = new HashSet<EdgePo>(Arrays.asList(e1));
    // projectPo.setEdges(edges);
    // var connectiveDomain1 = new ConnectiveDomainPo(2L, vertices, edges, "acd1",
    // "#CDBE70");
    // var connectiveDomains1 = new
    // HashSet<ConnectiveDomainPo>(Arrays.asList(connectiveDomain1));
    // var defaultSubgraph = new SubgraphPo(10L, 0.3d, "default subgraph",
    // connectiveDomains1);
    // var subgraphs = new HashSet<SubgraphPo>(Arrays.asList(defaultSubgraph));
    // projectPo.setSubgraphs(subgraphs);
    // // 添加了子图的Project
    // var connectiveDomains2 = new HashSet<ConnectiveDomainPo>(
    // Arrays.asList(new ConnectiveDomainPo(15L, vertices, edges, "", "#000000")));
    // var newSubgraph = new SubgraphPo(15L, 0.5d, "", connectiveDomains2);
    // var newProjectPo = new ProjectPo(2L, 5L, "projC", vertices, edges,
    // new HashSet<>(Arrays.asList(defaultSubgraph, newSubgraph)));

    // // 打桩
    // Mockito.when(projectData.findById(2L)).thenReturn(Optional.of(projectPo));
    // Mockito.when(subgraphData.save(new SubgraphPo(null, 0.5d, "", new
    // HashSet<>())))
    // .thenReturn(new SubgraphPo(15L, 0.5d, "", new HashSet<>()));
    // Mockito.when(projectData.save(Mockito.isA(ProjectPo.class))).thenReturn(newProjectPo);

    // // 调用
    // service.addSubgraph(2L, 0.5d);

    // // 验证
    // Mockito.verify(subgraphData).save(new SubgraphPo(null, 0.5d, "", new
    // HashSet<>()));
    // Mockito.verify(projectData).save(Mockito.isA(ProjectPo.class));
    // }

    // @Test
    // public void testRemoveSubgraph1() {
    // service.removeSubgraph(3L);
    // Mockito.verify(subgraphData).deleteById(3L);
    // }

    // @Test
    // public void testGetSubgraphAllByProjectId() {
    // // 打桩数据生成
    // var projectPo = new ProjectPo(2L, 5L, "projC", null, null, null);
    // // Vertices
    // var v1 = new VertexPo(3L, "v1", "dian1", "a1", 0f, 0f);
    // var v2 = new VertexPo(4L, "v2", "dian2", "a2", 0f, 0f);
    // var vertices = new HashSet<VertexPo>(Arrays.asList(v1, v2));
    // projectPo.setVertices(vertices);
    // // Edges
    // var e1 = new EdgePo(3L, v1, v2, 0.3d, "bian1");
    // var edges = new HashSet<EdgePo>(Arrays.asList(e1));
    // projectPo.setEdges(edges);
    // // Subgraphs
    // var connectiveDomain1 = new ConnectiveDomainPo(2L, vertices, edges, "acd1",
    // "#CDBE70");
    // var connectiveDomains1 = new
    // HashSet<ConnectiveDomainPo>(Arrays.asList(connectiveDomain1));
    // var defaultSubgraph = new SubgraphPo(4L, 0d, "default subgraph",
    // connectiveDomains1);
    // var subgraphs = new HashSet<SubgraphPo>(Arrays.asList(defaultSubgraph));
    // projectPo.setSubgraphs(subgraphs);
    // // 打桩
    // Mockito.when(projectData.findById(2L)).thenReturn(Optional.ofNullable(projectPo));

    // // 调用
    // var resVo = service.getSubgraphAllByProjectId(2L);
    // assertEquals(resVo.size(), 1);
    // var svo = resVo.get(0);
    // // 验证数据生成
    // var cs1vids = Arrays.asList(3L, 4L);
    // var cs1eids = Arrays.asList(3L);
    // var cs1 = new ConnectiveDomainStaticVo(2L, cs1vids, cs1eids);
    // var css = Arrays.asList(cs1);
    // var cd1 = new ConnectiveDomainDynamicVo(2L, "acd1", "#CDBE70");
    // var cds = Arrays.asList(cd1);
    // var subgs1 = new SubgraphStaticVo(4L, 0d, css);
    // var subgd1 = new SubgraphDynamicVo(4L, "default subgraph", cds);
    // var subga1 = new SubgraphAllVo(4L, subgs1, subgd1);

    // // 验证
    // assertEquals(getHashCodeForSubgraphAllVo(svo),
    // getHashCodeForSubgraphAllVo(subga1));
    // }

    // @Test
    // public void testGetOriginalGraphShortestPath() {
    // // 打桩数据生成
    // var projectPo = new ProjectPo(2L, 5L, "projC", null, null, null);
    // // Vertices
    // var v1 = new VertexPo(3L, "v1", "dian1", "a1", 0f, 0f);
    // var v2 = new VertexPo(4L, "v2", "dian2", "a2", 0f, 0f);
    // var vertices = new HashSet<VertexPo>(Arrays.asList(v1, v2));
    // projectPo.setVertices(vertices);
    // // Edges
    // var e1 = new EdgePo(3L, v1, v2, 0.3d, "bian1");
    // var edges = new HashSet<EdgePo>(Arrays.asList(e1));
    // projectPo.setEdges(edges);
    // // Subgraphs
    // var connectiveDomain1 = new ConnectiveDomainPo(2L, vertices, edges, "acd1",
    // "#CDBE70");
    // var connectiveDomains1 = new
    // HashSet<ConnectiveDomainPo>(Arrays.asList(connectiveDomain1));
    // var defaultSubgraph = new SubgraphPo(4L, 0d, "default subgraph",
    // connectiveDomains1);
    // var subgraphs = new HashSet<SubgraphPo>(Arrays.asList(defaultSubgraph));
    // projectPo.setSubgraphs(subgraphs);

    // // 打桩
    // Mockito.when(projectData.findById(2L)).thenReturn(Optional.ofNullable(projectPo));

    // // 调用
    // var resVo = service.getOriginalGraphShortestPath(2L, 3L, 4L);

    // ArrayList<List<Long>> paths = new ArrayList<>();
    // ArrayList<Long> path = new ArrayList<>();
    // path.add(3L);
    // paths.add(path);
    // var pathVo = new PathVo(paths);
    // // 验证
    // assertEquals(pathVo, resVo);

    // }

    // @Test
    // public void testGetSimilarFunction() {
    // // 打桩数据生成
    // var projectPo = new ProjectPo(2L, 5L, "projC", null, null, null);
    // // Vertices
    // var v1 = new VertexPo(3L, "v1", "dian1", "a1", 0f, 0f);
    // var v2 = new VertexPo(4L, "v2", "dian2", "a2", 0f, 0f);
    // var vertices = new HashSet<VertexPo>(Arrays.asList(v1, v2));
    // projectPo.setVertices(vertices);
    // // Edges
    // var e1 = new EdgePo(3L, v1, v2, 0.3d, "bian1");
    // var edges = new HashSet<EdgePo>(Arrays.asList(e1));
    // projectPo.setEdges(edges);
    // // Subgraphs
    // var connectiveDomain1 = new ConnectiveDomainPo(2L, vertices, edges, "acd1",
    // "#CDBE70");
    // var connectiveDomains1 = new
    // HashSet<ConnectiveDomainPo>(Arrays.asList(connectiveDomain1));
    // var defaultSubgraph = new SubgraphPo(4L, 0d, "default subgraph",
    // connectiveDomains1);
    // var subgraphs = new HashSet<SubgraphPo>(Arrays.asList(defaultSubgraph));
    // projectPo.setSubgraphs(subgraphs);

    // // 打桩
    // Mockito.when(projectData.findById(2L)).thenReturn(Optional.ofNullable(projectPo));

    // List<String> strs = new ArrayList<>();
    // strs.add("v1");
    // strs.add("v2");

    // var res = service.getSimilarFunction(2L, "v");
    // assertEquals(new HashSet<>(res), new HashSet<>(strs));
    // }
}