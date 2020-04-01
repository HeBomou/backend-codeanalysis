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
public class ProjectBasicAttributeVo {

    Long id;

    String projectName;

    /**
     * 初始图点数
     */
    Integer vertexNum;

    /**
     * 初始图边数
     */
    Integer edgeNum;

    /**
     * 初始图联通域个数
     */
    Integer connectiveDomainNum;

}