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
import local.happysixplus.backendcodeanalysis.service.ProjectService;
import local.happysixplus.backendcodeanalysis.vo.PathVo;
import local.happysixplus.backendcodeanalysis.vo.ProjectAllVo;
import local.happysixplus.backendcodeanalysis.vo.ProjectDynamicVo;
import local.happysixplus.backendcodeanalysis.vo.SubgraphAllVo;
import local.happysixplus.backendcodeanalysis.vo.SubgraphDynamicVo;
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
        String functionName;
        int inDegree;
        int outDegree;
        List<Edge> edges = new ArrayList<>();
        List<Edge> undirectedEdge = new ArrayList<>();

        Vertex(String name) {
            functionName = name;
        }

        void addEdge(Edge e) {
            edges.add(e);
        }

        void addUndirectedEdge(Edge e) {
            undirectedEdge.add(e);
        }
    }

    public class Edge {
        Vertex from;
        Vertex to;
        Double closeness;

        Edge(Vertex begin, Vertex end) {
            from = begin;
            to = end;
        }

        void setCloseness(Double num) {
            closeness = num;
        }
    }

    public class ConnectiveDomain {
        int vertexNum;
        List<Vertex> vertexs = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        ConnectiveDomain(List<Vertex> v, List<Edge> e) {
            edges = e;
            vertexs = v;
            vertexNum = v.size();
        }
    }

    public class Graph {
        Map<String, Vertex> vertexMap;
        List<ConnectiveDomain> connectiveDomain;
        Set<Edge> edges;

        Graph(List<String> caller, List<String> callee) {
            Map<String, Boolean> isChecked = new HashMap<String, Boolean>();
            // 去重得到所有顶点集合
            var vertexNameSet = new HashSet<String>();
            vertexNameSet.addAll(caller);
            vertexNameSet.addAll(callee);
            var vertexNames = new ArrayList<String>(vertexNameSet);
            for (var str : vertexNames) {
                Vertex newVertex = new Vertex(str);
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
                newEdge.setCloseness(closeness);
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
                return b.vertexNum - a.vertexNum;
            });
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
        Graph graph=new Graph(caller, callee);
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
        System.out.println("get Project All By UserId");
        return new ArrayList<>();
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