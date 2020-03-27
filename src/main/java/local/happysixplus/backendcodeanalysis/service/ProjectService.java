package local.happysixplus.backendcodeanalysis.service;

import java.util.List;

import local.happysixplus.backendcodeanalysis.vo.PathVo;
import local.happysixplus.backendcodeanalysis.vo.ProjectAllVo;
import local.happysixplus.backendcodeanalysis.vo.ProjectDynamicVo;
import local.happysixplus.backendcodeanalysis.vo.SubgraphAllVo;
import local.happysixplus.backendcodeanalysis.vo.SubgraphDynamicVo;

public interface ProjectService {

    /**
     * 读取项目代码，并解析依赖关系，生成有向图。预处理得到点数、边数、联通域数、各点出度入度紧密度
     * 
     * @param url 项目代码的git仓库url
     * @return 返回新项目的id
     */
    ProjectAllVo addProject(String projectName, String url, long userId);

    void removeProject(Long id);

    void updateProject(ProjectDynamicVo vo);

    List<ProjectAllVo> getProjectAllByUserId(Long userId);

    /**
     * 根据阈值生成一张子图，预处理得到删除紧密度低于阈值的边后的图及其基本信息，不包括只有一个点的联通域
     * 
     * @param projectId 项目id
     * @param threshold 紧密度阈值
     * @return 子图的id
     */
    SubgraphAllVo addSubgraph(Long projectId, Double threshold);

    void removeSubgraph(Long id);

    void updateSubGraph(Long projectId, SubgraphDynamicVo vo);

    List<SubgraphAllVo> getSubgraphAllByProjectId(Long projectId);

    /**
     * 获取初始图中从start出发到end的最短路
     */
    PathVo getOriginalGraphShortestPath(Long projectId, Long startVertexId, Long endVertexId);

    /**
     * 获取项目中与funcName相似的函数名
     */
    List<String> getSimilarFunction(Long projectId, String funcName);
}