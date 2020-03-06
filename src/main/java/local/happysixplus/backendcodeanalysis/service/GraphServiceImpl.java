package local.happysixplus.backendcodeanalysis.service;

import java.util.Map;
import java.util.Set;
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
import lombok.var;

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
            List<VertexVo> vertexVos = new ArrayList<>();
            List<EdgeVo> edgeVos = new ArrayList<>();
            for (var vertex : vertexs) {
                vertexVos.add(vertex.getVertexVo());
                for (int j = 0; j < vertex.edges.size(); j++) {
                    Edge e = vertex.edges.get(j);
                    edgeVos.add(new EdgeVo(e.from.getVertexVo(), e.to.getVertexVo(), e.closeness));
                }
            }
            return new ConnectiveDomainVo(vertexVos, edgeVos);
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
            var vertexNameSet = new HashSet<String>();
            vertexNameSet.addAll(caller);
            vertexNameSet.addAll(callee);
            var vertexNames = new ArrayList<String>(vertexNameSet);
            for (String vertexName : vertexNames) {
                Vertex newVertex = new Vertex(vertexName);
                vertexMap.put(vertexName, newVertex);
                isChecked.put(vertexName, false);
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
    // 变化有向图
    Graph limitedGraph = new Graph(caller, callee, -1.0);

    @Override
    public boolean loadCode(String path) {
        caller = new ArrayList<>();
        callee = new ArrayList<>();
        // 从文件中读取函数依赖关系
        File file = new File(path);
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new FileReader(file));
            String line = null;
            Set<List<String>> edgeSet = new HashSet<>();
            while ((line = bf.readLine()) != null) {
                List<String> tempList = new ArrayList<>();
                String[] temp = line.split(" ");
                tempList.add(temp[0].substring(2));
                tempList.add(temp[1].substring(3));
                edgeSet.add(tempList);
            }
            for (var edge : edgeSet) {
                caller.add(edge.get(0));
                callee.add(edge.get(1));
            }
            bf.close();
        } catch (IOException e) {
            return false;
        }
        graph = new Graph(caller, callee, -1.0);
        limitedGraph = new Graph(caller, callee, -1.0);
        return true;
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
        Map<String, TpVertex> tpVertexs = new HashMap<>();
        // 入度为0的点集合
        List<TpVertex> vertexWithZero = new ArrayList<>();
        // 初始化两个list
        for (String vstr : graph.vertexMap.keySet()) {
            TpVertex temp;
            if (vstr.equals(start.getFunctionName()))
                temp = new TpVertex(graph.vertexMap.get(vstr), 1, 0);
            else
                temp = new TpVertex(graph.vertexMap.get(vstr), 0, Integer.MAX_VALUE / 3);
            // 处理自环
            for (var edge : temp.vertex.edges)
                if (edge.to.functionName.equals(temp.vertex.functionName))
                    temp.inDegree--;

            tpVertexs.put(vstr, temp);
            if (temp.inDegree == 0)
                vertexWithZero.add(temp);
        }
        while (vertexWithZero.size() > 0) {
            TpVertex from = vertexWithZero.get(0);
            for (int i = 0; i < from.vertex.outDegree; i++) {
                String toStr = from.vertex.edges.get(i).to.functionName;
                // 处理自环
                if (toStr.equals(from.vertex.functionName))
                    continue;
                TpVertex to = tpVertexs.get(toStr);
                to.pathNum += from.pathNum;
                to.inDegree--;
                if (to.inDegree == 0)
                    vertexWithZero.add(to);
                if (from.edgeNum + 1 < to.edgeNum) {
                    to.edgeNum = from.edgeNum + 1;
                    to.preEdge = from.vertex.edges.get(i);
                }
            }
            vertexWithZero.remove(0);
        }
        // 判断是否能到达
        if (tpVertexs.get(end.getFunctionName()).pathNum == 0)
            return new PathVo(0, new ArrayList<>());
        // 输出
        List<EdgeVo> path = new ArrayList<>();
        TpVertex temp = tpVertexs.get(end.getFunctionName());
        int pathNum = temp.pathNum;
        while (!temp.vertex.functionName.equals(start.getFunctionName())) {
            path.add(temp.preEdge.getEdgeVo());
            temp = tpVertexs.get(temp.preEdge.from.functionName);
        }
        Collections.reverse(path);
        return new PathVo(pathNum, path);
    }

    @Override
    public List<String> getSimilarVertex(String funcName) {
        var res = new ArrayList<String>();
        for (var fullName : graph.vertexMap.keySet()) {
            if (fullName.contains(funcName))
                res.add(fullName);
        }
        return res;
    }

    public class TpVertex {
        Vertex vertex;
        int pathNum;
        int edgeNum;
        int inDegree;
        Edge preEdge;

        TpVertex(Vertex v, int pn, int en) {
            vertex = v;
            pathNum = pn;
            edgeNum = en;
            inDegree = v.inDegree;
            preEdge = null;
        }
    }
}