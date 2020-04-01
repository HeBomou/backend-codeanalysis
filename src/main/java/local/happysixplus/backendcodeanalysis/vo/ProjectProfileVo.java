package local.happysixplus.backendcodeanalysis.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目所有信息，包括源代码，初始图的结构等静态信息，以及用户的注释等动态信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectProfileVo {

    Long id;

    /**
     * 初始图点数
     */
    Integer vertexNum;

    /**
     * 初始图边数
     */
    Integer edgeNum;

    /**
     * 子图数量
     */
    Integer subgraphNum;

    /**
     * 被标注的点数
     */
    Integer vertexAnotationNum;

    /**
     * 被标注的边数
     */
    Integer edgeAnotationNum;

    /**
     * 被标注的联通域数
     */
    Integer connectiveDomainAnotationNum;
}