package local.happysixplus.backendcodeanalysis.service;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Collections;
import java.io.*;

import org.springframework.stereotype.Service;

import local.happysixplus.backendcodeanalysis.vo.ConnectiveDomainVo;
import local.happysixplus.backendcodeanalysis.vo.PathVo;
import local.happysixplus.backendcodeanalysis.vo.VertexVo;

@Service
public class GraphServiceImpl implements GraphService {
    // 内部顶点类,包括顶点函数名称和从该点出发的边
    public class Vertex {
        String functionName;
        List<Edge> edges = new ArrayList<>();

        Vertex(String name) {
            functionName = name;
        }

        void addEdge(Edge e) {
            edges.add(e);
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
    }

    // 内部有向图类
    public class Graph {
        Map<String, Vertex> vertexMap = new HashMap<String, Vertex>();
        Double closenessMin = Double.NEGATIVE_INFINITY;

        Graph(List<String> caller, List<String> callee) {
            // 去重得到所有顶点集合
            List<String> verStr = new ArrayList<>();
            verStr.addAll(caller);
            verStr.addAll(callee);
            verStr = new ArrayList<String>(new HashSet<>(verStr));
            for (String each : verStr) {
                vertexMap.put(each, new Vertex(each));
            }
            // 为每个顶点添加边集
            for (int i = 0; i < caller.size(); i++) {
                String startStr = caller.get(i);
                String endStr = callee.get(i);
                Double closeness = 2.0
                        / (Collections.frequency(caller, startStr) + Collections.frequency(callee, endStr));
                if (closeness < closenessMin)
                    continue;
                Vertex begin = vertexMap.get(caller.get(i));
                Vertex end = vertexMap.get(callee.get(i));
                Edge tmpEdge = new Edge(begin, end);
                tmpEdge.setCloseness(closeness);
                vertexMap.get(caller.get(i)).addEdge(tmpEdge);
            }
        }
    }

    // 完整有向图
    Graph graph;
    // 输入边集对应顶点
    List<String> caller = new ArrayList<>();
    List<String> callee = new ArrayList<>();

    @Override
    public void loadCode(String path) {
        // 从文件中读取函数依赖关系
        File file = new File(path);
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = bf.readLine()) != null) {
                String[] temp = line.split(" ");
                caller.add(temp[0].substring(2));
                callee.add(temp[1].substring(3));
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        graph = new Graph(caller, callee);
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
        return -1;
    }

    @Override
    public List<ConnectiveDomainVo> getConnectiveDomains() {
        return null;
    }

    @Override
    public void setClosenessMin(double closeness) {
    }

    @Override
    public List<ConnectiveDomainVo> getConnectiveDomainsWithClosenessMin() {
        return null;
    }

    @Override
    public PathVo getShortestPath(VertexVo start, VertexVo end) {
        return null;
    }
}