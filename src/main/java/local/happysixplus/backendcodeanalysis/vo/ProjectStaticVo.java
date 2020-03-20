package local.happysixplus.backendcodeanalysis.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 项目所有信息，包括源代码，初始图的结构等静态信息，以及用户的注释等动态信息
 */
@Data
@AllArgsConstructor
public class ProjectStaticVo {

    Long id;

    /**
     * 初始图的所有点
     */
    List<VertexStaticVo> vertices;

    /**
     * 初始图的所有边
     */
    List<EdgeStaticVo> edges;

    /**
     * 阈值为零的默认子图
     */
    SubgraphStaticVo defaultSubgraph;
}