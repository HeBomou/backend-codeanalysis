package local.happysixplus.backendcodeanalysis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

import local.happysixplus.backendcodeanalysis.data.ConnectiveDomainColorDynamicData;
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
import local.happysixplus.backendcodeanalysis.data.ProjectStaticAttributeData;
import local.happysixplus.backendcodeanalysis.data.SubgraphData;
import local.happysixplus.backendcodeanalysis.data.SubgraphDynamicData;
import local.happysixplus.backendcodeanalysis.data.VertexDynamicData;
import local.happysixplus.backendcodeanalysis.data.VertexPositionDynamicData;
import local.happysixplus.backendcodeanalysis.po.ConnectiveDomainColorDynamicPo;
import local.happysixplus.backendcodeanalysis.po.ConnectiveDomainDynamicPo;
import local.happysixplus.backendcodeanalysis.po.ConnectiveDomainPo;
import local.happysixplus.backendcodeanalysis.po.EdgeDynamicPo;
import local.happysixplus.backendcodeanalysis.po.EdgePo;
import local.happysixplus.backendcodeanalysis.po.ProjectDynamicPo;
import local.happysixplus.backendcodeanalysis.po.ProjectPo;
import local.happysixplus.backendcodeanalysis.po.ProjectStaticAttributePo;
import local.happysixplus.backendcodeanalysis.po.SubgraphDynamicPo;
import local.happysixplus.backendcodeanalysis.po.SubgraphPo;
import local.happysixplus.backendcodeanalysis.po.VertexDynamicPo;
import local.happysixplus.backendcodeanalysis.po.VertexPo;
import local.happysixplus.backendcodeanalysis.po.VertexPositionDynamicPo;
import local.happysixplus.backendcodeanalysis.vo.ConnectiveDomainAllVo;
import local.happysixplus.backendcodeanalysis.vo.ConnectiveDomainDynamicVo;
import local.happysixplus.backendcodeanalysis.vo.EdgeAllVo;
import local.happysixplus.backendcodeanalysis.vo.EdgeDynamicVo;
import local.happysixplus.backendcodeanalysis.vo.PathVo;
import local.happysixplus.backendcodeanalysis.vo.ProjectAllVo;
import local.happysixplus.backendcodeanalysis.vo.ProjectBasicAttributeVo;
import local.happysixplus.backendcodeanalysis.vo.ProjectDynamicVo;
import local.happysixplus.backendcodeanalysis.vo.ProjectProfileVo;
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
    ProjectStaticAttributeData projectStaticAttributeData;

    @MockBean
    SubgraphDynamicData subgraphDynamicData;

    @MockBean
    ConnectiveDomainDynamicData connectiveDomainDynamicData;

    @MockBean
    ConnectiveDomainColorDynamicData connectiveDomainColorDynamicData;

    @MockBean
    EdgeDynamicData edgeDynamicData;

    @MockBean
    VertexDynamicData vertexDynamicData;

    @MockBean
    VertexPositionDynamicData vertexPositionDynamicData;

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
        // 打桩数据生成
        // project
        var v1 = new VertexPo(1L, "top.hebomou.App:main(java.lang.String[])",
                "public static void main( String[] args )");
        var v2 = new VertexPo(2L, "top.hebomou.ClassA:<init>()", "public ClassA()");
        var v3 = new VertexPo(3L, "top.hebomou.ClassA:funcA2()", "public void funcA2()");
        var v4 = new VertexPo(4L, "top.hebomou.ClassB:<init>(char)", "public ClassB(char temp)");
        var v5 = new VertexPo(5L, "top.hebomou.ClassB:funcB2(char)", "public void funcB2(char temp)");
        var v6 = new VertexPo(6L, "top.hebomou.ClassC:<init>()", "public ClassC()");
        var v7 = new VertexPo(7L, "top.hebomou.ClassC:funcC1(top.hebomou.ClassB,char)",
                "public void funcC1(ClassB obj, char temp)");
        var v8 = new VertexPo(8L, "top.hebomou.ClassD:<init>()", "public ClassD()");
        var v9 = new VertexPo(9L, "top.hebomou.ClassD:funcD1()", "public void funcD1()");
        var vertices = new HashSet<VertexPo>(Arrays.asList(v1, v2, v3, v4, v5, v6, v7, v8, v9));
        var e1 = new EdgePo(1L, v1, v2, 1d);
        var e2 = new EdgePo(2L, v4, v7, 0.5d);
        var e3 = new EdgePo(3L, v4, v6, 0.5d);
        var e4 = new EdgePo(4L, v8, v9, 1d);
        var e5 = new EdgePo(5L, v2, v4, 1d);
        var e6 = new EdgePo(6L, v7, v5, 0.666667d);
        var e7 = new EdgePo(7L, v3, v6, 0.666667d);
        var e8 = new EdgePo(8L, v7, v7, 0.5d);
        var edges = new HashSet<EdgePo>(Arrays.asList(e1, e2, e3, e4, e5, e6, e7, e8));
        var po = new ProjectPo(23333L, 2L, vertices, edges);
        // subgraph
        var connectiveDomain1 = new ConnectiveDomainPo(444444L, Arrays.asList(8L, 9L), Arrays.asList(4L));
        var connectiveDomain2 = new ConnectiveDomainPo(444445L, Arrays.asList(1L, 2L, 5L, 3L, 7L, 6L, 4L),
                Arrays.asList(1L, 3L, 2L, 7L, 5L, 8L, 6L));
        var connectiveDomainsPo = new HashSet<ConnectiveDomainPo>(Arrays.asList(connectiveDomain1, connectiveDomain2));
        var sPo = new SubgraphPo(123456789L, 2L, 0d, connectiveDomainsPo);
        // static attribute
        var saPo = new ProjectStaticAttributePo(23333L, 2L, 9, 8, 2);
        // project dynamic
        var dPo = new ProjectDynamicPo(23333L, 2L, "Test Faker");
        // subgraph dynamic
        var dsPo = new SubgraphDynamicPo(123456789L, 23333L, "Default subgraph");

        // 打桩
        Mockito.when(projectData.save(isA(ProjectPo.class))).thenReturn(po);
        Mockito.when(subgraphData.save(isA(SubgraphPo.class))).thenReturn(sPo);
        Mockito.when(projectStaticAttributeData.save(isA(ProjectStaticAttributePo.class))).thenReturn(saPo);
        Mockito.when(projectDynamicData.save(isA(ProjectDynamicPo.class))).thenReturn(dPo);
        Mockito.when(subgraphDynamicData.save(isA(SubgraphDynamicPo.class))).thenReturn(dsPo);

        // 执行
        var resVo = service.addProject("Test Faker", "https://gitee.com/HeBomou/simple", 2L);

        // 测试数据生成
        var vVo1 = new VertexAllVo(1L, "top.hebomou.App:main(java.lang.String[])",
                "public static void main( String[] args )", null);
        var vVo2 = new VertexAllVo(2L, "top.hebomou.ClassA:<init>()", "public ClassA()", null);
        var vVo3 = new VertexAllVo(3L, "top.hebomou.ClassA:funcA2()", "public void funcA2()", null);
        var vVo4 = new VertexAllVo(4L, "top.hebomou.ClassB:<init>(char)", "public ClassB(char temp)", null);
        var vVo5 = new VertexAllVo(5L, "top.hebomou.ClassB:funcB2(char)", "public void funcB2(char temp)", null);
        var vVo6 = new VertexAllVo(6L, "top.hebomou.ClassC:<init>()", "public ClassC()", null);
        var vVo7 = new VertexAllVo(7L, "top.hebomou.ClassC:funcC1(top.hebomou.ClassB,char)",
                "public void funcC1(ClassB obj, char temp)", null);
        var vVo8 = new VertexAllVo(8L, "top.hebomou.ClassD:<init>()", "public ClassD()", null);
        var vVo9 = new VertexAllVo(9L, "top.hebomou.ClassD:funcD1()", "public void funcD1()", null);
        var verticesVo = Arrays.asList(vVo1, vVo2, vVo3, vVo4, vVo5, vVo6, vVo7, vVo8, vVo9);
        var eVo1 = new EdgeAllVo(1L, 1L, 2L, 1d, null);
        var eVo2 = new EdgeAllVo(2L, 4L, 7L, 0.5d, null);
        var eVo3 = new EdgeAllVo(3L, 4L, 6L, 0.5d, null);
        var eVo4 = new EdgeAllVo(4L, 8L, 9L, 1d, null);
        var eVo5 = new EdgeAllVo(5L, 2L, 4L, 1d, null);
        var eVo6 = new EdgeAllVo(6L, 7L, 5L, 0.666667d, null);
        var eVo7 = new EdgeAllVo(7L, 3L, 6L, 0.666667d, null);
        var eVo8 = new EdgeAllVo(8L, 7L, 7L, 0.5d, null);
        var edgesVo = Arrays.asList(eVo1, eVo2, eVo3, eVo4, eVo5, eVo6, eVo7, eVo8);
        var connectiveDomainVo1 = new ConnectiveDomainAllVo(444444L, Arrays.asList(8L, 9L), Arrays.asList(4L), null);
        var connectiveDomainVo2 = new ConnectiveDomainAllVo(444445L, Arrays.asList(1L, 2L, 5L, 3L, 7L, 6L, 4L),
                Arrays.asList(1L, 3L, 2L, 7L, 5L, 8L, 6L), null);
        var connectiveDomainsVo = Arrays.asList(connectiveDomainVo1, connectiveDomainVo2);
        var sVo = Arrays.asList(new SubgraphAllVo(123456789L, 0d, connectiveDomainsVo,
                new SubgraphDynamicVo(123456789L, "Default subgraph")));
        var pVo = new ProjectAllVo(23333L, verticesVo, edgesVo, sVo, new ProjectDynamicVo(23333L, "Test Faker"));

        // 测试
        assertEquals(getHashCodeForProjectAllVo(pVo), getHashCodeForProjectAllVo(resVo));
    }

    @Test
    public void testGetProjectAll1() {
        // 打桩数据生成
        // project
        var v1 = new VertexPo(3L, "v1", "dian1()");
        var v2 = new VertexPo(4L, "v2", "dian2()");
        var vertices = new HashSet<VertexPo>(Arrays.asList(v1, v2));
        var e1 = new EdgePo(3L, v1, v2, 0.3d);
        var edges = new HashSet<EdgePo>(Arrays.asList(e1));
        var po = new ProjectPo(2L, 233L, vertices, edges);

        // Subgraphs
        var connectiveDomain1 = new ConnectiveDomainPo(2L, Arrays.asList(3L, 4L), Arrays.asList(3L));
        var connectiveDomains1 = new HashSet<ConnectiveDomainPo>(Arrays.asList(connectiveDomain1));
        var sPo = new SubgraphPo(4L, 2L, 0d, connectiveDomains1);

        // Dynamic Pos
        var dV1 = new VertexDynamicPo(3L, 2L, "a1");
        var dV2 = new VertexDynamicPo(4L, 2L, "a2");
        var pDV1 = new VertexPositionDynamicPo(3L, 2L, 0f, 0f);
        var pDV2 = new VertexPositionDynamicPo(4L, 2L, 0f, 0f);
        var dE1 = new EdgeDynamicPo(3L, 2L, "abian 1");
        var dConnectiveDomain1 = new ConnectiveDomainDynamicPo(2L, 2L, "acd 1");
        var dConnectiveDomainColor1 = new ConnectiveDomainColorDynamicPo(2L, 2L, "#CDBE70");
        var dsPo = new SubgraphDynamicPo(4L, 2L, "Default subgraph");
        var dPo = new ProjectDynamicPo(2L, 5L, "projC");

        // 打桩
        Mockito.when(projectData.findById(2L)).thenReturn(Optional.of(po));
        Mockito.when(subgraphData.findByProjectId(2L)).thenReturn(Arrays.asList(sPo));
        Mockito.when(projectDynamicData.findById(2L)).thenReturn(Optional.of(dPo));
        Mockito.when(subgraphDynamicData.findByProjectId(2L)).thenReturn(Arrays.asList(dsPo));
        Mockito.when(connectiveDomainDynamicData.findByProjectId(2L)).thenReturn(Arrays.asList(dConnectiveDomain1));
        Mockito.when(connectiveDomainColorDynamicData.findByProjectId(2L)).thenReturn(Arrays.asList(dConnectiveDomainColor1));
        Mockito.when(edgeDynamicData.findByProjectId(2L)).thenReturn(Arrays.asList(dE1));
        Mockito.when(vertexDynamicData.findByProjectId(2L)).thenReturn(Arrays.asList(dV1, dV2));
        Mockito.when(vertexPositionDynamicData.findByProjectId(2L)).thenReturn(Arrays.asList(pDV1, pDV2));

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
    public void testGetProjectBasicAttribute() {
        // 打桩数据生成
        var dPo1 = new ProjectDynamicPo(10000L, 55555L, "One project");
        var dPo2 = new ProjectDynamicPo(10001L, 55555L, "Two project");
        var dPo3 = new ProjectDynamicPo(10002L, 55555L, "Three project");
        var dPos = Arrays.asList(dPo1, dPo2, dPo3);
        var sPo1 = new ProjectStaticAttributePo(10000L, 55555L, 34, 56, 4);
        var sPo2 = new ProjectStaticAttributePo(10001L, 55555L, 2333, 6000, 68);
        var sPo3 = new ProjectStaticAttributePo(10002L, 55555L, 2, 1, 1);
        var sPos = Arrays.asList(sPo1, sPo2, sPo3);
        // 打桩
        Mockito.when(projectDynamicData.findByUserId(55555L)).thenReturn(dPos);
        Mockito.when(projectStaticAttributeData.findByUserId(55555L)).thenReturn(sPos);
        // 验证数据生成
        var vo1 = new ProjectBasicAttributeVo(10000L, "One project", 34, 56, 4);
        var vo2 = new ProjectBasicAttributeVo(10001L, "Two project", 2333, 6000, 68);
        var vo3 = new ProjectBasicAttributeVo(10002L, "Three project", 2, 1, 1);
        var vos = Arrays.asList(vo1, vo2, vo3);
        // 执行
        var res = service.getProjectBasicAttribute(55555L);
        // 测试
        Mockito.verify(projectDynamicData).findByUserId(55555L);
        Mockito.verify(projectStaticAttributeData).findByUserId(55555L);

        assertEquals(vos, res);
    }

    @Test
    public void testGetProjectProfile() {
        // 打桩数据生成
        var dPo = new ProjectDynamicPo(10000L, 55555L, "One project");
        var sPo = new ProjectStaticAttributePo(10000L, 55555L, 34, 56, 4);
        // 打桩
        Mockito.when(projectDynamicData.findById(10000L)).thenReturn(Optional.of(dPo));
        Mockito.when(projectStaticAttributeData.findById(10000L)).thenReturn(Optional.of(sPo));
        Mockito.when(subgraphData.countByProjectId(10000L)).thenReturn(2020);
        Mockito.when(vertexDynamicData.countByProjectId(10000L)).thenReturn(20);
        Mockito.when(edgeDynamicData.countByProjectId(10000L)).thenReturn(20);
        Mockito.when(connectiveDomainDynamicData.countByProjectId(10000L)).thenReturn(0);
        // 验证数据
        var vo = new ProjectProfileVo(10000L, "One project", 34, 56, 4, 2020, 20, 20, 0);
        // 执行
        var res = service.getProjectProfile(10000L);
        // 测试
        Mockito.verify(projectDynamicData).findById(10000L);
        Mockito.verify(projectStaticAttributeData).findById(10000L);
        assertEquals(vo, res);
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

        var connectiveDomainVo1 = new ConnectiveDomainAllVo(1L, Arrays.asList(3L, 1L, 2L), Arrays.asList(6L, 1L), null);
        var connectiveDomainVo2 = new ConnectiveDomainAllVo(2L, Arrays.asList(5L, 4L), Arrays.asList(5L), null);
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
        Mockito.verify(connectiveDomainDynamicData).save(new ConnectiveDomainDynamicPo(3L, 2L, "best"));
        Mockito.verify(connectiveDomainColorDynamicData).save(new ConnectiveDomainColorDynamicPo(3L, 2L, "#555555"));
    }

    @Test
    public void testUpdateEdgeDynamic1() {
        service.updateEdgeDynamic(2L, new EdgeDynamicVo(5L, "this is root"));
        Mockito.verify(edgeDynamicData).save(new EdgeDynamicPo(5L, 2L, "this is root"));
    }

    @Test
    public void testUpdateVertexDynamic1() {
        service.updateVertexDynamic(27L, new VertexDynamicVo(456L, "this is a vertex", 45.5f, 92.2f));
        Mockito.verify(vertexDynamicData).save(new VertexDynamicPo(456L, 27L, "this is a vertex"));
        Mockito.verify(vertexPositionDynamicData).save(new VertexPositionDynamicPo(456L, 27L, 45.5f, 92.2f));
    }

    @Test
    public void testGetOriginalGraphPath() {
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

    @Test
    public void testGetSimilarFunction() {
        // 打桩数据生成
        var po = new ProjectPo(2L, 2333L, null, null);
        var v1 = new VertexPo(1L, "v1", "dian1()");
        var v2 = new VertexPo(2L, "v2", "dian2()");
        var v3 = new VertexPo(3L, "v3", "dian3()");
        var v4 = new VertexPo(4L, "v4", "dian4()");
        var v5 = new VertexPo(5L, "a5", "dian5()");
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

        // 打桩
        Mockito.when(projectData.findById(2L)).thenReturn(Optional.of(po));

        // 调用
        var res = service.getSimilarFunction(2L, "v");

        // 测试数据
        var strs = new ArrayList<String>();
        strs.add("v1");
        strs.add("v2");
        strs.add("v3");
        strs.add("v4");

        // 测试
        Mockito.verify(projectData).findById(2L);
        assertEquals(new HashSet<>(strs), new HashSet<>(res));
    }
}