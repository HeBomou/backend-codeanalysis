package local.happysixplus.backendcodeanalysis.po;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设置了阈值的子图
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubgraphDynamicPo {

    Long id;

    String name;

    /**
     * 各联通域注释
     */
    List<ConnectiveDomainDynamicPo> connectiveDomains;
}