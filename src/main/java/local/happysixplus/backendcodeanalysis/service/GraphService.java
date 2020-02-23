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
    void LoadCode(String path);

    /**
     * 获取图的点数
     */
    int GetVertexNum();

    /**
     * 获取图的边数
     */
    int GetEdgeNum();

    /**
     * 获取图的联通域数
     */
    int GetConnectiveDomainNum();

    /**
     * 获取图的所有联通域
     */
    List<ConnectiveDomainVo> GetConnectiveDomains();

    /**
     * 设置最小紧密度阈值。预处理得到删除紧密度低于阈值的边后的图及其基本信息
     */
    void SetClosenessMin(double closeness);

    /**
     * 获取删除紧密度低于阈值的边之后图的联通域。联通域按照顶点数降序排序
     */
    List<ConnectiveDomainVo> GetConnectiveDomainsWithClosenessMin();

    /**
     * 获取图中从start出发到end的最短路
     */
    PathVo GetShortestPath(VertexVo start, VertexVo end);
}