package local.happysixplus.backendcodeanalysis.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 项目的注释等动态信息
 */
@Data
@AllArgsConstructor
public class ProjectVo {

    String projectName;

    OriginGraphVo baseGraph;
}