package local.happysixplus.backendcodeanalysis.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 初始图
 */
@Data
@AllArgsConstructor
public class OriginGraphVo {

    /**
     * 函数节点的注释、位置等
     */
    List<VertexVo> vertices;

    /**
     * 边的注释
     */
    List<EdgeVo> edges;

    /**
     * 联通域注释
     */
    List<ConnectiveDomainVo> connectiveDomains;
}