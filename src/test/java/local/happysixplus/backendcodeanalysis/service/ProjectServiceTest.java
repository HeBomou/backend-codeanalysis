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
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import local.happysixplus.backendcodeanalysis.data.EdgeData;
import local.happysixplus.backendcodeanalysis.data.ProjectData;
import local.happysixplus.backendcodeanalysis.data.ProjectDynamicData;
import local.happysixplus.backendcodeanalysis.data.ProjectStaticAttributeData;
import local.happysixplus.backendcodeanalysis.data.SubgraphData;
import local.happysixplus.backendcodeanalysis.data.SubgraphDynamicData;
import local.happysixplus.backendcodeanalysis.data.VertexData;
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
import local.happysixplus.backendcodeanalysis.vo.PackageNodeVo;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProjectServiceTest {
    @MockBean
    VertexData vertexData;

    @MockBean
    EdgeData edgeData;

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

    static long getHashCodeForPackageNode(PackageNodeVo vo) {
        long res = vo.getStr().hashCode();
        res += new HashSet<>(vo.getFunctions()).hashCode();
        for (var chr : vo.getChildren())
            res += getHashCodeForPackageNode(chr);
        return res;
    }

    static long getHashCodeForProjectAllVo(ProjectAllVo vo) {
        long res = vo.getId().hashCode();
        res += new HashSet<>(vo.getEdges()).hashCode();
        res += new HashSet<>(vo.getVertices()).hashCode();
        res += getHashCodeForPackageNode(vo.getPackageRoot());
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

    @SuppressWarnings("unchecked")
    @Test
    public void testAddProject1() {
        // 打桩数据生成
        // project
        var v1 = new VertexPo(1L, 23333L, "top.hebomou.App:main(java.lang.String[])",
                "public static void main( String[] args )");
        var v2 = new VertexPo(2L, 23333L, "top.hebomou.ClassA:<init>()", "public ClassA()");
        var v3 = new VertexPo(3L, 23333L, "top.hebomou.ClassA:funcA2()", "public void funcA2()");
        var v4 = new VertexPo(4L, 23333L, "top.hebomou.ClassB:<init>(char)", "public ClassB(char temp)");
        var v5 = new VertexPo(5L, 23333L, "top.hebomou.ClassB:funcB2(char)", "public void funcB2(char temp)");
        var v6 = new VertexPo(6L, 23333L, "top.hebomou.ClassC:<init>()", "public ClassC()");
        var v7 = new VertexPo(7L, 23333L, "top.hebomou.ClassC:funcC1(top.hebomou.ClassB,char)",
                "public void funcC1(ClassB obj, char temp)");
        var v8 = new VertexPo(8L, 23333L, "top.hebomou.ClassD:<init>()", "public ClassD()");
        var v9 = new VertexPo(9L, 23333L, "top.hebomou.ClassD:funcD1()", "public void funcD1()");
        var vPo = Arrays.asList(v1, v2, v3, v4, v5, v6, v7, v8, v9);
        var e1 = new EdgePo(1L, 23333L, 1L, 2L, 1d);
        var e2 = new EdgePo(2L, 23333L, 4L, 7L, 0.5d);
        var e3 = new EdgePo(3L, 23333L, 4L, 6L, 0.5d);
        var e4 = new EdgePo(4L, 23333L, 8L, 9L, 1d);
        var e5 = new EdgePo(5L, 23333L, 2L, 4L, 1d);
        var e6 = new EdgePo(6L, 23333L, 7L, 5L, 0.666667d);
        var e7 = new EdgePo(7L, 23333L, 3L, 6L, 0.666667d);
        var e8 = new EdgePo(8L, 23333L, 7L, 7L, 0.5d);
        var ePo = Arrays.asList(e1, e2, e3, e4, e5, e6, e7, e8);
        var po = new ProjectPo(23333L, 2L,
                "{\"chrs\":{\"top\":{\"chrs\":{\"hebomou\":{\"chrs\":{\"App\":{\"chrs\":{},\"funcs\":[1],\"str\":\"App\"},\"ClassD\":{\"chrs\":{},\"funcs\":[8,9],\"str\":\"ClassD\"},\"ClassA\":{\"chrs\":{},\"funcs\":[2,3],\"str\":\"ClassA\"},\"ClassB\":{\"chrs\":{},\"funcs\":[4,5],\"str\":\"ClassB\"},\"ClassC\":{\"chrs\":{},\"funcs\":[6,7],\"str\":\"ClassC\"}},\"funcs\":[],\"str\":\"hebomou\"}},\"funcs\":[],\"str\":\"top\"}},\"funcs\":[],\"str\":\"src\"}",
                -1l);
        // subgraph
        var connectiveDomain1 = new ConnectiveDomainPo(444444L, Arrays.asList(8L, 9L), Arrays.asList(4L));
        var connectiveDomain2 = new ConnectiveDomainPo(444445L, Arrays.asList(1L, 2L, 5L, 3L, 7L, 6L, 4L),
                Arrays.asList(1L, 3L, 2L, 7L, 5L, 8L, 6L));
        var connectiveDomainsPo = new HashSet<ConnectiveDomainPo>(Arrays.asList(connectiveDomain1, connectiveDomain2));
        var sPo = new SubgraphPo(123456789L, 2L, 0d, connectiveDomainsPo);
        // static attribute
        var saPo = new ProjectStaticAttributePo(23333L, 2L, 9, 8, 2, -1l);
        // project dynamic
        var dPo = new ProjectDynamicPo(23333L, 2L, "Test Faker", -1l);
        // subgraph dynamic
        var dsPo = new SubgraphDynamicPo(123456789L, 23333L, "Default subgraph");

        // 打桩
        Mockito.when(vertexData.saveAll(isA(List.class))).thenReturn(vPo);
        Mockito.when(edgeData.saveAll(isA(List.class))).thenReturn(ePo);
        Mockito.when(projectData.save(isA(ProjectPo.class))).thenReturn(po);
        Mockito.when(vertexPositionDynamicData.save(Mockito.any(VertexPositionDynamicPo.class)))
                .thenAnswer((Answer<VertexPositionDynamicPo>) invocation -> {
                    return (VertexPositionDynamicPo) invocation.getArgument(0);
                });
        Mockito.when(subgraphData.save(isA(SubgraphPo.class))).thenReturn(sPo);
        Mockito.when(projectStaticAttributeData.save(isA(ProjectStaticAttributePo.class))).thenReturn(saPo);
        Mockito.when(projectDynamicData.save(isA(ProjectDynamicPo.class))).thenReturn(dPo);
        Mockito.when(subgraphDynamicData.save(isA(SubgraphDynamicPo.class))).thenReturn(dsPo);
        Mockito.when(connectiveDomainColorDynamicData.save(Mockito.any(ConnectiveDomainColorDynamicPo.class)))
                .thenAnswer((Answer<ConnectiveDomainColorDynamicPo>) invocation -> {
                    return (ConnectiveDomainColorDynamicPo) invocation.getArgument(0);
                });

        // 执行
        service.addProject("Test Faker", "https://gitee.com/HeBomou/simple", 2L, -1L);

        // 测试
        try {
            Thread.sleep(1000 * 20);

        } catch (Exception e) {
        }
        Mockito.verify(vertexData).saveAll(isA(List.class));
        Mockito.verify(edgeData).saveAll(isA(List.class));
        Mockito.verify(projectData, Mockito.atLeastOnce()).save(isA(ProjectPo.class));
        Mockito.verify(vertexPositionDynamicData, Mockito.atLeastOnce())
                .save(Mockito.any(VertexPositionDynamicPo.class));
        Mockito.verify(subgraphData, Mockito.atLeastOnce()).save(isA(SubgraphPo.class));
        Mockito.verify(projectStaticAttributeData, Mockito.atLeastOnce()).save(isA(ProjectStaticAttributePo.class));
        Mockito.verify(projectDynamicData, Mockito.atLeastOnce()).save(isA(ProjectDynamicPo.class));
        Mockito.verify(subgraphDynamicData, Mockito.atLeastOnce()).save(isA(SubgraphDynamicPo.class));
        Mockito.verify(connectiveDomainColorDynamicData, Mockito.atLeastOnce())
                .save(Mockito.any(ConnectiveDomainColorDynamicPo.class));

        // for (var cd : resVo.getSubgraphs().get(0).getConnectiveDomains()) {
        // assertEquals(cd.getDomainDynamicVo().getColor().charAt(0), '#');
        // assertEquals(cd.getDomainDynamicVo().getColor().length(), 7);
        // // 忽略颜色
        // cd.getDomainDynamicVo().setColor(null);
        // }
        // // 测试数据生成
        // var vVo1 = new VertexAllVo(1L, "top.hebomou.App:main(java.lang.String[])",
        // "public static void main( String[] args )", new VertexDynamicVo(1L, "", null,
        // null));
        // var vVo2 = new VertexAllVo(2L, "top.hebomou.ClassA:<init>()", "public
        // ClassA()",
        // new VertexDynamicVo(2L, "", null, null));
        // var vVo3 = new VertexAllVo(3L, "top.hebomou.ClassA:funcA2()", "public void
        // funcA2()",
        // new VertexDynamicVo(3L, "", null, null));
        // var vVo4 = new VertexAllVo(4L, "top.hebomou.ClassB:<init>(char)", "public
        // ClassB(char temp)",
        // new VertexDynamicVo(4L, "", null, null));
        // var vVo5 = new VertexAllVo(5L, "top.hebomou.ClassB:funcB2(char)", "public
        // void funcB2(char temp)",
        // new VertexDynamicVo(5L, "", null, null));
        // var vVo6 = new VertexAllVo(6L, "top.hebomou.ClassC:<init>()", "public
        // ClassC()",
        // new VertexDynamicVo(6L, "", null, null));
        // var vVo7 = new VertexAllVo(7L,
        // "top.hebomou.ClassC:funcC1(top.hebomou.ClassB,char)",
        // "public void funcC1(ClassB obj, char temp)", new VertexDynamicVo(7L, "",
        // null, null));
        // var vVo8 = new VertexAllVo(8L, "top.hebomou.ClassD:<init>()", "public
        // ClassD()",
        // new VertexDynamicVo(8L, "", null, null));
        // var vVo9 = new VertexAllVo(9L, "top.hebomou.ClassD:funcD1()", "public void
        // funcD1()",
        // new VertexDynamicVo(9L, "", null, null));
        // var verticesVo = Arrays.asList(vVo1, vVo2, vVo3, vVo4, vVo5, vVo6, vVo7,
        // vVo8, vVo9);
        // var nVo1 = new PackageNodeVo("App", new ArrayList<>(), Arrays.asList(1L));
        // var nVo2 = new PackageNodeVo("ClassA", new ArrayList<>(), Arrays.asList(2L,
        // 3L));
        // var nVo3 = new PackageNodeVo("ClassB", new ArrayList<>(), Arrays.asList(4L,
        // 5L));
        // var nVo4 = new PackageNodeVo("ClassC", new ArrayList<>(), Arrays.asList(6L,
        // 7L));
        // var nVo5 = new PackageNodeVo("ClassD", new ArrayList<>(), Arrays.asList(8L,
        // 9L));
        // var nVo6 = new PackageNodeVo("hebomou", Arrays.asList(nVo1, nVo2, nVo3, nVo4,
        // nVo5), new ArrayList<>());
        // var nVo7 = new PackageNodeVo("top", Arrays.asList(nVo6), new ArrayList<>());
        // var nVoRoot = new PackageNodeVo("src", Arrays.asList(nVo7), new
        // ArrayList<>());
        // var eVo1 = new EdgeAllVo(1L, 1L, 2L, 1d, null);
        // var eVo2 = new EdgeAllVo(2L, 4L, 7L, 0.5d, null);
        // var eVo3 = new EdgeAllVo(3L, 4L, 6L, 0.5d, null);
        // var eVo4 = new EdgeAllVo(4L, 8L, 9L, 1d, null);
        // var eVo5 = new EdgeAllVo(5L, 2L, 4L, 1d, null);
        // var eVo6 = new EdgeAllVo(6L, 7L, 5L, 0.666667d, null);
        // var eVo7 = new EdgeAllVo(7L, 3L, 6L, 0.666667d, null);
        // var eVo8 = new EdgeAllVo(8L, 7L, 7L, 0.5d, null);
        // var edgesVo = Arrays.asList(eVo1, eVo2, eVo3, eVo4, eVo5, eVo6, eVo7, eVo8);
        // var connectiveDomainVo1 = new ConnectiveDomainAllVo(444444L,
        // Arrays.asList(8L, 9L), Arrays.asList(4L),
        // new ConnectiveDomainDynamicVo(444444L, "", null));
        // var connectiveDomainVo2 = new ConnectiveDomainAllVo(444445L,
        // Arrays.asList(1L, 2L, 5L, 3L, 7L, 6L, 4L),
        // Arrays.asList(1L, 3L, 2L, 7L, 5L, 8L, 6L), new
        // ConnectiveDomainDynamicVo(444445L, "", null));
        // var connectiveDomainsVo = Arrays.asList(connectiveDomainVo1,
        // connectiveDomainVo2);
        // var sVo = Arrays.asList(new SubgraphAllVo(123456789L, 0d,
        // connectiveDomainsVo,
        // new SubgraphDynamicVo(123456789L, "Default subgraph")));
        // var pVo = new ProjectAllVo(23333L, verticesVo, nVoRoot, edgesVo, sVo,
        // new ProjectDynamicVo(23333L, "Test Faker"));

    }

    @Test
    public void testGetProjectAll1() {
        // 打桩数据生成
        var v1 = new VertexPo(3L, 2L, "edu.itrust.BeanBuilder:v1(java.util.Map,java.lang.Object)", "dian1()");
        var v2 = new VertexPo(4L, 2L, "edu.itrust.BeanSBer:v2(java.util.Map,java.lang.Object)", "dian2()");
        var vPo = Arrays.asList(v1, v2);
        var e1 = new EdgePo(3L, 2L, 3L, 4L, 0.3d);
        var ePo = Arrays.asList(e1);
        var po = new ProjectPo(2L, 233L,
                "{\"chrs\":{\"edu\":{\"chrs\":{\"itrust\":{\"chrs\":{\"BeanBuilder\":{\"chrs\":{},\"funcs\":[3],\"str\":\"BeanBuilder\"},\"BeanSBer\":{\"chrs\":{},\"funcs\":[4],\"str\":\"BeanSBer\"}},\"funcs\":[],\"str\":\"itrust\"}},\"funcs\":[],\"str\":\"edu\"}},\"funcs\":[],\"str\":\"src\"}",
                -1l);

        // Subgraphs
        var connectiveDomain1 = new ConnectiveDomainPo(2L, Arrays.asList(3L, 4L), Arrays.asList(3L));
        var connectiveDomains1 = new HashSet<ConnectiveDomainPo>(Arrays.asList(connectiveDomain1));
        var sPo = new SubgraphPo(4L, 2L, 0d, connectiveDomains1);

        // Dynamic Pos
        var dV1 = new VertexDynamicPo(3L, 2L, "a1");
        var dV2 = new VertexDynamicPo(4L, 2L, "a2");
        var pDV1 = new VertexPositionDynamicPo(3L,3L, 2L,4L, 0f, 0f);
        var pDV2 = new VertexPositionDynamicPo(4L,4L, 2L,4L, 0f, 0f);
        var dE1 = new EdgeDynamicPo(3L, 2L, "abian 1");
        var dConnectiveDomain1 = new ConnectiveDomainDynamicPo(2L, 2L, "acd 1");
        var dConnectiveDomainColor1 = new ConnectiveDomainColorDynamicPo(2L, 2L, "#CDBE70");
        var dsPo = new SubgraphDynamicPo(4L, 2L, "Default subgraph");
        var dPo = new ProjectDynamicPo(2L, 5L, "projC", -1l);

        // 打桩
        Mockito.when(vertexData.findByProjectId(2L)).thenReturn(vPo);
        Mockito.when(edgeData.findByProjectId(2L)).thenReturn(ePo);
        Mockito.when(projectData.findById(2L)).thenReturn(Optional.of(po));
        Mockito.when(subgraphData.findByProjectId(2L)).thenReturn(Arrays.asList(sPo));
        Mockito.when(projectDynamicData.findById(2L)).thenReturn(Optional.of(dPo));
        Mockito.when(subgraphDynamicData.findByProjectId(2L)).thenReturn(Arrays.asList(dsPo));
        Mockito.when(connectiveDomainDynamicData.findByProjectId(2L)).thenReturn(Arrays.asList(dConnectiveDomain1));
        Mockito.when(connectiveDomainColorDynamicData.findByProjectId(2L))
                .thenReturn(Arrays.asList(dConnectiveDomainColor1));
        Mockito.when(edgeDynamicData.findByProjectId(2L)).thenReturn(Arrays.asList(dE1));
        Mockito.when(vertexDynamicData.findByProjectId(2L)).thenReturn(Arrays.asList(dV1, dV2));
        Mockito.when(vertexPositionDynamicData.findBySubgraphId(4L)).thenReturn(Arrays.asList(pDV1, pDV2));

        // 调用
        var resVo = service.getProjectAll(2L);
        // 验证数据生成
        var expV1 = new VertexAllVo(3L, "edu.itrust.BeanBuilder:v1(java.util.Map,java.lang.Object)", "dian1()",
                new VertexDynamicVo(3L,"a1"));
        var expV2 = new VertexAllVo(4L, "edu.itrust.BeanSBer:v2(java.util.Map,java.lang.Object)", "dian2()",
                new VertexDynamicVo(4L,"a2"));
        var expPNode1 = new PackageNodeVo("BeanBuilder", new ArrayList<>(), Arrays.asList(3L));
        var expPNode2 = new PackageNodeVo("BeanSBer", new ArrayList<>(), Arrays.asList(4L));
        var expPNode3 = new PackageNodeVo("itrust", Arrays.asList(expPNode1, expPNode2), new ArrayList<>());
        var expPNode4 = new PackageNodeVo("edu", Arrays.asList(expPNode3), new ArrayList<>());
        var expPNode5 = new PackageNodeVo("src", Arrays.asList(expPNode4), new ArrayList<>());
        var expE1 = new EdgeAllVo(3L, 3L, 4L, 0.3d, new EdgeDynamicVo(3L, "abian 1"));
        var expC1 = new ConnectiveDomainAllVo(2L, Arrays.asList(3L, 4L), Arrays.asList(3L),
                new ConnectiveDomainDynamicVo(2L, "acd 1", "#CDBE70"));
        var expS1 = new SubgraphAllVo(4L, 0d, Arrays.asList(expC1), new SubgraphDynamicVo(4L, "Default subgraph", null)); // TODO:
        var expP = new ProjectAllVo(2L, Arrays.asList(expV1, expV2), expPNode5, Arrays.asList(expE1),
                Arrays.asList(expS1), new ProjectDynamicVo(2L, "projC"));
        // 验证
        assertEquals(getHashCodeForProjectAllVo(expP), getHashCodeForProjectAllVo(resVo));
    }

    @Test
    public void testGetProjectBasicAttribute() {
        // 打桩数据生成
        var dPo1 = new ProjectDynamicPo(10000L, 55555L, "One project", -1l);
        var dPo2 = new ProjectDynamicPo(10001L, 55555L, "Two project", -1l);
        var dPo3 = new ProjectDynamicPo(10002L, 55555L, "Three project", -1l);
        var dPos = Arrays.asList(dPo1, dPo2, dPo3);
        var sPo1 = new ProjectStaticAttributePo(10000L, 55555L, 34, 56, 4, -1l);
        var sPo2 = new ProjectStaticAttributePo(10001L, 55555L, 2333, 6000, 68, -1l);
        var sPo3 = new ProjectStaticAttributePo(10002L, 55555L, 2, 1, 1, -1l);
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
        var res = service.getProjectBasicAttribute(55555L, -1L);
        // 测试
        Mockito.verify(projectDynamicData).findByUserId(55555L);
        Mockito.verify(projectStaticAttributeData).findByUserId(55555L);

        assertEquals(vos, res);
    }

    @Test
    public void testGetProjectProfile() {
        // 打桩数据生成
        var dPo = new ProjectDynamicPo(10000L, 55555L, "One project", -1l);
        var sPo = new ProjectStaticAttributePo(10000L, 55555L, 34, 56, 4, -1l);
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
        Mockito.when(projectData.existsById(99999L)).thenReturn(true);
        Mockito.when(projectDynamicData.existsById(99999L)).thenReturn(true);
        Mockito.when(projectStaticAttributeData.existsById(99999L)).thenReturn(true);
        service.removeProject(99999L);
        Mockito.verify(projectData).deleteById(99999L);
        Mockito.verify(projectDynamicData).deleteById(99999L);
        Mockito.verify(projectStaticAttributeData).deleteById(99999L);
    }

    @Test
    public void testUpdateProjectDynamic1() {
        // 打桩数据生成
        var originDPo = new ProjectDynamicPo(256L, 15L, "Hello", -1l);
        var dPoToSave = new ProjectDynamicPo(256L, 15L, "Bye", -1l);
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
        var po = new ProjectPo(2L, 233L, "zheli xjb xie ye meishi", -1l);

        var v1 = new VertexPo(1L, 2L, "v1", "dian1()");
        var v2 = new VertexPo(2L, 2L, "v2", "dian2()");
        var v3 = new VertexPo(3L, 2L, "v3", "dian3()");
        var v4 = new VertexPo(4L, 2L, "v4", "dian4()");
        var v5 = new VertexPo(5L, 2L, "v5", "dian5()");
        var vPo = Arrays.asList(v1, v2, v3, v4, v5);

        var e1 = new EdgePo(1L, 2L, 1L, 2L, 0.666667d);
        var e2 = new EdgePo(2L, 2L, 2L, 5L, 0.5d);
        var e3 = new EdgePo(3L, 2L, 1L, 4L, 0.4d);
        var e4 = new EdgePo(4L, 2L, 2L, 4L, 0.4d);
        var e5 = new EdgePo(5L, 2L, 4L, 5L, 0.666667d);
        var e6 = new EdgePo(6L, 2L, 3L, 1L, 0.666667d);
        var e7 = new EdgePo(7L, 2L, 3L, 4L, 0.4d);
        var ePo = Arrays.asList(e1, e2, e3, e4, e5, e6, e7);

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
        Mockito.when(vertexData.findByProjectId(2L)).thenReturn(vPo);
        Mockito.when(edgeData.findByProjectId(2L)).thenReturn(ePo);
        Mockito.when(projectData.findById(2L)).thenReturn(Optional.of(po));
        Mockito.when(subgraphData.save(spo1)).thenReturn(spo2);
        Mockito.when(subgraphDynamicData.save(sdpo1)).thenReturn(sdpo2);
        Mockito.when(connectiveDomainColorDynamicData.save(Mockito.any(ConnectiveDomainColorDynamicPo.class)))
                .thenAnswer((Answer<ConnectiveDomainColorDynamicPo>) invocation -> {
                    return (ConnectiveDomainColorDynamicPo) invocation.getArgument(0);
                });
        // 调用
        var res = service.addSubgraph(2L, 0.555d, "subgraphdd");
        for (var cd : res.getConnectiveDomains()) {
            assertEquals(cd.getDomainDynamicVo().getColor().charAt(0), '#');
            assertEquals(cd.getDomainDynamicVo().getColor().length(), 7);
            // 忽略颜色
            cd.getDomainDynamicVo().setColor(null);
        }

        // 验证
        Mockito.verify(vertexData).findByProjectId(2L);
        Mockito.verify(edgeData).findByProjectId(2L);
        Mockito.verify(projectData).findById(2L);
        Mockito.verify(subgraphData).save(spo1);
        Mockito.verify(subgraphDynamicData).save(sdpo1);

        var connectiveDomainVo1 = new ConnectiveDomainAllVo(1L, Arrays.asList(3L, 1L, 2L), Arrays.asList(6L, 1L),
                new ConnectiveDomainDynamicVo(1L, "", null));
        var connectiveDomainVo2 = new ConnectiveDomainAllVo(2L, Arrays.asList(5L, 4L), Arrays.asList(5L),
                new ConnectiveDomainDynamicVo(2L, "", null));
        var connectiveDomainsVo = Arrays.asList(connectiveDomainVo1, connectiveDomainVo2);
        var svo = new SubgraphAllVo(17L, 0.555d, connectiveDomainsVo, new SubgraphDynamicVo(17L, "subgraphdd", null)); // TODO:
        assertEquals(getHashCodeForSubgraphAllVo(svo), getHashCodeForSubgraphAllVo(res));

    }

    @Test
    public void testRemoveSubgraph1() {
        service.removeSubgraph(3L);
        Mockito.verify(subgraphData).deleteById(3L);
        Mockito.verify(subgraphDynamicData).deleteById(3L);
    }

    @Test
    public void testUpdateSubgraphDynamic1() {
        service.updateSubGraphDynamic(2L, new SubgraphDynamicVo(1L, "newName", null)); // TODO:
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
        service.updateVertexDynamic(27L, new VertexDynamicVo(456L, "this is a vertex"));
        Mockito.verify(vertexDynamicData).save(new VertexDynamicPo(456L, 27L, "this is a vertex"));
        // Mockito.verify(vertexPositionDynamicData).save(new VertexPositionDynamicPo(456L, 27L, 45.5f, 92.2f));
    }

    @Test
    public void testUpdateConnectiveDomainAllVertex() {

        // 打桩数据
        var vpPo1 = new VertexPositionDynamicPo(1L,1L, 2L,3L, 34.56f, 76.12f);
        var vpPo2 = new VertexPositionDynamicPo(2L,2L, 2L,3L, -34.56f, -76.12f);
        var vpPo3 = new VertexPositionDynamicPo(3L,3L, 2L,3L, 0f, -10f);
        var vPos = Arrays.asList(vpPo1, vpPo2, vpPo3);
        var cPo = new ConnectiveDomainPo(10L, Arrays.asList(1L, 3L), Arrays.asList(1L, 2L));
        // 打桩
        Mockito.when(vertexPositionDynamicData.findBySubgraphId(3L)).thenReturn(vPos);
        Mockito.when(connectiveDomainData.findById(10L)).thenReturn(Optional.of(cPo));
        // 执行
        service.updateConnectiveDomainAllVertex(2L, 3L,10L, 56.7f, -10.71f);
        // 测试数据
        var vpPo4 = new VertexPositionDynamicPo(1L,1L, 2L,3L, 91.26f, 65.41f);
        var vpPo5 = new VertexPositionDynamicPo(3L,3L, 2L,3L, 56.7f, -20.71f);
        // 测试
        Mockito.verify(vertexPositionDynamicData).findBySubgraphId(3L);
        Mockito.verify(connectiveDomainData).findById(10L);
        Mockito.verify(vertexPositionDynamicData).saveAll(Arrays.asList(vpPo4, vpPo5));
    }

    @Test
    public void testGetOriginalGraphPath() {
        // 打桩数据生成
        var po = new ProjectPo(2L, 233L, "project package", -1l);
        var v1 = new VertexPo(1L, 2L, "v1", "dian1()");
        var v2 = new VertexPo(2L, 2L, "v2", "dian2()");
        var v3 = new VertexPo(3L, 2L, "v3", "dian3()");
        var v4 = new VertexPo(4L, 2L, "v4", "dian4()");
        var v5 = new VertexPo(5L, 2L, "v5", "dian5()");
        var vPo = Arrays.asList(v1, v2, v3, v4, v5);
        var e1 = new EdgePo(1L, 2L, 1L, 2L, 0.666667d);
        var e2 = new EdgePo(2L, 2L, 2L, 5L, 0.5d);
        var e3 = new EdgePo(3L, 2L, 1L, 4L, 0.4d);
        var e4 = new EdgePo(4L, 2L, 2L, 4L, 0.4d);
        var e5 = new EdgePo(5L, 2L, 4L, 5L, 0.666667d);
        var e6 = new EdgePo(6L, 2L, 3L, 1L, 0.666667d);
        var e7 = new EdgePo(7L, 2L, 3L, 4L, 0.4d);
        var ePo = Arrays.asList(e1, e2, e3, e4, e5, e6, e7);
        // 打桩
        Mockito.when(projectData.findById(2L)).thenReturn(Optional.of(po));
        Mockito.when(vertexData.findByProjectId(2L)).thenReturn(vPo);
        Mockito.when(edgeData.findByProjectId(2L)).thenReturn(ePo);

        // 调用
        var res = service.getOriginalGraphPath(2L, 1L, 5L);
        // 测试
        Mockito.verify(projectData).findById(2L);
        Mockito.verify(vertexData).findByProjectId(2L);
        Mockito.verify(edgeData).findByProjectId(2L);
        var p1 = Arrays.asList(1L, 2L);
        var p2 = Arrays.asList(3L, 5L);
        var p3 = Arrays.asList(1L, 4L, 5L);
        var pvo = new PathVo(3, Arrays.asList(p1, p2, p3));
        assertEquals(getHashCodeForPathVo(pvo), getHashCodeForPathVo(res));
    }

    @Test
    public void testGetSimilarFunction() {
        // 打桩数据生成
        var v1 = new VertexPo(1L, 2L, "v1", "dian1()");
        var v2 = new VertexPo(2L, 2L, "v2", "dian2()");
        var v3 = new VertexPo(3L, 2L, "v3", "dian3()");
        var v4 = new VertexPo(4L, 2L, "v4", "dian4()");
        var v5 = new VertexPo(5L, 2L, "a5", "dian5()");
        var vPo = Arrays.asList(v1, v2, v3, v4, v5);

        // 打桩
        Mockito.when(vertexData.findByProjectId(2L)).thenReturn(vPo);

        // 调用
        var res = service.getSimilarFunction(2L, "v");

        // 测试数据
        var strs = new ArrayList<String>();
        strs.add("v1");
        strs.add("v2");
        strs.add("v3");
        strs.add("v4");

        // 测试
        Mockito.verify(vertexData).findByProjectId(2L);
        assertEquals(new HashSet<>(strs), new HashSet<>(res));
    }
}