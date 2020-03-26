package local.happysixplus.backendcodeanalysis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    @Autowired
    CallGraphMethods callGraphMethods;

    @Autowired
    ProjectData projectData;

    @Autowired
    SubgraphData subgraphData;

    public class Vertex {
        Long id;
        String functionName;
        String sourceCode;
        String anotation;
        Float x;
        Float y;
        int inDegree;
        int outDegree;
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
        String anotation;
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

        EdgeStaticVo getStaticVo() {
            return new EdgeStaticVo(id, from.id, to.id, closeness);
        }

        EdgeDynamicVo getDynamicVo() {
            return new EdgeDynamicVo(id, anotation);
        }
    }

    public class ConnectiveDomain {
        Long id;
        String anotation;
        List<Vertex> vertices;
        List<Edge> edges;

        ConnectiveDomain(List<Vertex> v, List<Edge> e) {
            edges = e;
            vertices = v;
        }

        ConnectiveDomain(ConnectiveDomainPo po, List<Vertex> vertices, List<Edge> edges) {
            id = po.getId();
            anotation = po.getAnotation();
            this.vertices = vertices;
            this.edges = edges;
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
            return new ConnectiveDomainDynamicVo(id, anotation);
        }
    }

    public class Subgraph {
        Long id;
        Double threshold;
        String name;
        List<ConnectiveDomain> connectiveDomains;

        Subgraph(SubgraphPo po, List<ConnectiveDomain> connectiveDomains) {
            id = po.getId();
            threshold = po.getThreshold();
            name = po.getName();
            this.connectiveDomains = connectiveDomains;
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
        Map<String, Vertex> vertexMap;
        List<Edge> edges;
        List<Subgraph> subgraphs;
        List<ConnectiveDomain> connectiveDomain; // TODO: 原图联通域改成阈值为零的子图

        Project(List<String> caller, List<String> callee, Map<String, String> sourceCode) {
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
            for (var str : vertexMap.keySet()) {
                List<Vertex> domainVertexs = new ArrayList<>();
                List<Edge> domainEdges = new ArrayList<>();
                DFS(null, vertexMap.get(str), isChecked, domainVertexs, domainEdges);
                if (domainVertexs.size() > 0)
                    connectiveDomain.add(new ConnectiveDomain(domainVertexs, domainEdges));
            }
            // 按连通域点的个数排序
            connectiveDomain.sort((a, b) -> {
                return b.vertices.size() - a.vertices.size();
            });
        }

        Project(ProjectPo po) {
            id = po.getId();
            userId = po.getUserId();
            projectName = po.getProjectName();
            vertexMap = new HashMap<>(po.getVertices().size());
            for (var vPo : po.getVertices())
                vertexMap.put(vPo.getFunctionName(), new Vertex(vPo));
            edges = new ArrayList<>(po.getEdges().size());
            for (var ePo : po.getEdges())
                edges.add(new Edge(ePo, vertexMap.get(ePo.getFrom().getFunctionName()),
                        vertexMap.get(ePo.getTo().getFunctionName())));
            // TODO: 子图
        }

        ProjectStaticVo getStaticVo() {
            var vertexVos = new ArrayList<VertexStaticVo>(vertexMap.size());
            for (var v : vertexMap.values())
                vertexVos.add(v.getStaticVo());
            var edgeVos = new ArrayList<EdgeStaticVo>(edges.size());
            for (var e : edges)
                edgeVos.add(e.getStaticVo());
            var subgraphVos = new ArrayList<SubgraphStaticVo>(subgraphs.size());
            for (var s : subgraphs)
                subgraphVos.add(s.getStaticVo());
            return new ProjectStaticVo(id, vertexVos, edgeVos, subgraphVos);
        }

        ProjectDynamicVo getDynamicVo() {
            var vertexVos = new ArrayList<VertexDynamicVo>(vertexMap.size());
            for (var v : vertexMap.values())
                vertexVos.add(v.getDynamicVo());
            var edgeVos = new ArrayList<EdgeDynamicVo>(edges.size());
            for (var e : edges)
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
        Project newProject = new Project(caller, callee, sourceCode);
        return new ProjectAllVo();
    };

    @Override
    public void removeProject(Long id) {
        projectData.deleteById(id);
    };

    @Override
    public void updateProject(ProjectDynamicVo vo) {

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
        System.out.println("add subgraph");
        return new SubgraphAllVo();
    };

    @Override
    public void removeSubgraph(Long id) {
        subgraphData.deleteById(id);
    };

    @Override
    public void updateSubGraph(Long projectId, SubgraphDynamicVo vo) {
        System.out.println("update subgraph");
    };

    @Override
    public List<SubgraphAllVo> getSubgraphAllByProjectId(Long projectId) {
        System.out.println("get subgraph All By ProjectId");
        return new ArrayList<>();
    };

    @Override
    public PathVo getOriginalGraphShortestPath(Long projectId, Long startVertexId, Long endVertexId) {
        System.out.println("get OriginalGraph Shortest Path");
        return new PathVo();
    };

    @Override
    public PathVo getSubgraphShortestPath(Long projectId, Long subgraphId, Long startVertexId, Long endVertexId) {
        System.out.println("get Subgraph Shortest Path");
        return new PathVo();
    };

    @Override
    public List<String> getSimilarFunction(Long projectId, String funcName) {
        System.out.println("get similar function");
        return new ArrayList<>();
    };
}