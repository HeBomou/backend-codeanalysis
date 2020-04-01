package local.happysixplus.backendcodeanalysis.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import local.happysixplus.backendcodeanalysis.util.callgraph.CallGraphMethods;
import local.happysixplus.backendcodeanalysis.data.ConnectiveDomainDynamicData;
import local.happysixplus.backendcodeanalysis.data.EdgeDynamicData;
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
import local.happysixplus.backendcodeanalysis.service.ProjectService;
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

@Service
public class ProjectServiceImpl implements ProjectService {

    public class Vertex {
        Long id;
        String functionName = "";
        String sourceCode = "";
        int inDegree = 0;
        int outDegree = 0;
        List<Edge> edges = new ArrayList<>();
        List<Edge> allEdges = new ArrayList<>();

        Vertex(VertexPo po) {
            id = po.getId();
            functionName = po.getFunctionName();
            sourceCode = po.getSourceCode();
        }

        VertexPo getVertexPo() {
            return new VertexPo(id, functionName, sourceCode);
        }

        VertexAllVo getAllVo(VertexDynamicVo dVo) {
            return new VertexAllVo(id, functionName, sourceCode, dVo);
        }
    }

    public class Edge {
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

        EdgePo getEdgePo(Map<String, VertexPo> vMap) {
            return new EdgePo(id, vMap.get(from.functionName), vMap.get(to.functionName), closeness);
        }

        EdgeAllVo getAllVo(EdgeDynamicVo dVo) {
            return new EdgeAllVo(id, from.id, to.id, closeness, dVo);
        }

    }

    public class ConnectiveDomain {
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

    public class Subgraph {
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

        SubgraphAllVo getAllVo(SubgraphDynamicVo dVo, Map<Long, ConnectiveDomainDynamicPo> cDPoMap) {
            var cdVos = new ArrayList<ConnectiveDomainAllVo>(connectiveDomains.size());
            for (var c : connectiveDomains)
                cdVos.add(c.getAllVo(dPoTodVo(cDPoMap.get(c.id))));
            return new SubgraphAllVo(id, threshold, cdVos, dVo);
        }

    }

    public class Project {
        Long id;
        Long userId;
        Map<Long, Vertex> vIdMap = new HashMap<Long, Vertex>();
        Map<Long, Edge> eIdMap = new HashMap<Long, Edge>();
        List<Subgraph> subgraphs = new ArrayList<Subgraph>();

        Project(ProjectPo po, List<SubgraphPo> sPos) {
            id = po.getId();
            userId = po.getUserId();
            for (var vPo : po.getVertices())
                vIdMap.put(vPo.getId(), new Vertex(vPo));
            for (var ePo : po.getEdges())
                eIdMap.put(ePo.getId(),
                        new Edge(ePo, vIdMap.get(ePo.getFrom().getId()), vIdMap.get(ePo.getTo().getId())));
            for (var sPo : sPos) {
                subgraphs.add(new Subgraph(sPo));
            }
        }

        ProjectPo getProjectPo() {
            var vPo = new HashSet<VertexPo>(vIdMap.size());
            var vMap = new HashMap<String, VertexPo>(vIdMap.size());
            for (var v : vIdMap.values()) {
                var temp = v.getVertexPo();
                vPo.add(temp);
                vMap.put(v.functionName, temp);
            }
            var ePo = new HashSet<EdgePo>(eIdMap.size());
            var eMap = new HashMap<String, EdgePo>(eIdMap.size());
            for (var e : eIdMap.values()) {
                var temp = e.getEdgePo(vMap);
                ePo.add(temp);
                eMap.put(e.from.functionName + e.to.functionName, temp);
            }
            return new ProjectPo(id, userId, vPo, ePo);
        }

        ProjectAllVo getAllVo(Map<Long, VertexDynamicPo> vDPoMap, Map<Long, EdgeDynamicPo> eDPoMap,
                Map<Long, SubgraphDynamicPo> sDPoMap, Map<Long, ConnectiveDomainDynamicPo> cDPoMap,
                ProjectDynamicVo dVo) {
            List<VertexAllVo> vVos = new ArrayList<>();
            for (var v : vIdMap.values())
                vVos.add(v.getAllVo(dPoTodVo(vDPoMap.get(v.id))));
            List<EdgeAllVo> eVos = new ArrayList<>();
            for (var e : eIdMap.values())
                eVos.add(e.getAllVo(dPoTodVo(eDPoMap.get(e.id))));
            List<SubgraphAllVo> sVos = new ArrayList<>();
            for (var s : subgraphs)
                sVos.add(s.getAllVo(dPoTodVo(sDPoMap.get(s.id)), cDPoMap));
            return new ProjectAllVo(id, vVos, eVos, sVos, dVo);
        }

        class DfsE {
            Long id;
            DfsV to;
            Double closeness;

            DfsE(Long id, DfsV to, Double closeness) {
                this.id = id;
                this.to = to;
                this.closeness = closeness;
            }
        }

        class DfsV {
            Long id;
            List<DfsE> es = new ArrayList<>();

            DfsV(Long id) {
                this.id = id;
            }
        }

        SubgraphPo initSubgraph(Double threshold) {
            var resConnectiveDomains = new ArrayList<ConnectiveDomain>();
            var isChecked = new HashSet<Long>(vIdMap.size());
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
                Dfs(threshold, p, isChecked, domainVertexs, domainEdges);
                if (domainVertexs.size() > 1)
                    resConnectiveDomains.add(new ConnectiveDomain(domainVertexs, new ArrayList<>(domainEdges)));
            }
            resConnectiveDomains.sort((a, b) -> {
                return b.vertexIds.size() - a.vertexIds.size();
            });
            return new Subgraph(threshold, resConnectiveDomains).getSubgraphPo(id);
        }

        void Dfs(Double threshold, DfsV p, Set<Long> isChecked, List<Long> domainVertexs, Set<Long> domainEdges) {
            if (isChecked.contains(p.id))
                return;
            isChecked.add(p.id);
            domainVertexs.add(p.id);
            for (var e : p.es) {
                if (e.closeness < threshold)
                    continue;
                domainEdges.add(e.id);
                DfsV to = e.to;
                Dfs(threshold, to, isChecked, domainVertexs, domainEdges);
            }
        }
    }

    ProjectPo initProject(List<String> caller, List<String> callee, Map<String, String> sourceCode, Long userId) {
        Set<EdgePo> edgePos = new HashSet<EdgePo>();
        Set<VertexPo> vertexPos = new HashSet<VertexPo>();
        Map<String, VertexPo> vertexMap = new HashMap<String, VertexPo>();
        Map<String, Integer> outdegree = new HashMap<String, Integer>();
        Map<String, Integer> indegree = new HashMap<String, Integer>();

        var vertexNameSet = new HashSet<String>();
        vertexNameSet.addAll(caller);
        vertexNameSet.addAll(callee);
        var vertexNames = new ArrayList<String>(vertexNameSet);

        for (var str : vertexNames) {
            VertexPo vPo;
            if (sourceCode.containsKey(str))
                vPo = new VertexPo(null, str, sourceCode.get(str));
            else
                vPo = new VertexPo(null, str, "");
            vertexPos.add(vPo);
            vertexMap.put(str, vPo);
            outdegree.put(str, 0);
            indegree.put(str, 0);
        }

        for (int i = 0; i < caller.size(); i++) {
            String startName = caller.get(i);
            String endName = callee.get(i);
            outdegree.put(startName, outdegree.get(startName) + 1);
            indegree.put(endName, indegree.get(endName) + 1);
        }

        for (int i = 0; i < caller.size(); i++) {
            String startName = caller.get(i);
            String endName = callee.get(i);
            VertexPo from = vertexMap.get(startName);
            VertexPo to = vertexMap.get(endName);
            Double closeness = 2.0 / (outdegree.get(startName) + indegree.get(endName));
            edgePos.add(new EdgePo(null, from, to, closeness));
        }
        return new ProjectPo(null, userId, vertexPos, edgePos);
    }

    @Autowired
    CallGraphMethods callGraphMethods;

    @Autowired
    ProjectData projectData;

    @Autowired
    SubgraphData subgraphData;

    @Autowired
    ProjectDynamicData projectDynamicData;

    @Autowired
    SubgraphDynamicData subgraphDynamicData;

    @Autowired
    ConnectiveDomainDynamicData connectiveDomainDynamicData;

    @Autowired
    EdgeDynamicData edgeDynamicData;

    @Autowired
    VertexDynamicData vertexDynamicData;

    @Override
    public ProjectAllVo addProject(String projectName, String url, long userId) {
        var projectInfo = callGraphMethods.initGraph(url, projectName);
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
        for (var edge : edgeSet) {
            caller.add(edge.get(0));
            callee.add(edge.get(1));
        }
        // 生成并存入项目静态信息
        var projPo = initProject(caller, callee, sourceCode, userId);
        projPo = projectData.save(projPo);
        var project = new Project(projPo, new ArrayList<>());
        // 生成并存入默认子图静态信息
        var subPo = project.initSubgraph(0D);
        subPo = subgraphData.save(subPo);
        project = new Project(projPo, Arrays.asList(subPo));
        // 存入项目动态信息
        var projDPo = new ProjectDynamicPo(projPo.getId(), userId, projectName);
        projDPo = projectDynamicData.save(projDPo);
        // 存入子图动态信息
        var subgDPo = new SubgraphDynamicPo(subPo.getId(), projPo.getId(), "Default subgraph");
        subgDPo = subgraphDynamicData.save(subgDPo);
        // 返回结果
        var subgDPoMap = new HashMap<Long, SubgraphDynamicPo>(1);
        subgDPoMap.put(subgDPo.getId(), subgDPo);
        return project.getAllVo(new HashMap<>(), new HashMap<>(), subgDPoMap, new HashMap<>(), dPoTodVo(projDPo));
    };

    @Override
    public void removeProject(Long id) {
        projectData.deleteById(id);
        subgraphData.deleteById(id);
        projectDynamicData.deleteById(id);
        subgraphDynamicData.deleteByProjectId(id);
        connectiveDomainDynamicData.deleteByProjectId(id);
        edgeDynamicData.deleteByProjectId(id);
        vertexDynamicData.deleteByProjectId(id);
    };

    @Override
    public void updateProjectDynamic(Long projectId, ProjectDynamicVo vo) {
        vo.setId(projectId);
        var userId = projectDynamicData.findById(projectId).orElse(null).getUserId();
        projectDynamicData.save(new ProjectDynamicPo(vo.getId(), userId, vo.getProjectName()));
    };

    @Override
    public List<ProjectDynamicVo> getProjectDynamicByUserId(Long userId) {
        var pDPos = projectDynamicData.findByUserId(userId);
        var vos = new ArrayList<ProjectDynamicVo>(pDPos.size());
        for (var pDPo : pDPos)
            vos.add(dPoTodVo(pDPo));
        return vos;
    };

    @Override
    public ProjectAllVo getProjectAllById(Long id) {
        var po = projectData.findById(id).orElse(null);
        var sPos = subgraphData.findByProjectId(id);
        var project = new Project(po, sPos);
        var vDPoMap = vertexDynamicData.findByProjectId(id).stream().collect(Collectors.toMap(v -> v.getId(), v -> v));
        var eDPoMap = edgeDynamicData.findByProjectId(id).stream().collect(Collectors.toMap(v -> v.getId(), v -> v));
        var sDPoMap = subgraphDynamicData.findByProjectId(id).stream()
                .collect(Collectors.toMap(v -> v.getId(), v -> v));
        var cDPoMap = connectiveDomainDynamicData.findByProjectId(id).stream()
                .collect(Collectors.toMap(v -> v.getId(), v -> v));
        var dVo = dPoTodVo(projectDynamicData.findById(id).orElse(null));
        return project.getAllVo(vDPoMap, eDPoMap, sDPoMap, cDPoMap, dVo);
    }

    @Override
    public SubgraphAllVo addSubgraph(Long projectId, Double threshold, String name) {
        // 生成并存储静态信息
        var po = projectData.findById(projectId).orElse(null);
        var sPos = subgraphData.findByProjectId(projectId);
        var project = new Project(po, sPos);
        var newSPo = project.initSubgraph(threshold);
        newSPo = subgraphData.save(newSPo);
        // 存储动态信息
        var sDPo = new SubgraphDynamicPo(newSPo.getId(), projectId, name);

        var subgraph = new Subgraph(newSPo);
        return subgraph.getAllVo(dPoTodVo(sDPo), new HashMap<>());
    };

    @Override
    public void removeSubgraph(Long id) {
        subgraphData.deleteById(id);
    };

    @Override
    public void updateSubGraphDynamic(Long projectId, SubgraphDynamicVo vo) {
        subgraphDynamicData.save(new SubgraphDynamicPo(vo.getId(), projectId, vo.getName()));
    };

    @Override
    public void updateConnectiveDomainDynamic(Long projectId, Long subgraphId, ConnectiveDomainDynamicVo vo) {
        connectiveDomainDynamicData
                .save(new ConnectiveDomainDynamicPo(vo.getId(), projectId, vo.getAnotation(), vo.getColor()));
    }

    @Override
    public void updateEdgeDynamic(Long projectId, EdgeDynamicVo vo) {
        edgeDynamicData.save(new EdgeDynamicPo(vo.getId(), projectId, vo.getAnotation()));
    }

    @Override
    public void updateVertexDynamic(Long projectId, VertexDynamicVo vo) {
        vertexDynamicData.save(new VertexDynamicPo(vo.getId(), projectId, vo.getAnotation(), vo.getX(), vo.getY()));
    }

    @Override
    public PathVo getOriginalGraphShortestPath(Long projectId, Long startVertexId, Long endVertexId) {
        var po = projectData.findById(projectId).orElse(null);
        var sPos = subgraphData.findByProjectId(projectId);
        var project = new Project(po, sPos);
        var res = new ArrayList<List<Long>>();
        getAllPathDFS(endVertexId, project.vIdMap.get(startVertexId), new ArrayList<Long>(), res);
        res.sort((a, b) -> {
            return b.size() - a.size();
        });
        return new PathVo(res);
    };

    private void getAllPathDFS(Long endVertexId, Vertex p, List<Long> path, List<List<Long>> res) {
        if (p.id == endVertexId) {
            res.add(new ArrayList<>(path));
        }
        for (var edge : p.edges) {
            if (edge.to == p)
                continue;
            path.add(edge.id);
            getAllPathDFS(endVertexId, edge.to, path, res);
            path.remove(path.size() - 1);
        }
    }

    @Override
    public List<String> getSimilarFunction(Long projectId, String funcName) {
        var res = new ArrayList<String>();
        var po = projectData.findById(projectId).orElse(null);
        for (var vPo : po.getVertices())
            if (vPo.getFunctionName().contains(funcName))
                res.add(vPo.getFunctionName());
        return res;
    };

    private static VertexDynamicVo dPoTodVo(VertexDynamicPo po) {
        if (po == null)
            return new VertexDynamicVo();
        return new VertexDynamicVo(po.getId(), po.getAnotation(), po.getX(), po.getY());
    }

    private static EdgeDynamicVo dPoTodVo(EdgeDynamicPo po) {
        if (po == null)
            return new EdgeDynamicVo();
        return new EdgeDynamicVo(po.getId(), po.getAnotation());
    }

    private static ConnectiveDomainDynamicVo dPoTodVo(ConnectiveDomainDynamicPo po) {
        if (po == null)
            return new ConnectiveDomainDynamicVo();
        return new ConnectiveDomainDynamicVo(po.getId(), po.getAnotation(), po.getColor());
    }

    private static SubgraphDynamicVo dPoTodVo(SubgraphDynamicPo po) {
        if (po == null)
            return new SubgraphDynamicVo();
        return new SubgraphDynamicVo(po.getId(), po.getName());
    }

    private static ProjectDynamicVo dPoTodVo(ProjectDynamicPo po) {
        if (po == null)
            return new ProjectDynamicVo();
        return new ProjectDynamicVo(po.getId(), po.getProjectName());
    }

    // private static String getRandomColor() {
    // String[] colors = { "#CDCDB4", "#CDB5CD", "#CDBE70", "#B4CDCD", "#CD919E",
    // "#9ACD32", "#CD4F39", "#8B3E2F",
    // "#8B7E66", "#8B668B", "#36648B", "#141414" };
    // return colors[((int) (Math.random() * colors.length))];
    // } TODO: 应当为联通域随机生成DPo
}