package local.happysixplus.backendcodeanalysis.po;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目所有信息，包括源代码，初始图的结构等静态信息，以及用户的注释等动态信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDynamicPo {

    Long id;

    String projectName;

    /**
     * 函数节点的注释、位置等
     */
    List<VertexDynamicPo> vertices;

    /**
     * 边的注释
     */
    List<EdgeDynamicPo> edges;

    /**
     * 阈值为零的默认子图
     */
    SubgraphDynamicPo defaultSubgraph;
}