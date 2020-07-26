package local.happysixplus.backendcodeanalysis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.util.AffineTransformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
import local.happysixplus.backendcodeanalysis.po.ConnectiveDomainColorDynamicPo;
import local.happysixplus.backendcodeanalysis.po.EdgePo;
import local.happysixplus.backendcodeanalysis.po.ProjectDynamicPo;
import local.happysixplus.backendcodeanalysis.po.ProjectPo;
import local.happysixplus.backendcodeanalysis.po.ProjectStaticAttributePo;
import local.happysixplus.backendcodeanalysis.po.SubgraphDynamicPo;
import local.happysixplus.backendcodeanalysis.po.VertexPo;
import local.happysixplus.backendcodeanalysis.po.VertexPositionDynamicPo;
import local.happysixplus.backendcodeanalysis.vo.SubgraphAllVo;
import local.happysixplus.backendcodeanalysis.vo.SubgraphDynamicVo;
import local.happysixplus.backendcodeanalysis.vo.VertexPositionDynamicVo;
import lombok.var;

import local.happysixplus.backendcodeanalysis.service.impl.ProjectServiceImpl.Vertex;
import local.happysixplus.backendcodeanalysis.service.impl.ProjectServiceImpl.Subgraph;
import local.happysixplus.backendcodeanalysis.service.impl.ProjectServiceImpl.Project;
import local.happysixplus.backendcodeanalysis.service.impl.ProjectServiceImpl.PackageNode;

@Service
public class AsyncForProjectServiceImpl {

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

    @Autowired
    AsyncCallGraphForProjectServiceImpl asyncCallGraphForProjectServiceImpl;

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

    private SubgraphAllVo addSubgraph(Long projectId, Project project, Double threshold, String name) {
        // 生成并存储静态信息
        var newSPo = project.initSubgraph(threshold);
        newSPo = subgraphData.save(newSPo);
        // 存储动态信息
        var sDPo = new SubgraphDynamicPo(newSPo.getId(), projectId, name);
        sDPo = subgraphDynamicData.save(sDPo);
        // 生成联通域初始颜色并存储
        var cDPoMap = new HashMap<Long, ConnectiveDomainColorDynamicPo>(newSPo.getConnectiveDomains().size());
        String[] colors = { "#CDCDB4", "#CDB5CD", "#CDBE70", "#B4CDCD", "#CD919E", "#9ACD32", "#CD4F39", "#8B3E2F",
                "#8B7E66", "#8B668B", "#36648B", "#141414" };
        for (var cd : newSPo.getConnectiveDomains()) {
            var color = colors[((int) (Math.random() * colors.length))];
            var cDPo = connectiveDomainColorDynamicData
                    .save(new ConnectiveDomainColorDynamicPo(cd.getId(), projectId, color));
            cDPoMap.put(cd.getId(), cDPo);
        }

        // 生成节点初始位置
        var cdList = new ArrayList<>(newSPo.getConnectiveDomains());
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
            Long subgraphId;

            Util(HashMap<Long, VertexPositionDynamicPo> resMap, Long projectId, Long subgraphId,
                    Map<Long, Vertex> vMap) {
                this.resMap = resMap;
                this.projectId = projectId;
                this.subgraphId = subgraphId;
                this.vMap = vMap;
            }

            float calcRadius(int size) {
                // TODO: 应当根据前端显示效果修改半径系数
                return (float) (200 * Math.sqrt(size));
            }

            int dfs(Vertex p) {
                if (wMap.containsKey(p.id))
                    return wMap.get(p.id);
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
                int colWidth = (int) length / (wColStart[wDiff - 1] + wColCnts[wDiff - 1]);
                for (var id : vIds) {
                    int rw = wMap.get(id) - minW;
                    float x = wColStart[rw] + wCnts[rw] / maxRowNum;
                    float y = wCnts[rw] % maxRowNum;
                    wCnts[rw]++;
                    float randOffsetX = (float)(Math.random() * 10);
                    float randOffsetY = (float)(Math.random() * 50);
                    resMap.put(id, new VertexPositionDynamicPo(null, id, projectId, subgraphId, leftUpX + x * colWidth + randOffsetX,
                            leftUpY + y * rowHeight + randOffsetY));
                }
                wMap.clear();
            }
        }
        Util util = new Util(vPosPoMap, project.id, newSPo.getId(), project.vIdMap);
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

        // 返回
        var subgraph = new Subgraph(newSPo);
        var pPPoInSs = vertexPositionDynamicData.findBySubgraphId(newSPo.getId());
        return subgraph.getAllVo(dPoTodVo(sDPo, pPPoInSs), new HashMap<>(), cDPoMap);
    }

    public SubgraphAllVo addSubgraph(Long projectId, Double threshold, String name) {
        // 获取project
        var po = projectData.findById(projectId).orElse(null);
        var vPos = vertexData.findByProjectId(projectId);
        var ePos = edgeData.findByProjectId(projectId);
        var project = new Project(po, vPos, ePos);
        po = null;
        vPos = null;
        ePos = null;
        // 生成子图并返回
        return addSubgraph(projectId, project, threshold, name);
    }

    @Async("ProjectExecutor")
    public CompletableFuture<String> asyncAddProject(Long projectId, String projectName, String url, long userId,
            long groupId) {
        try {
            var projectInfoFuture = asyncCallGraphForProjectServiceImpl.asyncGitPull(url);
            var projectInfo = projectInfoFuture.get(300, TimeUnit.SECONDS);
            if (projectInfo == null)
                throw new Exception();
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
            // TODO: 先存入错误信息避免失败后无法更新
            // projectDynamicData.save(new ProjectDynamicPo(projectId, userId, projectName +
            // "（数据库异常）"));
            // 生成并存入项目静态信息
            var project = initAndSaveProject(projectId, caller, callee, sourceCode, userId, groupId);
            // 可能projectId已经因为被用户删除而失效
            projectId = project.id;
            sourceCode = null;
            // 生成默认子图
            var subgAllVo = addSubgraph(projectId, project, 0D, "Default subgraph");
            // 存入项目静态属性信息
            var projSAPo = new ProjectStaticAttributePo(project.id, userId, project.vIdMap.size(),
                    project.eIdMap.size(), subgAllVo.getConnectiveDomains().size(), groupId);
            projSAPo = projectStaticAttributeData.save(projSAPo);
            // 存入项目动态信息
            var projDPo = new ProjectDynamicPo(project.id, userId, projectName, groupId);
            projDPo = projectDynamicData.save(projDPo);
        } catch (TimeoutException timeout) {
            var failedDPo = new ProjectDynamicPo(projectId, userId, projectName + "（项目Clone超时）", groupId);
            projectDynamicData.save(failedDPo);
        } catch (Exception e) {
            var failedDPo = new ProjectDynamicPo(projectId, userId, projectName + "（项目无法解析）", groupId);
            projectDynamicData.save(failedDPo);
        }
        return CompletableFuture.completedFuture("Finished");
    }

    Project initAndSaveProject(Long projectId, List<String> caller, List<String> callee, Map<String, String> sourceCode,
            Long userId, Long groupId) {
        List<EdgePo> edgePos = new ArrayList<EdgePo>();
        List<VertexPo> vertexPos = new ArrayList<VertexPo>();
        Map<String, VertexPo> vertexMap = new HashMap<String, VertexPo>();
        Map<String, Integer> outdegree = new HashMap<String, Integer>();
        Map<String, Integer> indegree = new HashMap<String, Integer>();

        // 存入项目
        var projPo = projectData.save(new ProjectPo(projectId, userId, "", groupId));
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

    private static VertexPositionDynamicVo dPoTodVo(VertexPositionDynamicPo po) {
        return new VertexPositionDynamicVo(po.getVertexId(), po.getSubgraphId(), po.getX(), po.getY());
    }

    private static SubgraphDynamicVo dPoTodVo(SubgraphDynamicPo po, List<VertexPositionDynamicPo> vPos) {
        if (po == null)
            return null;
        return new SubgraphDynamicVo(po.getId(), po.getName(),
                vPos.stream().map(p -> dPoTodVo(p)).collect(Collectors.toList()));
    }

}