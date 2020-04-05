package local.happysixplus.backendcodeanalysis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.util.AffineTransformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import local.happysixplus.backendcodeanalysis.util.callgraph.CallGraphMethods;
import local.happysixplus.backendcodeanalysis.data.ConnectiveDomainColorDynamicData;
import local.happysixplus.backendcodeanalysis.data.ConnectiveDomainDynamicData;
import local.happysixplus.backendcodeanalysis.data.EdgeData;
import local.happysixplus.backendcodeanalysis.data.EdgeDynamicData;
import local.happysixplus.backendcodeanalysis.data.ProjectData;
import local.happysixplus.backendcodeanalysis.data.ProjectDynamicData;
import local.happysixplus.backendcodeanalysis.data.ProjectStaticAttributeData;
import local.happysixplus.backendcodeanalysis.data.SubgraphData;
import local.happysixplus.backendcodeanalysis.data.SubgraphDynamicData;
import local.happysixplus.backendcodeanalysis.data.VertexData;
import local.happysixplus.backendcodeanalysis.data.VertexDynamicData;
import local.happysixplus.backendcodeanalysis.data.VertexPositionDynamicData;
import local.happysixplus.backendcodeanalysis.exception.MyRuntimeException;
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
import local.happysixplus.backendcodeanalysis.vo.ProjectAllVo;
import local.happysixplus.backendcodeanalysis.vo.ProjectDynamicVo;
import local.happysixplus.backendcodeanalysis.vo.SubgraphAllVo;
import local.happysixplus.backendcodeanalysis.vo.SubgraphDynamicVo;
import local.happysixplus.backendcodeanalysis.vo.VertexAllVo;
import local.happysixplus.backendcodeanalysis.vo.VertexDynamicVo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.var;

@Service
public class AsyncForProjectServiceImpl {

    static public class Vertex {
        Long id;
        String functionName = "";
        String sourceCode = "";
        List<Edge> edges = new ArrayList<>();
        List<Edge> allEdges = new ArrayList<>();

        Vertex(VertexPo po) {
            id = po.getId();
            functionName = po.getFunctionName();
            sourceCode = po.getSourceCode();
        }

        VertexAllVo getAllVo(VertexDynamicVo dVo) {
            return new VertexAllVo(id, functionName, sourceCode, dVo);
        }
    }

    static public class Edge {
        Long id;
        Double closeness = 0D;
        Vertex from;
        Vertex to;

        Edge(EdgePo po, Vertex from, Vertex to) {
            id = po.getId();
            closeness = po.getCloseness();
            this.from = from;
            this.to = to;
        }

        EdgeAllVo getAllVo(EdgeDynamicVo dVo) {
            return new EdgeAllVo(id, from.id, to.id, closeness, dVo);
        }

    }

    static public class ConnectiveDomain {
        Long id;
        List<Long> vertexIds = new ArrayList<Long>();
        List<Long> edgeIds = new ArrayList<Long>();

        ConnectiveDomain(List<Long> v, List<Long> e) {
            vertexIds = v;
            edgeIds = e;
        }

        ConnectiveDomain(ConnectiveDomainPo po) {
            id = po.getId();
            // anotation = po.getAnotation();
            // color = po.getColor();
            vertexIds = new ArrayList<>(po.getVertexIds());
            edgeIds = new ArrayList<>(po.getEdgeIds());
        }

        ConnectiveDomainPo getConnectiveDomainPo() {
            return new ConnectiveDomainPo(id, new ArrayList<>(vertexIds), new ArrayList<>(edgeIds));
        }

        ConnectiveDomainAllVo getAllVo(ConnectiveDomainDynamicVo dVo) {
            return new ConnectiveDomainAllVo(id, new ArrayList<>(vertexIds), new ArrayList<>(edgeIds), dVo);
        }

    }

    static public class Subgraph {
        Long id;
        Double threshold;
        List<ConnectiveDomain> connectiveDomains = new ArrayList<ConnectiveDomain>();

        Subgraph(Double t, List<ConnectiveDomain> c) {
            threshold = t;
            connectiveDomains = c;
        }

        Subgraph(SubgraphPo po) {
            id = po.getId();
            threshold = po.getThreshold();
            // name = po.getName();
            for (var cPo : po.getConnectiveDomains()) {
                connectiveDomains.add(new ConnectiveDomain(cPo));
            }
        }

        SubgraphPo getSubgraphPo(Long projectId) {
            var cPo = new HashSet<ConnectiveDomainPo>(connectiveDomains.size());
            for (var c : connectiveDomains)
                cPo.add(c.getConnectiveDomainPo());
            return new SubgraphPo(id, projectId, threshold, cPo);
        }

        SubgraphAllVo getAllVo(SubgraphDynamicVo dVo, Map<Long, ConnectiveDomainDynamicPo> cDPoMap,
                Map<Long, ConnectiveDomainColorDynamicPo> cColorDPoMap) {
            var cdVos = new ArrayList<ConnectiveDomainAllVo>(connectiveDomains.size());
            for (var c : connectiveDomains)
                cdVos.add(c.getAllVo(dPoTodVo(cDPoMap.get(c.id), cColorDPoMap.get(c.id))));
            return new SubgraphAllVo(id, threshold, cdVos, dVo);
        }

    }

    static public class Project {
        Long id;
        Long userId;
        Map<Long, Vertex> vIdMap = new HashMap<Long, Vertex>();
        Map<Long, Edge> eIdMap = new HashMap<Long, Edge>();
        String packageStructureJSON;

        Project(ProjectPo po, List<VertexPo> vPos, List<EdgePo> ePos) {
            id = po.getId();
            userId = po.getUserId();
            for (var vPo : vPos)
                vIdMap.put(vPo.getId(), new Vertex(vPo));
            for (var ePo : ePos) {
                var e = new Edge(ePo, vIdMap.get(ePo.getFromId()), vIdMap.get(ePo.getToId()));
                eIdMap.put(ePo.getId(), e);
                vIdMap.get(ePo.getFromId()).edges.add(e);
            }
            packageStructureJSON = po.getPackageStructure();
        }

        ProjectAllVo getAllVo(Map<Long, VertexDynamicPo> vDPoMap, Map<Long, VertexPositionDynamicPo> cPDPoMap,
                Map<Long, EdgeDynamicPo> eDPoMap, List<SubgraphAllVo> sVos, ProjectDynamicVo dVo) {
            List<VertexAllVo> vVos = new ArrayList<>();
            for (var v : vIdMap.values())
                vVos.add(v.getAllVo(dPoTodVo(vDPoMap.get(v.id), cPDPoMap.get(v.id))));
            var rootVo = JSONObject.parseObject(packageStructureJSON, PackageNode.class).getVo();
            List<EdgeAllVo> eVos = new ArrayList<>();
            for (var e : eIdMap.values())
                eVos.add(e.getAllVo(dPoTodVo(eDPoMap.get(e.id))));
            return new ProjectAllVo(id, vVos, rootVo, eVos, sVos, dVo);
        }

        class DfsE {
            long id;
            DfsV to;
            double closeness;

            DfsE(long id, DfsV to, double closeness) {
                this.id = id;
                this.to = to;
                this.closeness = closeness;
            }
        }

        class DfsV {
            long id;
            boolean vst;
            List<DfsE> es = new ArrayList<>();

            DfsV(long id) {
                this.id = id;
                this.vst = false;
            }
        }

        SubgraphPo initSubgraph(Double threshold) {
            var resConnectiveDomains = new ArrayList<ConnectiveDomain>();
            // 点
            var vs = new HashMap<Long, DfsV>(vIdMap.size());
            for (var v : vIdMap.values())
                vs.put(v.id, new DfsV(v.id));
            // 添加双向边
            for (var e : eIdMap.values()) {
                vs.get(e.from.id).es.add(new DfsE(e.id, vs.get(e.to.id), e.closeness));
                vs.get(e.to.id).es.add(new DfsE(e.id, vs.get(e.from.id), e.closeness));
            }
            for (var p : vs.values()) {
                List<Long> domainVertexs = new ArrayList<>();
                Set<Long> domainEdges = new HashSet<>();
                Dfs(threshold, p, domainVertexs, domainEdges);
                if (domainVertexs.size() > 1)
                    resConnectiveDomains.add(new ConnectiveDomain(domainVertexs, new ArrayList<>(domainEdges)));
            }
            resConnectiveDomains.sort((a, b) -> {
                return b.vertexIds.size() - a.vertexIds.size();
            });
            return new Subgraph(threshold, resConnectiveDomains).getSubgraphPo(id);
        }

        void Dfs(double threshold, DfsV p, List<Long> domainVertexs, Set<Long> domainEdges) {
            if (p.vst)
                return;
            p.vst = true;
            domainVertexs.add(p.id);
            for (var e : p.es) {
                if (e.closeness < threshold)
                    continue;
                domainEdges.add(e.id);
                DfsV to = e.to;
                Dfs(threshold, to, domainVertexs, domainEdges);
            }
        }
    }

    @Data
    @NoArgsConstructor
    static class PackageNode {
        String str;
        Map<String, PackageNode> chrs = new HashMap<>();
        List<Long> funcs = new ArrayList<>();

        PackageNode(String str) {
            this.str = str;
        }

        void insertFunc(long id, String partName) {
            if (partName == null) {
                funcs.add(id);
                return;
            }
            var partNameSplit = partName.split("\\.", 2);
            var nextStr = partNameSplit[0];
            var nextPartName = partNameSplit.length == 1 ? null : partNameSplit[1];
            var chr = chrs.get(nextStr);
            if (chr == null) {
                chr = new PackageNode(nextStr);
                chrs.put(nextStr, chr);
            }
            chr.insertFunc(id, nextPartName);
        }

        PackageNodeVo getVo() {
            var chrVos = new ArrayList<PackageNodeVo>(chrs.size());
            for (var chr : chrs.values())
                chrVos.add(chr.getVo());
            return new PackageNodeVo(str, chrVos, funcs);
        }
    }

    @Autowired
    CallGraphMethods callGraphMethods;

    @Autowired
    ProjectData projectData;

    @Autowired
    ProjectStaticAttributeData projectStaticAttributeData;

    @Autowired
    ProjectDynamicData projectDynamicData;

    @Autowired
    SubgraphData subgraphData;

    @Autowired
    SubgraphDynamicData subgraphDynamicData;

    @Autowired
    ConnectiveDomainDynamicData connectiveDomainDynamicData;

    @Autowired
    ConnectiveDomainColorDynamicData connectiveDomainColorDynamicData;

    @Autowired
    EdgeData edgeData;

    @Autowired
    EdgeDynamicData edgeDynamicData;

    @Autowired
    VertexData vertexData;

    @Autowired
    VertexDynamicData vertexDynamicData;

    @Autowired
    VertexPositionDynamicData vertexPositionDynamicData;

    @Async("ProjectExecutor")
    public CompletableFuture<String> asyncRemoveProject(Long id) {
        if (subgraphData.existsByProjectId(id))
            subgraphData.deleteByProjectId(id);
        if (subgraphDynamicData.existsByProjectId(id))
            subgraphDynamicData.deleteByProjectId(id);
        if (connectiveDomainDynamicData.existsByProjectId(id))
            connectiveDomainDynamicData.deleteByProjectId(id);
        if (connectiveDomainColorDynamicData.existsByProjectId(id))
            connectiveDomainColorDynamicData.deleteByProjectId(id);
        if (edgeData.existsByProjectId(id))
            edgeData.deleteByProjectId(id);
        if (edgeDynamicData.existsByProjectId(id))
            edgeDynamicData.deleteByProjectId(id);
        if (vertexData.existsByProjectId(id))
            vertexData.deleteByProjectId(id);
        if (vertexDynamicData.existsByProjectId(id))
            vertexDynamicData.deleteByProjectId(id);
        if (vertexPositionDynamicData.existsByProjectId(id))
            vertexPositionDynamicData.deleteByProjectId(id);
        return CompletableFuture.completedFuture("Success");
    }

    @Async("ProjectExecutor")
    public CompletableFuture<String> asyncAddProject(Long projectId, String projectName, String url, long userId) {
        try {
            var projectInfo = callGraphMethods.initGraph(url);
            if (projectInfo == null)
                throw new MyRuntimeException("您的项目不行");
            String[] callGraph = projectInfo.getCallGraph();
            var sourceCode = projectInfo.getSourceCode();
            List<String> caller = new ArrayList<>();
            List<String> callee = new ArrayList<>();
            Set<List<String>> edgeSet = new HashSet<>();
            for (var str : callGraph) {
                List<String> tempList = new ArrayList<>();
                String[] temp = str.split(" ");
                tempList.add(temp[0].substring(2));
                tempList.add(temp[1].substring(3));
                edgeSet.add(tempList);
            }
            callGraph = null;
            projectInfo = null;
            for (var edge : edgeSet) {
                caller.add(edge.get(0));
                callee.add(edge.get(1));
            }
            // 先存入错误信息避免失败后无法更新
            projectDynamicData.save(new ProjectDynamicPo(projectId, userId, projectName + "（数据库异常）"));
            // 生成并存入项目静态信息
            var project = initAndSaveProject(projectId, caller, callee, sourceCode, userId);
            // 可能projectId已经因为被用户删除而失效
            projectId = project.id;
            sourceCode = null;
            // 生成并存入默认子图静态信息
            var subPo = project.initSubgraph(0D);
            subPo = subgraphData.save(subPo);
            // 存入项目静态属性信息
            var projSAPo = new ProjectStaticAttributePo(project.id, userId, project.vIdMap.size(),
                    project.eIdMap.size(), subPo.getConnectiveDomains().size());
            projSAPo = projectStaticAttributeData.save(projSAPo);
            // 存入项目动态信息
            var projDPo = new ProjectDynamicPo(project.id, userId, projectName);
            projDPo = projectDynamicData.save(projDPo);
            // 存入子图动态信息
            var subgDPo = new SubgraphDynamicPo(subPo.getId(), project.id, "Default subgraph");
            subgDPo = subgraphDynamicData.save(subgDPo);
            // 生成联通域初始颜色并存储
            var cDPoMap = new HashMap<Long, ConnectiveDomainColorDynamicPo>(subPo.getConnectiveDomains().size());
            String[] colors = { "#CDCDB4", "#CDB5CD", "#CDBE70", "#B4CDCD", "#CD919E", "#9ACD32", "#CD4F39", "#8B3E2F",
                    "#8B7E66", "#8B668B", "#36648B", "#141414" };
            for (var cd : subPo.getConnectiveDomains()) {
                var color = colors[((int) (Math.random() * colors.length))];
                var cDPo = connectiveDomainColorDynamicData
                        .save(new ConnectiveDomainColorDynamicPo(cd.getId(), project.id, color));
                cDPoMap.put(cd.getId(), cDPo);
            }
            // 生成节点初始位置
            var cdList = new ArrayList<>(subPo.getConnectiveDomains());
            cdList.sort((a, b) -> {
                return b.getVertexIds().size() - a.getVertexIds().size();
            });
            var vPosPoMap = new HashMap<Long, VertexPositionDynamicPo>(cdList.size());
            class Util {
                HashMap<Long, VertexPositionDynamicPo> resMap;
                Map<Long, Vertex> vMap;
                Map<Long, Integer> wMap = new HashMap<>();
                Set<Long> vstSet = new HashSet<>();
                Long projectId;

                Util(HashMap<Long, VertexPositionDynamicPo> resMap, Long projectId, Map<Long, Vertex> vMap) {
                    this.resMap = resMap;
                    this.projectId = projectId;
                    this.vMap = vMap;
                }

                float calcRadius(int size) {
                    // TODO: 应当根据前端显示效果修改半径系数
                    return (float) (200 * Math.sqrt(size));
                }

                int dfs(Vertex p) {
                    if (vstSet.contains(p.id))
                        return Integer.MAX_VALUE;
                    vstSet.add(p.id);
                    Integer w = wMap.get(p.id);
                    if (w != null)
                        return w;
                    w = Integer.MAX_VALUE;
                    for (var e : p.edges)
                        w = Math.min(w, dfs(e.to));
                    wMap.put(p.id, w - 1);
                    vstSet.remove(p.id);
                    return w - 1;
                }

                void calcPosForCD(Coordinate center, float radius, List<Long> vIds) {
                    List<Vertex> vs = vIds.stream().map(id -> vMap.get(id)).collect(Collectors.toList());
                    int minW = Integer.MAX_VALUE;
                    int maxW = Integer.MIN_VALUE;
                    // 获取权
                    for (var v : vs) {
                        minW = Math.min(minW, dfs(v));
                        maxW = Math.max(maxW, wMap.get(v.id));
                    }
                    vstSet.clear();
                    // 根据权分配坐标
                    radius *= 0.8;
                    float leftUpX = (float) center.x - radius;
                    float leftUpY = (float) center.y - radius;
                    float length = radius * 2;
                    int rowHeight = 80;
                    int maxRowNum = (int) length / rowHeight; // 最大行数
                    int wDiff = maxW - minW + 1;
                    int[] wCnts = new int[wDiff]; // 每个权值统计总数
                    int[] wColCnts = new int[wDiff]; // 每个权值占有的列数
                    int[] wColStart = new int[wDiff]; // 每个权值起始列
                    for (var id : vIds)
                        wCnts[wMap.get(id) - minW]++;
                    for (int i = 0; i < wDiff; i++)
                        wColCnts[i] = wCnts[i] / maxRowNum + (wCnts[i] % maxRowNum == 0 ? 0 : 1);
                    for (int i = 1; i < wDiff; i++)
                        wColStart[i] = wColStart[i - 1] + wColCnts[i - 1];
                    for (int i = 0; i < wDiff; i++)
                        wCnts[i] = 0;
                    int colWidth = (int)length / (wColStart[wDiff - 1] + wColCnts[wDiff - 1]);
                    for (var id : vIds) {
                        int rw = wMap.get(id) - minW;
                        float x = wColStart[rw] + wCnts[rw] / maxRowNum;
                        float y = wCnts[rw] % maxRowNum;
                        wCnts[rw]++;
                        resMap.put(id, new VertexPositionDynamicPo(id, projectId, leftUpX + x * colWidth, leftUpY + y * rowHeight));
                    }
                    wMap.clear();
                }
            }
            Util util = new Util(vPosPoMap, project.id, project.vIdMap);
            if (cdList.size() > 0) {
                var it = cdList.iterator();
                var cd = it.next();
                var radius = util.calcRadius(cd.getVertexIds().size());
                // 中心联通域
                util.calcPosForCD(new Coordinate(), radius, cd.getVertexIds());
                while (it.hasNext()) {
                    // 确定半径
                    float centerR;
                    float r;
                    double theta;
                    Coordinate p;
                    // 第一个
                    cd = it.next();
                    r = util.calcRadius(cd.getVertexIds().size());
                    centerR = radius + r;
                    theta = Math.asin(r / centerR) * 2 * 1.5;
                    radius += r * 2;
                    p = new Coordinate(0, centerR);
                    util.calcPosForCD(p, r, cd.getVertexIds());
                    // 其余的几个
                    int num = (int) (6.28 / theta);
                    for (int i = 1; i < num; i++) {
                        if (it.hasNext())
                            cd = it.next();
                        else
                            break;
                        AffineTransformation.rotationInstance(theta).transform(p, p);
                        util.calcPosForCD(p, r, cd.getVertexIds());
                    }
                }
            }
            // 存储节点初始位置
            for (var vp : vPosPoMap.values())
                vertexPositionDynamicData.save(vp);
        } catch (Exception e) {
            var failedDPo = new ProjectDynamicPo(projectId, userId, projectName + "（项目无法解析）");
            projectDynamicData.save(failedDPo);
            throw e;
        }
        return CompletableFuture.completedFuture("Finished");
    }

    Project initAndSaveProject(Long projectId, List<String> caller, List<String> callee, Map<String, String> sourceCode,
            Long userId) {
        List<EdgePo> edgePos = new ArrayList<EdgePo>();
        List<VertexPo> vertexPos = new ArrayList<VertexPo>();
        Map<String, VertexPo> vertexMap = new HashMap<String, VertexPo>();
        Map<String, Integer> outdegree = new HashMap<String, Integer>();
        Map<String, Integer> indegree = new HashMap<String, Integer>();

        // 存入项目
        var projPo = projectData.save(new ProjectPo(projectId, userId, ""));
        projectId = projPo.getId();

        // 去除重复点
        var vertexNameSet = new HashSet<String>();
        vertexNameSet.addAll(caller);
        vertexNameSet.addAll(callee);
        for (var str : vertexNameSet) {
            VertexPo vPo;
            if (sourceCode.containsKey(str))
                vPo = new VertexPo(null, projectId, str, sourceCode.get(str));
            else
                vPo = new VertexPo(null, projectId, str, "");
            vertexPos.add(vPo);
        }
        vertexNameSet = null;
        // 存入点
        vertexPos = vertexData.saveAll(vertexPos);
        for (var vPo : vertexPos) {
            vertexMap.put(vPo.getFunctionName(), vPo);
            outdegree.put(vPo.getFunctionName(), 0);
            indegree.put(vPo.getFunctionName(), 0);
        }

        // 计算出度入度
        for (int i = 0; i < caller.size(); i++) {
            String startName = caller.get(i);
            String endName = callee.get(i);
            outdegree.put(startName, outdegree.get(startName) + 1);
            indegree.put(endName, indegree.get(endName) + 1);
        }
        // 生成边
        for (int i = 0; i < caller.size(); i++) {
            String startName = caller.get(i);
            String endName = callee.get(i);
            VertexPo from = vertexMap.get(startName);
            VertexPo to = vertexMap.get(endName);
            Double closeness = 2.0 / (outdegree.get(startName) + indegree.get(endName));
            edgePos.add(new EdgePo(null, projectId, from.getId(), to.getId(), closeness));
        }
        // 存入边
        edgePos = edgeData.saveAll(edgePos);

        // 获取包结构并存入
        var vMap = vertexPos.stream().collect(Collectors.toMap(v -> v.getId(), v -> v));
        PackageNode root = new PackageNode("src");
        for (var v : vMap.values())
            root.insertFunc(v.getId(), v.getFunctionName().split(":", 2)[0]);
        projPo.setPackageStructure(JSONObject.toJSONString(root));
        projPo = projectData.save(projPo);
        return new Project(projPo, vertexPos, edgePos);
    }

    private static VertexDynamicVo dPoTodVo(VertexDynamicPo po, VertexPositionDynamicPo posPo) {
        // 初始一定有Pos
        var res = new VertexDynamicVo(posPo.getId(), "", posPo.getX(), posPo.getY());
        if (po != null)
            res.setAnotation(po.getAnotation());
        return res;
    }

    private static EdgeDynamicVo dPoTodVo(EdgeDynamicPo po) {
        if (po == null)
            return null;
        return new EdgeDynamicVo(po.getId(), po.getAnotation());
    }

    private static ConnectiveDomainDynamicVo dPoTodVo(ConnectiveDomainDynamicPo po,
            ConnectiveDomainColorDynamicPo cPo) {
        // 初始一定有Color
        var res = new ConnectiveDomainDynamicVo(cPo.getId(), "", cPo.getColor());
        if (po != null)
            res.setAnotation(po.getAnotation());
        return res;
    }

}