package local.happysixplus.backendcodeanalysis.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 项目所有信息，包括源代码，初始图的结构等静态信息，以及用户的注释等动态信息
 */
@Data
@AllArgsConstructor
public class ProjectAllVo {

    Long id;

    /**
     * 初始图的所有点
     */
    List<VertexBaseVo> vertices;

    /**
     * 初始图的所有边
     */
    List<EdgeBaseVo> edges;

    String projectName;

    /**
     * 初始图的动态信息
     */
    OriginGraphVo baseGraph;

    /**
     * 所有的子图
     */
    List<SubgraphAllVo> subgraphs;
}