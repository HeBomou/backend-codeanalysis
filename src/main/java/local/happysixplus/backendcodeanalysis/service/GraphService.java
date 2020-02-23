package local.happysixplus.backendcodeanalysis.service;

import java.util.List;

import local.happysixplus.backendcodeanalysis.vo.ConnectiveDomainVo;
import local.happysixplus.backendcodeanalysis.vo.PathVo;
import local.happysixplus.backendcodeanalysis.vo.VertexVo;

public interface GraphService {

    /**
     * 读取项目代码，并解析依赖关系，生成有向图。预处理得到点数、边数、联通域数、各点出度入度紧密度
     * 
     * @param path 项目代码文件夹的路径
     */
    void loadCode(String path);

    /**
     * 获取图的点数
     */
    int getVertexNum();

    /**
     * 获取图的边数
     */
    int getEdgeNum();

    /**
     * 获取图的联通域数
     */
    int getConnectiveDomainNum();

    /**
     * 获取图的所有联通域
     */
    List<ConnectiveDomainVo> getConnectiveDomains();

    /**
     * 设置最小紧密度阈值。预处理得到删除紧密度低于阈值的边后的图及其基本信息
     */
    void setClosenessMin(double closeness);

    /**
     * 获取删除紧密度低于阈值的边之后图的联通域。联通域按照顶点数降序排序
     */
    List<ConnectiveDomainVo> getConnectiveDomainsWithClosenessMin();

    /**
     * 获取图中从start出发到end的最短路
     */
    PathVo getShortestPath(VertexVo start, VertexVo end);
}