package local.happysixplus.backendcodeanalysis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

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
import local.happysixplus.backendcodeanalysis.vo.PathVo;
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
        long res = vo.getId().hashCode() + vo.getThreshold().hashCode();
        res += getHashCodeForSubgraphDynamicVo(vo.getDynamicVo());
        res += vo.getConnectiveDomains().stream().map(ProjectServiceTest::getHashCodeForConnectiveDomainAllVo)
                .collect(Collectors.toSet()).hashCode();
        return res;
    }

    static long getHashCodeForConnectiveDomainAllVo(ConnectiveDomainAllVo vo) {
        long res = vo.getId().hashCode();
        res += new HashSet<>(vo.getVertexIds()).hashCode();
        res += new HashSet<>(vo.getEdgeIds()).hashCode();
        if (vo.getDomainDynamicVo() != null)
            res += vo.getDomainDynamicVo().hashCode();
        return res;
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

    static long getHashCodeForPathVo(PathVo vo) {
        long res = vo.getNum();
        res += vo.getPaths().stream().map(ProjectServiceTest::getHashCodeForPath).collect(Collectors.toSet())
                .hashCode();
        return res;
    }

    static long getHashCodeForPath(List<Long> path) {
        return new HashSet<>(path).hashCode();
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
        var resVo = service.getProjectAll(2L);

        // 验证数据生成
        var expV1 = new VertexAllVo(3L, "v1", "dian1()", new VertexDynamicVo(3L, "a1", 0f, 0f));
        var expV2 = new VertexAllVo(4L, "v2", "dian2()", new VertexDynamicVo(4L, "a2", 0f, 0f));
        var expE1 = new EdgeAllVo(3L, 3L, 4L, 0.3d, new EdgeDynamicVo(3L, "abian 1"));
        var expC1 = new ConnectiveDomainAllVo(2L, Arrays.asList(3L, 4L), Arrays.asList(3L),
                new ConnectiveDomainDynamicVo(2L, "acd 1", "#CDBE70"));
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

    @Test
    public void testAddSubgraph1() {
        // 打桩数据生成
        var po = new ProjectPo(2L, 233L, null, null);

        var v1 = new VertexPo(1L, "v1", "dian1()");
        var v2 = new VertexPo(2L, "v2", "dian2()");
        var v3 = new VertexPo(3L, "v3", "dian3()");
        var v4 = new VertexPo(4L, "v4", "dian4()");
        var v5 = new VertexPo(5L, "v5", "dian5()");
        var vertices = new HashSet<VertexPo>(Arrays.asList(v1, v2, v3, v4, v5));
        po.setVertices(vertices);

        var e1 = new EdgePo(1L, v1, v2, 0.666667d);
        var e2 = new EdgePo(2L, v2, v5, 0.5d);
        var e3 = new EdgePo(3L, v1, v4, 0.4d);
        var e4 = new EdgePo(4L, v2, v4, 0.4d);
        var e5 = new EdgePo(5L, v4, v5, 0.666667d);
        var e6 = new EdgePo(6L, v3, v1, 0.666667d);
        var e7 = new EdgePo(7L, v3, v4, 0.4d);
        var edges = new HashSet<EdgePo>(Arrays.asList(e1, e2, e3, e4, e5, e6, e7));
        po.setEdges(edges);

        var spo1 = new SubgraphPo(null, 2L, 0.555d, null);
        var connectiveDomain1 = new ConnectiveDomainPo(null, Arrays.asList(1L, 2L, 3L), Arrays.asList(1L, 6L));
        var connectiveDomain2 = new ConnectiveDomainPo(null, Arrays.asList(4L, 5L), Arrays.asList(5L));
        var connectiveDomains1 = new HashSet<ConnectiveDomainPo>(Arrays.asList(connectiveDomain1, connectiveDomain2));
        spo1.setConnectiveDomains(connectiveDomains1);

        var spo2 = new SubgraphPo(17L, 2L, 0.555d, null);
        var connectiveDomain3 = new ConnectiveDomainPo(1L, Arrays.asList(1L, 2L, 3L), Arrays.asList(1L, 6L));
        var connectiveDomain4 = new ConnectiveDomainPo(2L, Arrays.asList(4L, 5L), Arrays.asList(5L));
        var connectiveDomains2 = new HashSet<ConnectiveDomainPo>(Arrays.asList(connectiveDomain3, connectiveDomain4));
        spo2.setConnectiveDomains(connectiveDomains2);

        var sdpo1 = new SubgraphDynamicPo(17L, 2L, "subgraphdd");
        var sdpo2 = new SubgraphDynamicPo(17L, 2L, "subgraphdd");

        // 打桩
        Mockito.when(projectData.findById(2L)).thenReturn(Optional.of(po));
        Mockito.when(subgraphData.save(spo1)).thenReturn(spo2);
        Mockito.when(subgraphDynamicData.save(sdpo1)).thenReturn(sdpo2);

        // 调用
        var res = service.addSubgraph(2L, 0.555d, "subgraphdd");

        // 验证
        Mockito.verify(projectData).findById(2L);
        Mockito.verify(subgraphData).save(spo1);
        Mockito.verify(subgraphDynamicData).save(sdpo1);

        var connectiveDomainVo1 = new ConnectiveDomainAllVo(1L, Arrays.asList(1L, 2L, 3L), Arrays.asList(6L, 1L), null);
        var connectiveDomainVo2 = new ConnectiveDomainAllVo(2L, Arrays.asList(4L, 5L), Arrays.asList(5L), null);
        var connectiveDomainsVo = Arrays.asList(connectiveDomainVo1, connectiveDomainVo2);
        var svo = new SubgraphAllVo(17L, 0.555d, connectiveDomainsVo, new SubgraphDynamicVo(17L, "subgraphdd"));

        assertEquals(getHashCodeForSubgraphAllVo(svo), getHashCodeForSubgraphAllVo(res));

    }

    @Test
    public void testRemoveSubgraph1() {
        service.removeSubgraph(3L);
        Mockito.verify(subgraphData).deleteById(3L);
    }

    @Test
    public void testUpdateSubgraphDynamic1() {
        service.updateSubGraphDynamic(2L, new SubgraphDynamicVo(1L, "newName"));
        Mockito.verify(subgraphDynamicData).save(new SubgraphDynamicPo(1L, 2L, "newName"));
    }

    @Test
    public void testUpdateConnectiveDomainDynamic1() {
        service.updateConnectiveDomainDynamic(2L, 1L, new ConnectiveDomainDynamicVo(3L, "best", "#555555"));
        Mockito.verify(connectiveDomainDynamicData).save(new ConnectiveDomainDynamicPo(3L, 2L, "best", "#555555"));
    }

    @Test
    public void testUpdateEdgeDynamic1() {
        service.updateEdgeDynamic(2L, new EdgeDynamicVo(5L, "this is root"));
        Mockito.verify(edgeDynamicData).save(new EdgeDynamicPo(5L, 2L, "this is root"));
    }

    @Test
    public void testUpdateVertexDynamic1() {
        service.updateVertexDynamic(27L, new VertexDynamicVo(456L, "this is a vertex", 45.5f, 92.2f));
        Mockito.verify(vertexDynamicData).save(new VertexDynamicPo(456L, 27L, "this is a vertex", 45.5f, 92.2f));
    }

    @Test
    public void testGetOriginalGraphPath() {
        // 打桩数据生成
        var po = new ProjectPo(2L, 233L, null, null);
        // 点
        var v1 = new VertexPo(1L, "v1", "dian1()");
        var v2 = new VertexPo(2L, "v2", "dian2()");
        var v3 = new VertexPo(3L, "v3", "dian3()");
        var v4 = new VertexPo(4L, "v4", "dian4()");
        var v5 = new VertexPo(5L, "v5", "dian5()");
        var vertices = new HashSet<VertexPo>(Arrays.asList(v1, v2, v3, v4, v5));
        po.setVertices(vertices);
        // 边
        var e1 = new EdgePo(1L, v1, v2, 0.666667d);
        var e2 = new EdgePo(2L, v2, v5, 0.5d);
        var e3 = new EdgePo(3L, v1, v4, 0.4d);
        var e4 = new EdgePo(4L, v2, v4, 0.4d);
        var e5 = new EdgePo(5L, v4, v5, 0.666667d);
        var e6 = new EdgePo(6L, v3, v1, 0.666667d);
        var e7 = new EdgePo(7L, v3, v4, 0.4d);
        var edges = new HashSet<EdgePo>(Arrays.asList(e1, e2, e3, e4, e5, e6, e7));
        po.setEdges(edges);
        // 打桩
        Mockito.when(projectData.findById(2L)).thenReturn(Optional.of(po));
        // 调用
        var res = service.getOriginalGraphPath(2L, 1L, 5L);
        // 测试
        Mockito.verify(projectData).findById(2L);
        var p1 = Arrays.asList(1L, 2L);
        var p2 = Arrays.asList(3L, 5L);
        var p3 = Arrays.asList(1L, 4L, 5L);
        var pvo = new PathVo(3, Arrays.asList(p1, p2, p3));
        assertEquals(getHashCodeForPathVo(pvo), getHashCodeForPathVo(res));
    }

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