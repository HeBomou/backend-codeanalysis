package local.happysixplus.backendcodeanalysis.service;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.io.*;

import org.springframework.stereotype.Service;

import local.happysixplus.backendcodeanalysis.vo.ConnectiveDomainVo;
import local.happysixplus.backendcodeanalysis.vo.EdgeVo;
import local.happysixplus.backendcodeanalysis.vo.PathVo;
import local.happysixplus.backendcodeanalysis.vo.VertexVo;

@Service
public class GraphServiceImpl implements GraphService {
    // 内部顶点类,包括顶点函数名称和从该点出发的边
    public class Vertex {
        String functionName;
        int inDegree;
        int outDegree;
        // 有向图的边：从该点出发的边
        List<Edge> edges = new ArrayList<>();
        // 无向图的边
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

        VertexVo getVertexVo() {
            return new VertexVo(functionName);
        }
    }

    // 内部边类
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

        EdgeVo getEdgeVo() {
            return new EdgeVo(from.getVertexVo(), to.getVertexVo(), closeness);
        }
    }

    // 内部连通域类
    public class ConnectiveDomain {
        int vertexNum;
        List<Vertex> vertexs = new ArrayList<>();

        ConnectiveDomain(List<Vertex> v) {
            vertexs = v;
            vertexNum = v.size();
        }

        ConnectiveDomainVo getConnectiveDomainVo() {
            List<EdgeVo> edgeVo = new ArrayList<>();
            for (int i = 0; i < vertexNum; i++) {
                for (int j = 0; j < vertexs.get(i).edges.size(); j++) {
                    Edge e = vertexs.get(i).edges.get(j);
                    edgeVo.add(new EdgeVo(e.from.getVertexVo(), e.to.getVertexVo(), e.closeness));
                }
            }
            return new ConnectiveDomainVo(vertexNum, edgeVo);
        }
    }

    // 内部有向图类
    public class Graph {
        Map<String, Vertex> vertexMap = new HashMap<String, Vertex>();
        Double closenessMin;
        List<ConnectiveDomain> connectiveDomain = new ArrayList<>();

        Graph(List<String> caller, List<String> callee, Double clo) {
            closenessMin = clo;
            Map<String, Boolean> isChecked = new HashMap<String, Boolean>();
            // 去重得到所有顶点集合
            List<String> vertexName = new ArrayList<>();
            vertexName.addAll(caller);
            vertexName.addAll(callee);
            vertexName = new ArrayList<String>(new HashSet<>(vertexName));
            for (String vertex : vertexName) {
                Vertex newVertex = new Vertex(vertex);
                newVertex.outDegree = Collections.frequency(caller, vertex);
                newVertex.inDegree = Collections.frequency(callee, vertex);
                vertexMap.put(vertex, newVertex);
                isChecked.put(vertex, false);
            }
            // 为每个顶点添加边集
            for (int i = 0; i < caller.size(); i++) {
                Vertex begin = vertexMap.get(caller.get(i));
                Vertex end = vertexMap.get(callee.get(i));
                Double closeness = 2.0 / (begin.outDegree + end.inDegree);
                // 控制紧密度
                if (closeness < closenessMin)
                    continue;
                Edge tmpEdge = new Edge(begin, end);
                tmpEdge.setCloseness(closeness);
                begin.addEdge(tmpEdge);
                begin.addUndirectedEdge(tmpEdge);
                end.addUndirectedEdge(tmpEdge);
            }
            // 计算连通域
            for (String vertex : vertexMap.keySet()) {
                List<Vertex> tmpDomain = new ArrayList<>();
                DFS(vertexMap.get(vertex), isChecked, tmpDomain);
                if (tmpDomain.size() > 0)
                    connectiveDomain.add(new ConnectiveDomain(tmpDomain));
            }
            // 按连通域点的个数排序
            Collections.sort(connectiveDomain, new Comparator<ConnectiveDomain>() {
                public int compare(ConnectiveDomain o1, ConnectiveDomain o2) {
                    return o2.vertexNum - o1.vertexNum;
                }
            });
        }

        void DFS(Vertex vertex, Map<String, Boolean> isChecked, List<Vertex> tmpDomain) {
            if (isChecked.get(vertex.functionName))
                return;
            isChecked.put(vertex.functionName, true);
            tmpDomain.add(vertex);
            for (Edge e : vertex.undirectedEdge) {
                Vertex anotherVertex = (e.from == vertex) ? e.to : e.from;
                DFS(anotherVertex, isChecked, tmpDomain);
            }
        }
    }

    // 输入边集对应顶点
    List<String> caller = new ArrayList<>();
    List<String> callee = new ArrayList<>();
    // 完整有向图
    Graph graph = new Graph(caller, callee, -1.0);
    Graph limitedGraph = new Graph(caller, callee, -1.0);

    @Override
    public void loadCode(String path) {
        List<List<String>> edgePair = new ArrayList<List<String>>();
        // 从文件中读取函数依赖关系
        File file = new File(path);
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = bf.readLine()) != null) {
                List<String> tempList = new ArrayList<>();
                String[] temp = line.split(" ");
                tempList.add(temp[0].substring(2));
                tempList.add(temp[1].substring(3));
                if (!edgePair.contains(tempList)) {
                    edgePair.add(tempList);
                    caller.add(tempList.get(0));
                    callee.add(tempList.get(1));
                }
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        graph = new Graph(caller, callee, -1.0);
        limitedGraph = new Graph(caller, callee, -1.0);
        return;
    }

    @Override
    public int getVertexNum() {
        return graph.vertexMap.size();
    }

    @Override
    public int getEdgeNum() {
        return caller.size();
    }

    @Override
    public int getConnectiveDomainNum() {
        return limitedGraph.connectiveDomain.size();
    }

    @Override
    public List<ConnectiveDomainVo> getConnectiveDomains() {
        List<ConnectiveDomainVo> resVo = new ArrayList<>();
        for (int i = 0; i < graph.connectiveDomain.size(); i++) {
            if (graph.connectiveDomain.get(i).vertexNum > 1)
                resVo.add(graph.connectiveDomain.get(i).getConnectiveDomainVo());
        }
        return resVo;
    }

    @Override
    public void setClosenessMin(double closeness) {
        limitedGraph = new Graph(caller, callee, closeness);
        return;
    }

    @Override
    public List<ConnectiveDomainVo> getConnectiveDomainsWithClosenessMin() {
        List<ConnectiveDomainVo> resVo = new ArrayList<>();
        for (int i = 0; i < limitedGraph.connectiveDomain.size(); i++) {
            if (limitedGraph.connectiveDomain.get(i).vertexNum > 1)
                resVo.add(limitedGraph.connectiveDomain.get(i).getConnectiveDomainVo());
        }
        return resVo;
    }

    @Override
    public PathVo getShortestPath(VertexVo start, VertexVo end) {
        return null;
    }
}