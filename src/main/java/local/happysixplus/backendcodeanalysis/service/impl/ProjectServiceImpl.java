package local.happysixplus.backendcodeanalysis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import local.happysixplus.backendcodeanalysis.callgraph.CallGraphMethods;
import local.happysixplus.backendcodeanalysis.data.ProjectData;
import local.happysixplus.backendcodeanalysis.data.SubgraphData;
import local.happysixplus.backendcodeanalysis.po.ConnectiveDomainPo;
import local.happysixplus.backendcodeanalysis.po.EdgePo;
import local.happysixplus.backendcodeanalysis.po.ProjectPo;
import local.happysixplus.backendcodeanalysis.po.SubgraphPo;
import local.happysixplus.backendcodeanalysis.po.VertexPo;
import local.happysixplus.backendcodeanalysis.service.ProjectService;
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

@Service
public class ProjectServiceImpl implements ProjectService {

    public class Vertex {
        Long id;
        String functionName;
        String sourceCode;
        String anotation = "";
        Float x = 0F;
        Float y = 0F;
        int inDegree = 0;
        int outDegree = 0;
        List<Edge> edges = new ArrayList<>();
        List<Edge> undirectedEdge = new ArrayList<>();

        Vertex(String name) {
            functionName = name;
        }

        Vertex(VertexPo po) {
            id = po.getId();
            functionName = po.getFunctionName();
            sourceCode = po.getSourceCode();
            anotation = po.getAnotation();
            x = po.getX();
            y = po.getY();
        }

        VertexPo getVertexPo() {
            return new VertexPo(id, functionName, sourceCode, anotation, x, y);
        }

        VertexStaticVo getStaticVo() {
            return new VertexStaticVo(id, functionName, sourceCode);
        }

        VertexDynamicVo getDynamicVo() {
            return new VertexDynamicVo(id, anotation, x, y);
        }

        void addEdge(Edge e) {
            edges.add(e);
        }

        void addUndirectedEdge(Edge e) {
            undirectedEdge.add(e);
        }
    }

    public class Edge {
        Long id;
        Double closeness;
        String anotation = "";
        Vertex from;
        Vertex to;

        Edge(Vertex begin, Vertex end) {
            from = begin;
            to = end;
        }

        Edge(EdgePo po, Vertex from, Vertex to) {
            id = po.getId();
            closeness = po.getCloseness();
            anotation = po.getAnotation();
            this.from = from;
            this.to = to;
        }

        EdgePo getEdgePo(Map<String, VertexPo> vMap) {
            return new EdgePo(id, vMap.get(from.functionName), vMap.get(to.functionName), closeness, anotation);
        }

        EdgeStaticVo getStaticVo() {
            return new EdgeStaticVo(id, from.id, to.id, closeness);
        }

        EdgeDynamicVo getDynamicVo() {
            return new EdgeDynamicVo(id, anotation);
        }
    }

    public class ConnectiveDomain {
        Long id;
        String anotation = "";
        String color;
        List<Vertex> vertices;
        List<Edge> edges;

        ConnectiveDomain(List<Vertex> v, List<Edge> e) {
            edges = e;
            vertices = v;
        }

        ConnectiveDomain(ConnectiveDomainPo po, Map<Long, Vertex> vMap, Map<Long, Edge> eMap) {
            id = po.getId();
            anotation = po.getAnotation();
            color = po.getColor();
            for (var vPo : po.getVertexs())
                vertices.add(vMap.get(vPo.getId()));
            for (var ePo : po.getEdges())
                edges.add(eMap.get(ePo.getId()));
        }

        ConnectiveDomainPo getConnectiveDomainPo(Map<String, VertexPo> vMap, Map<String, EdgePo> eMap) {
            var vPo = new HashSet<VertexPo>(vertices.size());
            for (var v : vertices)
                vPo.add(vMap.get(v.functionName));
            var ePo = new HashSet<EdgePo>(vertices.size());
            for (var e : edges)
                ePo.add(eMap.get(e.from.functionName + e.to.functionName));
            return new ConnectiveDomainPo(id, vPo, ePo, anotation, color);
        }

        ConnectiveDomainStaticVo getStaticVo() {
            List<Long> vertexIds = new ArrayList<Long>(vertices.size());
            for (var v : vertices)
                vertexIds.add(v.id);
            List<Long> edgeIds = new ArrayList<Long>(vertices.size());
            for (var e : edges)
                edgeIds.add(e.id);
            return new ConnectiveDomainStaticVo(id, vertexIds, edgeIds);
        }

        ConnectiveDomainDynamicVo getDynamicVo() {
            return new ConnectiveDomainDynamicVo(id, anotation, color);
        }
    }

    public class Subgraph {
        Long id;
        Double threshold;
        String name = "";
        List<ConnectiveDomain> connectiveDomains;

        Subgraph(Double t, List<ConnectiveDomain> c) {
            threshold = t;
            connectiveDomains = c;
        }

        Subgraph(SubgraphPo po, Map<Long, Vertex> vMap, Map<Long, Edge> eMap) {
            id = po.getId();
            threshold = po.getThreshold();
            name = po.getName();
            for (var cPo : po.getConnectiveDomains()) {
                connectiveDomains.add(new ConnectiveDomain(cPo, vMap, eMap));
            }
        }

        SubgraphPo getSubgraphPo(Map<String, VertexPo> vMap, Map<String, EdgePo> eMap) {
            var cPo = new HashSet<ConnectiveDomainPo>(connectiveDomains.size());
            for (var c : connectiveDomains)
                cPo.add(c.getConnectiveDomainPo(vMap, eMap));
            return new SubgraphPo(id, threshold, name, cPo);
        }

        SubgraphStaticVo getStaticVo() {
            var connectiveDomainVos = new ArrayList<ConnectiveDomainStaticVo>(connectiveDomains.size());
            for (var cd : connectiveDomains)
                connectiveDomainVos.add(cd.getStaticVo());
            return new SubgraphStaticVo(id, threshold, connectiveDomainVos);
        }

        SubgraphDynamicVo getDynamicVo() {
            var connectiveDomainVos = new ArrayList<ConnectiveDomainDynamicVo>(connectiveDomains.size());
            for (var cd : connectiveDomains)
                connectiveDomainVos.add(cd.getDynamicVo());
            return new SubgraphDynamicVo(id, name, connectiveDomainVos);
        }
    }

    public class Project {
        Long id;
        Long userId;
        String projectName;
        List<Subgraph> subgraphs;
        Map<Long, Vertex> vIdMap;
        Map<Long, Edge> eIdMap;

        Project(String pn, long ui) {
            projectName = pn;
            userId = ui;
        }

        Project(ProjectPo po) {
            id = po.getId();
            userId = po.getUserId();
            projectName = po.getProjectName();
            vIdMap = new HashMap<Long, Vertex>(po.getVertices().size());
            for (var vPo : po.getVertices()) {
                var temp = new Vertex(vPo);
                vIdMap.put(vPo.getId(), temp);
            }
            eIdMap = new HashMap<Long, Edge>(po.getEdges().size());
            for (var ePo : po.getEdges()) {
                var temp = new Edge(ePo, vIdMap.get(ePo.getFrom().getId()), vIdMap.get(ePo.getTo().getId()));
                eIdMap.put(ePo.getId(), temp);
            }
            for (var sPo : po.getSubgraphs()) {
                subgraphs.add(new Subgraph(sPo, vIdMap, eIdMap));
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
            var sPo = new HashSet<SubgraphPo>(subgraphs.size());
            for (var s : subgraphs)
                sPo.add(s.getSubgraphPo(vMap, eMap));
            return new ProjectPo(id, userId, projectName, vPo, ePo, sPo);
        }

        ProjectStaticVo getStaticVo() {
            var vertexVos = new ArrayList<VertexStaticVo>(vIdMap.size());
            for (var v : vIdMap.values())
                vertexVos.add(v.getStaticVo());
            var edgeVos = new ArrayList<EdgeStaticVo>(eIdMap.size());
            for (var e : eIdMap.values())
                edgeVos.add(e.getStaticVo());
            var subgraphVos = new ArrayList<SubgraphStaticVo>(subgraphs.size());
            for (var s : subgraphs)
                subgraphVos.add(s.getStaticVo());
            return new ProjectStaticVo(id, vertexVos, edgeVos, subgraphVos);
        }

        ProjectDynamicVo getDynamicVo() {
            var vertexVos = new ArrayList<VertexDynamicVo>(vIdMap.size());
            for (var v : vIdMap.values())
                vertexVos.add(v.getDynamicVo());
            var edgeVos = new ArrayList<EdgeDynamicVo>(eIdMap.size());
            for (var e : eIdMap.values())
                edgeVos.add(e.getDynamicVo());
            var subgraphVos = new ArrayList<SubgraphDynamicVo>(subgraphs.size());
            for (var s : subgraphs)
                subgraphVos.add(s.getDynamicVo());
            return new ProjectDynamicVo(id, projectName, vertexVos, edgeVos, subgraphVos);
        }

        ProjectAllVo getAllVo() {
            return new ProjectAllVo(id, getStaticVo(), getDynamicVo());
        }

        void DFS(Edge edge, Vertex vertex, Map<String, Boolean> isChecked, List<Vertex> domainVertexs,
                List<Edge> domainEdges) {
            if (isChecked.get(vertex.functionName))
                return;
            isChecked.put(vertex.functionName, true);
            domainVertexs.add(vertex);
            if (edge != null)
                domainEdges.add(edge);
            for (Edge e : vertex.undirectedEdge) {
                Vertex anotherVertex = (e.from == vertex) ? e.to : e.from;
                DFS(e, anotherVertex, isChecked, domainVertexs, domainEdges);
            }
        }
    }

    ProjectPo initProject(List<String> caller, List<String> callee, Map<String, String> sourceCode, String pn,
            long ui) {
        Project project = new Project(pn, ui);
        project.projectName = pn;
        project.userId = ui;
        List<Edge> edges = new ArrayList<Edge>();
        Map<String, Vertex> vertexMap = new HashMap<String, Vertex>();
        Map<String, Boolean> isChecked = new HashMap<String, Boolean>();
        // 去重得到所有顶点集合
        var vertexNameSet = new HashSet<String>();
        vertexNameSet.addAll(caller);
        vertexNameSet.addAll(callee);
        var vertexNames = new ArrayList<String>(vertexNameSet);
        for (var str : vertexNames) {
            Vertex newVertex = new Vertex(str);
            if (sourceCode.containsKey(str))
                newVertex.sourceCode = sourceCode.get(str);
            vertexMap.put(str, newVertex);
            isChecked.put(str, false);
        }
        // 得到每个顶点的出度入度
        for (int i = 0; i < caller.size(); i++) {
            var begin = vertexMap.get(caller.get(i));
            var end = vertexMap.get(callee.get(i));
            begin.outDegree++;
            end.inDegree++;
        }
        // 为每个顶点添加边集
        for (int i = 0; i < caller.size(); i++) {
            Vertex begin = vertexMap.get(caller.get(i));
            Vertex end = vertexMap.get(callee.get(i));
            Double closeness = 2.0 / (begin.outDegree + end.inDegree);
            Edge newEdge = new Edge(begin, end);
            newEdge.closeness = closeness;
            edges.add(newEdge);
            begin.addEdge(newEdge);
            begin.addUndirectedEdge(newEdge);
            end.addUndirectedEdge(newEdge);
        }
        // 计算连通域
        var connectiveDomains = new ArrayList<ConnectiveDomain>();
        for (var str : vertexMap.keySet()) {
            List<Vertex> domainVertexs = new ArrayList<>();
            List<Edge> domainEdges = new ArrayList<>();
            project.DFS(null, vertexMap.get(str), isChecked, domainVertexs, domainEdges);
            if (domainVertexs.size() > 0)
                connectiveDomains.add(new ConnectiveDomain(domainVertexs, domainEdges));
        }
        connectiveDomains.sort((a, b) -> {
            return b.vertices.size() - a.vertices.size();
        });
        String[] colors = { "#CDCDB4", "#CDB5CD", "#CDBE70", "#B4CDCD", "#CD919E", "#9ACD32", "#CD4F39", "#8B3E2F",
                "#8B7E66", "#8B668B", "#36648B", "#141414" };
        int cl = colors.length;
        for (int i = 0; i < connectiveDomains.size(); i++) {
            connectiveDomains.get(i).color = colors[i % cl];
        }
        project.subgraphs.add(new Subgraph(0D, connectiveDomains));
        return project.getProjectPo();
    }

    @Autowired
    CallGraphMethods callGraphMethods;

    @Autowired
    ProjectData projectData;

    @Autowired
    SubgraphData subgraphData;

    @Override
    public ProjectAllVo addProject(String projectName, String url, long userId) {
        var project = callGraphMethods.initGraph(url, projectName);
        String[] callGraph = project.getCallGraph();
        var sourceCode = project.getSourceCode();
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
        var po = projectData.save(initProject(caller, callee, sourceCode, projectName, userId));
        var newProject = new Project(po);
        var vo = new ProjectAllVo(newProject.id, newProject.getStaticVo(), newProject.getDynamicVo());
        return vo;
    };

    @Override
    public void removeProject(Long id) {
        projectData.deleteById(id);
    };

    @Override
    public void updateProject(ProjectDynamicVo vo) {
        var po = projectData.findById(vo.getId()).orElse(null);
        Project project = new Project(po);
        project.projectName = vo.getProjectName();
        for (var v : vo.getVertices()) {
            var vertex = project.vIdMap.get(v.getId());
            vertex.anotation = v.getAnotation();
            vertex.x = v.getX();
            vertex.y = v.getY();
        }
        for (var e : vo.getEdges()) {
            var edge = project.eIdMap.get(e.getId());
            edge.anotation = e.getAnotation();
        }
        Map<Long, Subgraph> sIdMap = project.subgraphs.stream().collect(Collectors.toMap(s -> s.id, s -> s));
        for (var s : vo.getSubgraphs()) {
            var subgraph = sIdMap.get(s.getId());
            subgraph.name = s.getName();
            Map<Long, ConnectiveDomain> cIdMap = subgraph.connectiveDomains.stream()
                    .collect(Collectors.toMap(c -> c.id, c -> c));
            for (var c : s.getConnectiveDomains()) {
                var connectiveDomain = cIdMap.get(c.getId());
                connectiveDomain.anotation = c.getAnotation();
                connectiveDomain.color = c.getColor();
            }
        }
        projectData.save(project.getProjectPo());
    };

    @Override
    public List<ProjectAllVo> getProjectAllByUserId(Long userId) {
        var projectPos = projectData.findByUserId(userId);
        var projects = new ArrayList<Project>(projectPos.size());
        for (var projPo : projectPos)
            projects.add(new Project(projPo));
        var res = new ArrayList<ProjectAllVo>(projectPos.size());
        for (var proj : projects)
            res.add(proj.getAllVo());
        return res;
    };

    @Override
    public SubgraphAllVo addSubgraph(Long projectId, Double threshold) {
        //todo
        return new SubgraphAllVo();
    };

    @Override
    public void removeSubgraph(Long id) {
        subgraphData.deleteById(id);
    };

    @Override
    public void updateSubGraph(Long projectId, SubgraphDynamicVo vo) {
        var po = projectData.findById(projectId).orElse(null);
        var project = new Project(po);
        Map<Long, Subgraph> sIdMap = project.subgraphs.stream().collect(Collectors.toMap(s -> s.id, s -> s));
        var subgraph = sIdMap.get(vo.getId());
        subgraph.name = vo.getName();
        Map<Long, ConnectiveDomain> cIdMap = subgraph.connectiveDomains.stream()
                .collect(Collectors.toMap(c -> c.id, c -> c));
        for (var c : vo.getConnectiveDomains()) {
            var connectiveDomain = cIdMap.get(c.getId());
            connectiveDomain.anotation = c.getAnotation();
            connectiveDomain.color = c.getColor();
        }
        projectData.save(project.getProjectPo());
    };

    @Override
    public List<SubgraphAllVo> getSubgraphAllByProjectId(Long projectId) {
        var po = projectData.findById(projectId).orElse(null);
        var project = new Project(po);
        var res = new ArrayList<SubgraphAllVo>(project.subgraphs.size());
        for (var s : project.subgraphs)
            res.add(new SubgraphAllVo(s.id, s.getStaticVo(), s.getDynamicVo()));
        return res;
    };

    @Override
    public PathVo getOriginalGraphShortestPath(Long projectId, Long startVertexId, Long endVertexId) {
        //todo
        return new PathVo();
    };

    @Override
    public PathVo getSubgraphShortestPath(Long projectId, Long subgraphId, Long startVertexId, Long endVertexId) {
        //todo
        return new PathVo();
    };

    @Override
    public List<String> getSimilarFunction(Long projectId, String funcName) {
        var res = new ArrayList<String>();
        var po = projectData.findById(projectId).orElse(null);
        for (var vPo : po.getVertices())
            if (vPo.getFunctionName().contains(funcName))
                res.add(vPo.getFunctionName());
        return res;
    };
}