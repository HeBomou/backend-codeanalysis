package local.happysixplus.backendcodeanalysis.service;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
        List<Edge> edges=new ArrayList<>();

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
    }

    // 顶点集合
    Map<String, Vertex> vertexMap = new HashMap<String, Vertex>();

    @Override
    public void LoadCode(String path) {
        List<String> caller = new ArrayList<>();
        List<String> callee = new ArrayList<>();
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
            Vertex begin=vertexMap.get(caller.get(i));
            Vertex end=vertexMap.get(callee.get(i));
            Edge tmpEdge = new Edge(begin,end);
            vertexMap.get(caller.get(i)).addEdge(tmpEdge);
        }
        return;
    }

    @Override
    public int GetVertexNum() {
        return -1;
    }

    @Override
    public int GetEdgeNum() {
        return -1;
    }

    @Override
    public int GetConnectiveDomainNum() {
        return -1;
    }

    @Override
    public List<ConnectiveDomainVo> GetConnectiveDomains() {
        return null;
    }

    @Override
    public void SetClosenessMin(double closeness) {
    }

    @Override
    public List<ConnectiveDomainVo> GetConnectiveDomainsWithClosenessMin() {
        return null;
    }

    @Override
    public PathVo GetShortestPath(VertexVo start, VertexVo end) {
        return null;
    }
}