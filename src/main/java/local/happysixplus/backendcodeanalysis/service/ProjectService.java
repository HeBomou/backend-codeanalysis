package local.happysixplus.backendcodeanalysis.service;

import java.util.List;

import local.happysixplus.backendcodeanalysis.vo.ConnectiveDomainDynamicVo;
import local.happysixplus.backendcodeanalysis.vo.EdgeDynamicVo;
import local.happysixplus.backendcodeanalysis.vo.PathVo;
import local.happysixplus.backendcodeanalysis.vo.ProjectAllVo;
import local.happysixplus.backendcodeanalysis.vo.ProjectDynamicVo;
import local.happysixplus.backendcodeanalysis.vo.ProjectProfileVo;
import local.happysixplus.backendcodeanalysis.vo.SubgraphAllVo;
import local.happysixplus.backendcodeanalysis.vo.SubgraphDynamicVo;
import local.happysixplus.backendcodeanalysis.vo.VertexDynamicVo;

public interface ProjectService {

    /**
     * 读取项目代码，并解析依赖关系，生成有向图。预处理得到点数、边数、联通域数、各点出度入度紧密度
     * 
     * @param url 项目代码的git仓库url
     * @return 返回新项目的id
     */
    ProjectAllVo addProject(String projectName, String url, long userId);

    void removeProject(Long id);

    /**
     * 返回所有项目的动态信息
     * @param userId 添加用户id的筛选条件
     */
    List<ProjectDynamicVo> getProjectDynamic(Long userId);

    /**
     * 返回项目的点数边数等概要信息
     */
    ProjectProfileVo getProjectProfile(Long id);

    ProjectAllVo getProjectAllById(Long id);

    /**
     * 根据阈值生成一张子图，预处理得到删除紧密度低于阈值的边后的图及其基本信息，不包括只有一个点的联通域
     * 
     * @param projectId 项目id
     * @param threshold 紧密度阈值
     * @return 子图的id
     */
    SubgraphAllVo addSubgraph(Long projectId, Double threshold, String name);

    void removeSubgraph(Long id);

    /**
     * 更新一个项目的动态信息
     * @param projectId 项目id
     * @param vo 项目vo
     */
    void updateProjectDynamic(Long projectId, ProjectDynamicVo vo);

    /**
     * 更新一张子图的注释等动态信息
     * @param projectId 项目id
     * @param vo 子图vo
     */
    void updateSubGraphDynamic(Long projectId, SubgraphDynamicVo vo);

    /**
     * 更新一个联通域的注释等动态信息
     * @param projectId 项目id
     * @param subgraphId 子图id
     * @param vo 要更新的联通域vo
     */
    void updateConnectiveDomainDynamic(Long projectId, Long subgraphId, ConnectiveDomainDynamicVo vo);

    /**
     * 更新一条边的注释等动态信息
     * @param projectId
     * @param vo
     */
    void updateEdgeDynamic(Long projectId, EdgeDynamicVo vo);

    /**
     * 更新一个点的注释等动态信息
     * @param projectId
     * @param vo
     */
    void updateVertexDynamic(Long projectId, VertexDynamicVo vo);

    /**
     * 获取初始图中从start出发到end的所有路
     */
    PathVo getOriginalGraphPath(Long projectId, Long startVertexId, Long endVertexId);

    /**
     * 获取项目中与funcName相似的函数名
     */
    List<String> getSimilarFunction(Long projectId, String funcName);
}