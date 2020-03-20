package local.happysixplus.backendcodeanalysis.po;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 设置了阈值的子图，包括子图的各个联通域的点等静态信息
 */
@Data
@AllArgsConstructor
public class SubgraphStaticPo {

    Long id;

    Long projectId;

    Double threshold;

    /**
     * 联通域的点与边等信息
     */
    List<ConnectiveDomainStaticPo> staticConnectiveDomains;
}