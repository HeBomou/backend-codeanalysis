package local.happysixplus.backendcodeanalysis.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 项目所有信息，包括源代码，初始图的结构等静态信息，以及用户的注释等动态信息
 */
@Data
@AllArgsConstructor
public class ProjectAllVo {

    Long id;

    ProjectStaticVo staticVo;

    ProjectDynamicVo dynamicVo;
}