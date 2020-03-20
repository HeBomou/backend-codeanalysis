package local.happysixplus.backendcodeanalysis.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 设置了阈值的子图，包括子图的各个联通域的点等静态信息，以及联通域注释的动态信息
 */
@Data
@AllArgsConstructor
public class SubgraphAllVo {

    Long id;

    /**
     * 外层是联通域，内层是每一个联通域的所有节点id
     */
    List<List<Long>> staticConnectiveDomains;

    Double threshold;

    /**
     * 各联通域注释等动态信息
     */
    List<ConnectiveDomainVo> dynamicConnectiveDomains;
}