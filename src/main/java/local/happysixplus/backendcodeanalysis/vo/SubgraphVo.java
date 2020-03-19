package local.happysixplus.backendcodeanalysis.vo;

import java.util.List;

import lombok.Data;

/**
 * 设置了阈值的子图
 */
@Data
public class SubgraphVo {

    Long id;

    Double threshold;

    /**
     * 各联通域注释
     */
    List<ConnectiveDomainVo> connectiveDomains;
}