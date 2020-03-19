package local.happysixplus.backendcodeanalysis.vo;

import lombok.Data;

/**
 * 项目的注释等动态信息
 */
@Data
public class ProjectVo {

    String projectName;

    OriginGraphVo baseGraph;
}