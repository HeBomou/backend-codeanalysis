package local.happysixplus.backendcodeanalysis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import local.happysixplus.backendcodeanalysis.util.callgraph.CallGraphMethods;
import local.happysixplus.backendcodeanalysis.data.ConnectiveDomainColorDynamicData;
import local.happysixplus.backendcodeanalysis.data.ConnectiveDomainData;
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
import local.happysixplus.backendcodeanalysis.service.ProjectService;
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
import local.happysixplus.backendcodeanalysis.vo.VertexPositionDynamicVo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.var;

@Service
public class ProjectServiceImpl implements ProjectService {

    static public class Vertex {
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

        VertexPo getVertexPo(Long projectId) {
            return new VertexPo(id, projectId, functionName, sourceCode);
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

        ProjectAllVo getAllVo(Map<Long, VertexDynamicPo> vDPoMap, Map<Long, EdgeDynamicPo> eDPoMap,
                List<SubgraphAllVo> sVos, ProjectDynamicVo dVo) {
            List<VertexAllVo> vVos = new ArrayList<>();
            for (var v : vIdMap.values())
                vVos.add(v.getAllVo(dPoTodVo(vDPoMap.get(v.id))));
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
    ConnectiveDomainData connectiveDomainData;

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

    @Autowired
    AsyncForProjectServiceImpl asyncForProjectServiceImpl;

    @Override
    public ProjectAllVo addProject(String projectName, String url, long userId, long groupId) {
        var po = new ProjectPo(null, userId, "", groupId);
        po = projectData.save(po);
        var dPo = new ProjectDynamicPo(po.getId(), userId, projectName + "（正在解析）", groupId);
        dPo = projectDynamicData.save(dPo);
        var sAPo = new ProjectStaticAttributePo(po.getId(), userId, -1, -1, -1, groupId);
        sAPo = projectStaticAttributeData.save(sAPo);
        asyncForProjectServiceImpl.asyncAddProject(po.getId(), projectName, url, userId, groupId);
        return new ProjectAllVo(po.getId(), null, null, null, null,
                new ProjectDynamicVo(po.getId(), projectName + "（正在解析）"));
    };

    @Override
    public List<ProjectDynamicVo> getGroupProject(long groupId) {
        var pos = projectDynamicData.findByGroupId(groupId);
        var vos = new ArrayList<ProjectDynamicVo>(pos.size());
        for (var po : pos)
            vos.add(new ProjectDynamicVo(po.getId(), po.getProjectName()));
        return vos;
    }

    @Override
    public void removeProject(Long id) {
        boolean err = false;
        if (projectData.existsById(id))
            projectData.deleteById(id);
        else
            err = true;
        if (projectDynamicData.existsById(id))
            projectDynamicData.deleteById(id);
        if (projectStaticAttributeData.existsById(id))
            projectStaticAttributeData.deleteById(id);
        asyncForProjectServiceImpl.asyncRemoveProject(id);
        if (err)
            throw new MyRuntimeException("项目已不存在，但仍尝试删除");
    };

    @Override
    public void updateProjectDynamic(Long projectId, ProjectDynamicVo vo) {
        vo.setId(projectId);
        // var userId = projectDynamicData.findById(projectId).orElse(null).getUserId();
        // var groupId = projectDynamicData.findById(projectId).orElse(null).getGroupId();
        var projectDynamicPo=projectDynamicData.findById(projectId).orElse(null);
        var userId=projectDynamicPo.getUserId();
        var groupId=projectDynamicPo.getGroupId();
        projectDynamicData.save(new ProjectDynamicPo(vo.getId(), userId, vo.getProjectName(), groupId));
    };

    @Override
    public List<ProjectBasicAttributeVo> getProjectBasicAttribute(Long userId, Long groupId) {
        List<ProjectDynamicPo> pDPos;
        if (userId.equals(-1L) && groupId.equals(-1L))
            pDPos = projectDynamicData.findAll();
        else if (groupId.equals(-1L))
            pDPos = projectDynamicData.findByUserId(userId);
        else
            pDPos = projectDynamicData.findByGroupId(groupId);
        List<ProjectStaticAttributePo> pSAPos;
        if (userId.equals(-1L) && groupId.equals(-1L))
            pSAPos = projectStaticAttributeData.findAll();
        else if (groupId.equals(-1L))
            pSAPos = projectStaticAttributeData.findByUserId(userId);
        else
            pSAPos = projectStaticAttributeData.findByGroupId(groupId);
        var pSAPoMap = pSAPos.stream().collect(Collectors.toMap(ProjectStaticAttributePo::getId, po -> po));
        var vos = new ArrayList<ProjectBasicAttributeVo>(pDPos.size());
        for (var pDPo : pDPos) {
            var pSAPo = pSAPoMap.get(pDPo.getId());
            if (pSAPo != null)
                vos.add(new ProjectBasicAttributeVo(pDPo.getId(), pDPo.getProjectName(), pSAPo.getVertexNum(),
                        pSAPo.getEdgeNum(), pSAPo.getConnectiveDomainNum()));
        }
        return vos;
    };

    @Override
    public ProjectAllVo getProjectAll(Long id) {
        // 项目
        var po = projectData.findById(id).orElse(null);
        if (po == null)
            throw new MyRuntimeException("项目不存在");
        if (po.getPackageStructure().equals(""))
            throw new MyRuntimeException("项目正在解析");
        var vPos = vertexData.findByProjectId(id);
        var ePos = edgeData.findByProjectId(id);
        var project = new Project(po, vPos, ePos);
        po = null;
        vPos = null;
        ePos = null;
        var vDPoMap = vertexDynamicData.findByProjectId(id).stream().collect(Collectors.toMap(v -> v.getId(), v -> v));
        var eDPoMap = edgeDynamicData.findByProjectId(id).stream().collect(Collectors.toMap(v -> v.getId(), v -> v));
        var dVo = dPoTodVo(projectDynamicData.findById(id).orElse(null));
        // 子图
        var sPos = subgraphData.findByProjectId(id);
        var sDPoMap = subgraphDynamicData.findByProjectId(id).stream()
                .collect(Collectors.toMap(v -> v.getId(), v -> v));
        var cDPoMap = connectiveDomainDynamicData.findByProjectId(id).stream()
                .collect(Collectors.toMap(v -> v.getId(), v -> v));
        var cCDPoMap = connectiveDomainColorDynamicData.findByProjectId(id).stream()
                .collect(Collectors.toMap(v -> v.getId(), v -> v));
        var sVos = new ArrayList<SubgraphAllVo>(sPos.size());
        // 获取子图vo
        for (var sPo : sPos) {
            var vPPoInSs = vertexPositionDynamicData.findBySubgraphId(sPo.getId());
            sVos.add(new Subgraph(sPo).getAllVo(dPoTodVo(sDPoMap.get(sPo.getId()), vPPoInSs), cDPoMap, cCDPoMap));
        }
        // 返回
        return project.getAllVo(vDPoMap, eDPoMap, sVos, dVo);
    }

    @Override
    public ProjectProfileVo getProjectProfile(Long id) {
        var pDPo = projectDynamicData.findById(id).orElse(null);
        var pSAPo = projectStaticAttributeData.findById(id).orElse(null);
        if (pDPo == null)
            throw new MyRuntimeException("项目不存在");
        if (pSAPo == null)
            throw new MyRuntimeException("项目正在解析或解析失败");
        String projectName = pDPo.getProjectName();
        Integer vertexNum = pSAPo.getVertexNum();
        Integer edgeNum = pSAPo.getEdgeNum();
        Integer connectiveDomainNum = pSAPo.getConnectiveDomainNum();
        Integer subgraphNum = subgraphData.countByProjectId(id);
        Integer vertexAnotationNum = vertexDynamicData.countByProjectId(id);
        Integer edgeAnotationNum = edgeDynamicData.countByProjectId(id);
        Integer connectiveDomainAnotationNum = connectiveDomainDynamicData.countByProjectId(id);
        return new ProjectProfileVo(id, projectName, vertexNum, edgeNum, connectiveDomainNum, subgraphNum,
                vertexAnotationNum, edgeAnotationNum, connectiveDomainAnotationNum);
    }

    @Override
    public SubgraphAllVo addSubgraph(Long projectId, Double threshold, String name) {
        return asyncForProjectServiceImpl.addSubgraph(projectId, threshold, name);
    };

    @Override
    public void removeSubgraph(Long id) {
        subgraphData.deleteById(id);
        subgraphDynamicData.deleteById(id);
    };

    @Override
    public void updateSubGraphDynamic(Long projectId, SubgraphDynamicVo vo) {
        subgraphDynamicData.save(new SubgraphDynamicPo(vo.getId(), projectId, vo.getName()));
    };

    @Override
    public void updateConnectiveDomainDynamic(Long projectId, Long subgraphId, ConnectiveDomainDynamicVo vo) {
        if (vo.getAnotation() != null)
            connectiveDomainDynamicData.save(new ConnectiveDomainDynamicPo(vo.getId(), projectId, vo.getAnotation()));
        if (vo.getColor() != null)
            connectiveDomainColorDynamicData
                    .save(new ConnectiveDomainColorDynamicPo(vo.getId(), projectId, vo.getColor()));
    }

    @Override
    public void updateEdgeDynamic(Long projectId, EdgeDynamicVo vo) {
        edgeDynamicData.save(new EdgeDynamicPo(vo.getId(), projectId, vo.getAnotation()));
    }

    @Override
    public void updateVertexDynamic(Long projectId, VertexDynamicVo vo) {
        vertexDynamicData.save(new VertexDynamicPo(vo.getId(), projectId, vo.getAnotation()));
    }

    @Override
    public void updateVertexPositionDynamic(Long projectId, VertexPositionDynamicVo vo) {
        var po = vertexPositionDynamicData.findByVertexIdAndSubgraphId(vo.getId(), vo.getSubgraphId());
        po.setX(vo.getX());
        po.setY(vo.getY());
        vertexPositionDynamicData.save(po);
    }

    @Override
    public void updateConnectiveDomainAllVertex(Long projectId, Long subgraphId, Long connectiveDomainId,
            float relativeX, float relativeY) {
        var pVertexPo = vertexPositionDynamicData.findBySubgraphId(subgraphId);
        var cVertexIds = new HashSet<>(connectiveDomainData.findById(connectiveDomainId).orElse(null).getVertexIds());
        var vPos = new ArrayList<VertexPositionDynamicPo>();
        for (var vpo : pVertexPo) {
            if (cVertexIds.contains(vpo.getVertexId())) {
                vpo.setX(vpo.getX() + relativeX);
                vpo.setY(vpo.getY() + relativeY);
                vPos.add(vpo);
            }
        }
        vertexPositionDynamicData.saveAll(vPos);
    }

    @Override
    public List<String> getSimilarFunction(Long projectId, String funcName) {
        var res = new ArrayList<String>();
        var vPos = vertexData.findByProjectId(projectId);
        for (var vPo : vPos)
            if (vPo.getFunctionName().contains(funcName))
                res.add(vPo.getFunctionName());
        return res;
    };

    static class PathE {
        long id;
        PathV to;

        PathE(long id, PathV to) {
            this.id = id;
            this.to = to;
        }
    }

    static class PathV {
        long id;
        boolean inPath;
        List<PathE> es = new ArrayList<>();

        PathV(Long id) {
            this.id = id;
            this.inPath = false;
        }
    }

    @Override
    public PathVo getOriginalGraphPath(Long projectId, Long startVertexId, Long endVertexId) {
        var res = new ArrayList<List<Long>>();
        var po = projectData.findById(projectId).orElse(null);
        var vPos = vertexData.findByProjectId(projectId);
        var ePos = edgeData.findByProjectId(projectId);
        var project = new Project(po, vPos, ePos);
        po = null;
        vPos = null;
        ePos = null;
        // 添加点
        var vs = new HashMap<Long, PathV>(project.vIdMap.size());
        for (var v : project.vIdMap.values())
            vs.put(v.id, new PathV(v.id));
        // 添加单向边
        for (var e : project.eIdMap.values())
            vs.get(e.from.id).es.add(new PathE(e.id, vs.get(e.to.id)));

        getAllPathDFS(endVertexId, vs.get(startVertexId), new ArrayList<Long>(), res);
        res.sort((a, b) -> {
            return b.size() - a.size();
        });
        if (res.size() > 50)
            return new PathVo(res.size(), res.subList(0, 50));
        else
            return new PathVo(res.size(), res);
    };

    private void getAllPathDFS(long endVertexId, PathV v, List<Long> path, List<List<Long>> res) {
        if (v.inPath)
            return;
        if (v.id == endVertexId) {
            res.add(new ArrayList<>(path));
            return;
        }
        v.inPath = true;
        for (var edge : v.es) {
            path.add(edge.id);
            getAllPathDFS(endVertexId, edge.to, path, res);
            path.remove(path.size() - 1);
        }
        v.inPath = false;
    }

    private static VertexDynamicVo dPoTodVo(VertexDynamicPo po) {
        if (po == null)
            return null;
        return new VertexDynamicVo(po.getId(), po.getAnotation());
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

    private static VertexPositionDynamicVo dPoTodVo(VertexPositionDynamicPo po) {
        return new VertexPositionDynamicVo(po.getVertexId(), po.getSubgraphId(), po.getX(), po.getY());
    }

    private static SubgraphDynamicVo dPoTodVo(SubgraphDynamicPo po, List<VertexPositionDynamicPo> vPos) {
        if (po == null)
            return null;
        return new SubgraphDynamicVo(po.getId(), po.getName(),
                vPos.stream().map(p -> dPoTodVo(p)).collect(Collectors.toList()));
    }

    private static ProjectDynamicVo dPoTodVo(ProjectDynamicPo po) {
        if (po == null)
            return null;
        return new ProjectDynamicVo(po.getId(), po.getProjectName());
    }
}